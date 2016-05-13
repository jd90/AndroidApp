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

            if ((dayofyear - refreshDay) / 7 > 0) {
                emptyweeks = (dayofyear - refreshDay) / 7;
            } else {
                emptyweeks = 0;
            }

            double percent = ActGoals.goalStore.getTotalPercentage();
            list.remove(0);
            if (percent > 100) {percent = 100;}
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, ActGoals.p.getRefreshDay());
        String s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

        s += "" + calendar.get(Calendar.DAY_OF_MONTH);
        ClassArchiveItem archiveItem = new ClassArchiveItem((int) percent, s);
        list.add(15, archiveItem);

        int y = ActGoals.p.getRefreshDay();
        calendar = Calendar.getInstance();
        for(int i =1; i<= emptyweeks; i++) {

            list.remove(0);
            y += 7;
            calendar.set(Calendar.DAY_OF_YEAR, y);
            s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

            s += "" + calendar.get(Calendar.DAY_OF_MONTH);

            archiveItem = new ClassArchiveItem(0, s);
            list.add(15, archiveItem);

        }
    }

}
