package com.parse.starter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

/**
 * Created by Borris on 08/05/2016.
 */
public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        //this receives the intent after alarm time is up and called

        //defined receiver and intent-filter in manifest for this - also set alarm was a use permission

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ProfileDatastore profileDatastore = new ProfileDatastore();
        List<ClassProfile> profileList = databaseHelper.getAllProfiles();
        double lowestPercent=100;
        String lowestProfileName="";
        for (int i = 0; i < profileList.size(); i++) {

            GoalStore1 goalStore1= new GoalStore1(databaseHelper.getGoals(profileList.get(i).getName()),profileList.get(i).getName());
            if(goalStore1.getTotalPercentage()<lowestPercent){
                lowestPercent=goalStore1.getTotalPercentage();
                lowestProfileName=goalStore1.profile;
            }
        }

        if(lowestPercent<100){
            PendingIntent notifyIntent = PendingIntent.getActivity(context, 0, new
                    Intent(context, ActProfiles.class), 0);

            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context);
            notifBuilder.setSmallIcon(R.drawable.goal_shark_logo1);
            notifBuilder.setContentTitle("Enjoying Yer Doss, Aye?");
            String msg;
            if(lowestPercent<50) {
                msg = "Get Yer "+lowestProfileName+" Goals Done Ya Weapon!\nYer no even half way";
            }else{msg="Get Yer "+lowestProfileName+" Goals Done Ya Weapon!\nYer only " + (int) lowestPercent + "% Done";}
            notifBuilder.setContentText(msg);
            notifBuilder.setTicker("Enjoying Yer Doss, AYEEE?");
            notifBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
            notifBuilder.setAutoCancel(true);
            notifBuilder.setContentIntent(notifyIntent);

//sets up the notification settings - sets the pendingintent to call when clicked on with setcontentintent
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService
                            (Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notifBuilder.build());

            //manager service takes settings to build and execute the notification

        }




    }
}
