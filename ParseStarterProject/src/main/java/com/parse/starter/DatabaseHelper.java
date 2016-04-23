package com.parse.starter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 22/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{



    public DatabaseHelper(Context context) {
        super(context, "goalSharkDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating tables methods
        db.execSQL("CREATE TABLE IF NOT EXISTS profilesTbl (profileName VARCHAR, refreshDay INT(3))");
        db.execSQL("CREATE TABLE IF NOT EXISTS goalsTbl (profileName VARCHAR, goalName VARCHAR, total INT(3), done INT(3)," +
                "b0 INT(1),b1 INT(1),b2 INT(1),b3 INT(1),b4 INT(1),b5 INT(1),b6 INT(1)," +
                "bt0 INT(1),bt1 INT(1),bt2 INT(1),bt3 INT(1),bt4 INT(1),bt5 INT(1),bt6 INT(1)," +
                "percent INT(3), type INT(1))");
        db.execSQL("CREATE TABLE IF NOT EXISTS futureGoalsTbl (profileName, goalName, total, type)");
        db.execSQL("CREATE TABLE IF NOT EXISTS pastTotalsTbl (profileName VARCHAR, percent INT(3), date VARCHAR)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS profilesTbl");
        db.execSQL("DROP TABLE IF EXISTS goalsTbl");
        db.execSQL("DROP TABLE IF EXISTS pastTotalsTbl");

        // create new tables
        onCreate(db);
    }

    /*
    * ---- insert row methods -----
    */


    public void insertProfile(ClassProfile profile) {

        SQLiteDatabase db = this.getWritableDatabase();//not just have a db that i access and hold at a class level??

        //ContentValues values = new ContentValues();
        //values.put("profileName", profile.name);
        //values.put("refreshDay", 366);
        //db.insert("profilesTbl", null, values);

        db.execSQL("INSERT INTO profilesTbl (profileName, refreshDay) VALUES ('" + profile.name + "', "+profile.refreshDay+")");
    }

    public void insertGoal(ClassGoal goal) {

        Log.i("44331 dbh ", ""+goal.profileName);
        SQLiteDatabase db = this.getWritableDatabase();
        int type;
        if(goal.type){type=1;}else{type=0;}
        db.execSQL("INSERT INTO goalsTbl (profileName,goalName,total,done,b0,b1,b2,b3,b4,b5,b6,bt0,bt1,bt2,bt3,bt4,bt5,bt6,percent,type) " +
                "VALUES ('"+goal.profileName+"','"+goal.name+"',"+goal.total+","+goal.done+"" +
            ","+goal.getButton(0)+","+goal.getButton(1)+","+goal.getButton(2)+","+goal.getButton(3)+","+goal.getButton(4)+","+goal.getButton(5)+","+goal.getButton(6)+
            ","+goal.buttonsThrough[0]+","+goal.buttonsThrough[1]+","+goal.buttonsThrough[2]+","+goal.buttonsThrough[3]+","+goal.buttonsThrough[4]+","+goal.buttonsThrough[5]+","+goal.buttonsThrough[6]+
            ","+goal.percent+","+type+
                ")");
    }

    public void insertFutureGoal(String profileName, String goalName, int total, int type){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO futureGoalsTbl (profileName, goalName, total, type) " +
                "VALUES (" +
                "'" +profileName+"'"+
                ",'" +goalName+"'"+
                "," +total+
                "," +type+
                ")");

    }


    public void insertPastTotal(String profileName, int percent, String date){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO pastTotalsTbl (profileName,percent,date) " +
                    "VALUES (" +
                "" +profileName+
                "" +percent+
                "" +date+
                ")");

    }



    /*
    * ---- get list methods -----
    */

    public List<ClassProfile> getProfiles() {
        List<ClassProfile> profilesList = new ArrayList<ClassProfile>();
        String selectQuery = "SELECT  * FROM profilesTbl";

        Log.e("3311", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClassProfile profile = new ClassProfile("", 777);
                profile.name = (c.getString(c.getColumnIndex("profileName")));
                profile.refreshDay = (c.getInt(c.getColumnIndex("refreshDay")));

                // adding to list
                profilesList.add(profile);
            } while (c.moveToNext());
        }

        return profilesList;
    }


    public List<ClassGoal> getGoals(String profileName) {
        List<ClassGoal> goalsList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM goalsTbl WHERE profileName LIKE '"+profileName+"'";

        Log.e("3311", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClassGoal goal = new ClassGoal("",999);
                goal.profileName = profileName;
                goal.name = (c.getString(c.getColumnIndex("goalName")));
                goal.total = (c.getInt(c.getColumnIndex("total")));
                goal.done = (c.getInt(c.getColumnIndex("done")));
                int type =(c.getInt(c.getColumnIndex("type")));
                if(type == 1){goal.type = true;}else{goal.type=false;}
                goal.setButton(0, (c.getInt(c.getColumnIndex("b0"))));
                goal.setButton(1, (c.getInt(c.getColumnIndex("b1"))));
                goal.setButton(2, (c.getInt(c.getColumnIndex("b2"))));
                goal.setButton(3, (c.getInt(c.getColumnIndex("b3"))));
                goal.setButton(4, (c.getInt(c.getColumnIndex("b4"))));
                goal.setButton(5, (c.getInt(c.getColumnIndex("b5"))));
                goal.setButton(6, (c.getInt(c.getColumnIndex("b6"))));
                goal.buttonsThrough[0] = (c.getInt(c.getColumnIndex("bt0")));
                goal.buttonsThrough[1] = (c.getInt(c.getColumnIndex("bt1")));
                goal.buttonsThrough[2] = (c.getInt(c.getColumnIndex("bt2")));
                goal.buttonsThrough[3] = (c.getInt(c.getColumnIndex("bt3")));
                goal.buttonsThrough[4] = (c.getInt(c.getColumnIndex("bt4")));
                goal.buttonsThrough[5] = (c.getInt(c.getColumnIndex("bt5")));
                goal.buttonsThrough[6] = (c.getInt(c.getColumnIndex("bt6")));



                // adding to list
                goalsList.add(goal);
            } while (c.moveToNext());
        }

        return goalsList;
    }

    public List<ClassGoal> getFutureGoals(String profileName) {
        Log.i("44331 nm1", profileName);
        List<ClassGoal> futureGoalsList = new ArrayList<ClassGoal>();
        String selectQuery = "SELECT  * FROM futureGoalsTbl WHERE profileName LIKE '"+profileName+"'";

        Log.e("3311", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ClassGoal futureGoal = new ClassGoal("",999);
                Log.i("44331 nm13", profileName);
                futureGoal.profileName = profileName;
                futureGoal.name = (c.getString(c.getColumnIndex("goalName")));
                futureGoal.total = (c.getInt(c.getColumnIndex("total")));
                int type =(c.getInt(c.getColumnIndex("type")));
                if(type == 1){futureGoal.type = true;}else{futureGoal.type=false;}

                // adding to list
                futureGoalsList.add(futureGoal);
            } while (c.moveToNext());
        }

        return futureGoalsList;
    }

    public List<Integer> getPastTotals(String profileName) {
        List<Integer> pastTotals = new ArrayList<Integer>();
        String selectQuery = "SELECT  * FROM pastTotalsTbl WHERE profileName LIKE '"+profileName+"'";

        Log.e("3311", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                pastTotals.add(c.getInt(c.getColumnIndex("percent")));
                //needs to take date too - model past totals as an object?


            } while (c.moveToNext());
        }

        return pastTotals;
    }


    /*
    * ---- update/replace row methods -----
    */

    public void updateProfileRow(String oldProfileName, String newProfileName, int refreshDay) {//should possibly take a profileObject instead - consider when in context
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE profilesTbl " +
                " SET profileName = '"+newProfileName+"',refreshDay = "+refreshDay+
                " WHERE profileName LIKE '"+oldProfileName+"'"
                );
        //could split into a profileName change and a refreshDay change method
    }


    public void updateGoalRow(ClassGoal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("44331", "g "+ goal.getButton(0));
                 db.execSQL("UPDATE goalsTbl " +
                         "SET done = " + goal.done + ", percent = " + goal.percent +
                         ", b0 = " + goal.getButton(0) +
                         ", b1 = " + goal.getButton(1) +
                         ", b2 = " + goal.getButton(2) +
                         ", b3 = " + goal.getButton(3) +
                         ", b4 = " + goal.getButton(4) +
                         ", b5 = " + goal.getButton(5) +
                         ", b6 = " + goal.getButton(6) +
                         ", bt0 = " + goal.buttonsThrough[0] +
                         ", bt1 = " + goal.buttonsThrough[1] +
                         ", bt2 = " + goal.buttonsThrough[2] +
                         ", bt3 = " + goal.buttonsThrough[3] +
                         ", bt4 = " + goal.buttonsThrough[4] +
                         ", bt5 = " + goal.buttonsThrough[5] +
                         ", bt6 = " + goal.buttonsThrough[6] +
                         " WHERE profileName LIKE '" + goal.profileName + "'" +
                         " AND goalName LIKE '" + goal.name +
                         "'");

        Log.i("44331 prof", goal.profileName);
        Log.i("44331 prof", goal.name);
        //could split into a profileName change and a refreshDay change method

    }



    /*
    * ---- method to delete a particular object - method to clear all too/parse style ones -----
    */

    public void deleteProfileRow(String profileName){
        SQLiteDatabase db = this.getWritableDatabase();

        Log.i("44331", "DELETE FROM profilesTbl " +
                "WHERE profileName LIKE '"+profileName+"'");
        db.execSQL("DELETE FROM profilesTbl " +
                "WHERE profileName LIKE '" + profileName + "'");
        db.execSQL("DELETE FROM goalsTbl " +
                "WHERE profileName LIKE '" + profileName + "'");
        db.execSQL("DELETE FROM futureGoalsTbl " +
                "WHERE profileName LIKE '"+profileName+"'");
        db.execSQL("DELETE FROM pastTotalsTbl " +
                "WHERE profileName LIKE '"+profileName+"'");

    }


    public void clearProfileTbl(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM profilesTbl");
    }
    public void clearGoalsTbl(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM goalsTbl");
    }
    public void clearGoalsTbl(String profileName){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM goalsTbl WHERE profileName Like '"+profileName+"'");
    }
    public void clearFutureGoalsTbl(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM futureGoalsTbl");
    }

    public void clearFutureGoalsTbl(String profileName){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM futureGoalsTbl WHERE profileName LIKE '"+profileName+"'");
    }


    public void clearPastTotalsTbl(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM pastTotalsTbl");
    }

    public void clearAllTbls(){

        clearProfileTbl();
        clearGoalsTbl();
        clearFutureGoalsTbl();
        clearPastTotalsTbl();

    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    /*
    * ---- count rows methods? unless the loaded datastore can deal with this itself -----
    */



    //it may be wise to have just one datastore - dont know why i'd have more than one?

    //i should model a goal and extend it to future and active goal objects

    //model past totals

    //look into methods and logic that needs changed/repositioned throughout the app - including new tables format
    // and new how datastore will be different/what it needs to hold and if it should be one single datastore?

    //add methods into goal object class, for instance, after all methods that change something to call the appropriate sql update method?
    // this can be done using the goal's name?? i think??

    //look at how parse stuff needs to be changed
    //make toJSON class to handle that




    //test that these methods work before doing way too much work on the rest of the app stuff??

    //make a 'update all goals matching profilename' method for reorder operation

    //make a 'remove all database rows from all tables method' and 'save all these values to all tables method' for parse loading down

    //make a 'close database' method to use after all methods are called from in context

}
