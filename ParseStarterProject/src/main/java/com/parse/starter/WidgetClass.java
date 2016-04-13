package com.parse.starter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Borris on 13/04/2016.
 */
public class WidgetClass extends AppWidgetProvider {

    SQLiteDatabase profilesDatabase;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            int currentWidgetId = appWidgetIds[i];
            String url = "http://www.tutorialspoint.com";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));

            PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);


           profilesDatabase =  context.openOrCreateDatabase("Profiles", Context.MODE_PRIVATE, null);
            profilesDatabase.execSQL("CREATE TABLE IF NOT EXISTS profilesTbl (name VARCHAR, databaseNum INT(3))");
            profilesDatabase.execSQL("CREATE TABLE IF NOT EXISTS countTbl (count INT(3))");

            Cursor c = profilesDatabase.rawQuery("SELECT * FROM profilesTbl", null);
            int nameIndex = c.getColumnIndex("name");
            int databaseNumIndex = c.getColumnIndex("databaseNum");

            ArrayList<String> names = new ArrayList<>();

            c.moveToFirst();
            boolean cancel=false;
            while (c != null&& cancel==false) {
                try {
                    Log.i("6705widget", c.getString(nameIndex));
        names.add(c.getString(nameIndex));
                    c.getInt(databaseNumIndex);
                    c.moveToNext();
                }catch(Exception e){cancel = true; Log.i("6705widget", "canceled from index out of bounds exception");}
            }


            switch(names.size()) {

                case 0:

                    break;

                case 1:
                    views.setTextViewText(R.id.title1, names.get(0));
                    break;
                case 2:
                    views.setTextViewText(R.id.title1, names.get(0));
                    views.setTextViewText(R.id.title2, names.get(1));
                    break;
                case 3:
                    views.setTextViewText(R.id.title1, names.get(0));
                    views.setTextViewText(R.id.title2, names.get(1));
                    views.setTextViewText(R.id.title3, names.get(2));
                    break;
                case 4:
                    views.setTextViewText(R.id.title1, names.get(0));
                    views.setTextViewText(R.id.title2, names.get(1));
                    views.setTextViewText(R.id.title3, names.get(2));
                    views.setTextViewText(R.id.title4, names.get(3));
                    break;
            }
            views.setOnClickPendingIntent(R.id.container, pending);
            appWidgetManager.updateAppWidget(currentWidgetId, views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }
    }
}
