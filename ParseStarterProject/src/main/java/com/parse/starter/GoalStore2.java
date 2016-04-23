package com.parse.starter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 24/02/2016.
 */
public class GoalStore2 {

    List<ClassGoal> list;
    SQLiteDatabase myDatabase;
    Cursor c;
    DatabaseHelper databaseHelper;
    String profile;
    //inherit from base class. do same setup in oncreate here as in goalstore1.

    public GoalStore2(List<ClassGoal> goals, String profileName){

        databaseHelper = new DatabaseHelper(ActGoals.context);//fix this?

        list=goals;
        profile = profileName;
        Log.i("44331 sizefu", ""+list.size());
    }

   // public GoalStore2(SQLiteDatabase x) {
  //      myDatabase = x;
  //      list=new ArrayList<ClassGoal>();

 //       setUpGoalStore();

 //   }

    public boolean add(ClassGoal g) {
        g.profileName=profile;
        this.list.add(g);
        saveToDatabase();
        return true;
    }

    public ClassGoal getAt(int i){
        return list.get(i);
    }

    public void clear() {
        this.list.clear();
        databaseHelper.clearFutureGoalsTbl();

    }

    public int getSize()
    {
        return this.list.size();
    }

    public double getTotalPercentage() {
        double sum=0.0;
        for (ClassGoal g:this.list)
            sum+=g.percentNum;
        return sum;
    }

    public void saveToDatabase(){

        databaseHelper.clearFutureGoalsTbl(profile);

        for(int i=0; i<this.getSize(); i++){
            Log.i("44331 goalstore2future", "" + this.getSize());
            int type;
            if(this.getAt(i).type){type=1;}else{type=0;}
            ClassGoal g = this.getAt(i);
            databaseHelper.insertFutureGoal(g.profileName, g.name, g.total, type);
            Log.i("44331 goalstore2e22", "" + databaseHelper.getFutureGoals(g.profileName).size());
        }


        Log.i("goalstore2future", "being updated");
    }
    public void loadFromFutureDatabase(){

        this.clear();

        this.list = databaseHelper.getFutureGoals("TEST");
        Log.i("goalstore2future", "goalstore length "+this.getSize());
    }

    /*

    public void setUpGoalStore(){
        Log.i("goalstore2future", "called setupgoalstore2");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FgoalsStarted (started INT(1))");
         try {
            Cursor cur = myDatabase.rawQuery("SELECT COUNT(*) FROM FgoalsStarted", null);
            if (cur != null) {
                cur.moveToFirst();                       // Always one row returned.
                if (cur.getInt (0) == 0) {
// Zero count means empty table.
                        myDatabase.execSQL("INSERT INTO FgoalsStarted (started) VALUES (1)");
                    Log.i("goalstore2future", "started set");

                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FgoalsTbl (name VARCHAR, total INT(3), type INT(1))");
                }
                else {
//not empty table
                    Log.i("goalstore2future", "already started- moving on");
                    this.loadFromFutureDatabase();
                }}}
         catch(Exception e){e.printStackTrace();}
    }
    */

}
