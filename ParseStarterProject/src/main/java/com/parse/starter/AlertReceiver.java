package com.parse.starter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Borris on 08/05/2016.
 */
public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {



        PendingIntent notifyIntent = PendingIntent.getActivity(context, 0, new
                Intent(context, ActProfiles.class), 0);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context);
        notifBuilder.setSmallIcon(R.drawable.goal_shark_logo1);
        notifBuilder.setContentTitle("Enjoying Yer Doss, Aye?");
        notifBuilder.setContentText("Get Yer Goals Done Ya Weapon!");
        notifBuilder.setTicker("Enjoying Yer Doss, AYEEE?");
        notifBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        notifBuilder.setAutoCancel(true);
        notifBuilder.setContentIntent(notifyIntent);


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService
                        (Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notifBuilder.build());

/*

    @Override
    public void onReceive(Context context, Intent intent) {

        //called when broadcast is set
        Log.i("notifyyyy", "called 1");

        createNotification(context, "Let's Go", "nada", "AAAAHH");

    }

    public void createNotification(Context context, String message, String messageText, String messageAlert){

        Log.i("notifyyyy", "called 2");

        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0, new Intent(context, ActProfiles.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.goal_shark_logo1)
                .setContentTitle(message)
                .setTicker(messageAlert)
                .setContentText(messageText);

        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, mBuilder.build());


    }

*/
    }
}
