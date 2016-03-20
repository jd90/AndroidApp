package com.parse.starter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Borris on 13/03/2016.
 */
public class Profile {

    String name;
    int databaseNum;

    public Profile(String name, int database) {

        this.name=name;
        this.databaseNum=database;


        //could create database and goalStore right here? probably should...
        //i would then open dictate the correct goalstore by using the databaseNum?
    }

    public void renameProfile(String newName){
        this.name=newName;
    }

}