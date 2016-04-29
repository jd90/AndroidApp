package com.parse.starter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Borris on 13/03/2016.
 */
public class ClassProfile {

    String name;
    int refreshDay;

    public ClassProfile(String name, int refreshDay) {

        this.name=name;
        this.refreshDay = refreshDay;


    }

    public void renameProfile(String newName){
        this.name=newName;
    }

}