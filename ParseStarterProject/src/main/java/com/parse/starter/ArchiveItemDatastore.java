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

        int x = emptyweeks;

            for(int i =0; i< emptyweeks; i++) {
                list.remove(0);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, ActGoals.p.refreshDay - x);//should eventually be seven 7
                String s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

                s += "" + calendar.get(Calendar.DAY_OF_MONTH);
                ClassArchiveItem archiveItem = new ClassArchiveItem(0, s);
                list.add(15, archiveItem);
                x--;
            }


            double percent = ActGoals.goalStore.getTotalPercentage();
            list.remove(0);
            if (percent > 100) {percent = 100;}
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, ActGoals.p.refreshDay);//should eventually be seven 7
        String s = "" + new SimpleDateFormat("MMM").format(calendar.getTime()) + " ";

        s += "" + calendar.get(Calendar.DAY_OF_MONTH);
        ClassArchiveItem archiveItem = new ClassArchiveItem((int) percent, s);
        list.add(15, archiveItem);


    }

}
