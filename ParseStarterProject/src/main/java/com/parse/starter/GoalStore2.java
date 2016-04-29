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
    DatabaseHelper databaseHelper;
    String profile;
    //inherit from base class. do same setup in oncreate here as in goalstore1.

    public GoalStore2(List<ClassGoal> goals, String profileName){

        databaseHelper = new DatabaseHelper(ActGoals.context);//fix this?

        list=goals;
        profile = profileName;
        Log.i("44331 sizefu", ""+list.size());
    }


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

    public void removeGoal(int i){
        list.remove(i);
    }

    public void saveToDatabase(){

        databaseHelper.clearFutureGoalsTbl(profile);

        for(int i=0; i<this.getSize(); i++){
            Log.i("44331 goalstore2future", "" + this.getSize());
            int type;
            if(this.getAt(i).type){type=1;}else{type=0;}
            ClassGoal g = this.getAt(i);
            databaseHelper.insertFutureGoal(g.profileName, g.getName(), g.getTotal(), type);
            Log.i("44331 goalstore2e22", "" + databaseHelper.getFutureGoals(g.profileName).size());
        }


        Log.i("goalstore2future", "being updated");
    }

    public boolean nameTaken(String name){

        for(int i=0; i< list.size(); i++){
            if(name.equals(list.get(i).getName())){
                return true;
            }
        }
        return false;
    }


}
