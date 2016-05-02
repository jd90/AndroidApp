package com.parse.starter;

import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Borris on 24/04/2016.
 */
public class ArchiveItemDatastore {

    ArrayList<ClassArchiveItem> list;


    public ArchiveItemDatastore(){

        list= new ArrayList<ClassArchiveItem>();

    }

    public void addProfile(ClassArchiveItem archiveItem){

        list.add(archiveItem);
    }


    public void updateList(int refreshDay, int dayofyear){

    int emptyweeks;

            Log.i("8888", "moved to future load");
            if ((dayofyear - refreshDay) / 1 > 0) {//should eventually be seven 7
                emptyweeks = (dayofyear - refreshDay) / 1;//should eventually be seven 7
            } else {
                emptyweeks = 0;
            }
        Log.i("pastt", "refresh: "+emptyweeks);


            double percent = ActGoals.goalStore.getTotalPercentage();
            list.remove(0);
            if (percent > 100) {percent = 100;}
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, ActGoals.p.refreshDay);
        String s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

        s += "" + calendar.get(Calendar.DAY_OF_MONTH);
        ClassArchiveItem archiveItem = new ClassArchiveItem((int) percent, s);
        list.add(15, archiveItem);

        int x = emptyweeks;
        int y = ActGoals.p.refreshDay;
        calendar = Calendar.getInstance();
        for(int i =1; i<= emptyweeks; i++) {

            Log.i("pastt", "i: "+i);

            list.remove(0);

            y += 1;
            //Log.i("pasttY", s);
            calendar.set(Calendar.DAY_OF_YEAR, y);//should eventually be seven x*7?
            s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

            s += "" + calendar.get(Calendar.DAY_OF_MONTH);

            Log.i("pasttotes", s);
            archiveItem = new ClassArchiveItem(0, s);
            list.add(15, archiveItem);

        }
    }

}
