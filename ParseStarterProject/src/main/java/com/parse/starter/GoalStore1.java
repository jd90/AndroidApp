package com.parse.starter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Borris on 24/02/2016.
 */
public class GoalStore1 {

    static List<ClassGoal> list;

    boolean firstweek = false;
    static int dayofyear;
    String profile;
    DatabaseHelper databaseHelper;

    public GoalStore1(List<ClassGoal> goals, String profileName){


        databaseHelper = new DatabaseHelper(ActGoals.context);
        list = goals;
        profile = profileName;
        //refreshDay =

        Log.i("44331 sizego", ""+list.size());
        //this.list = databaseHelper.getGoals(profile);


    }

    public boolean add(ClassGoal g) {
        this.list.add(g);

        return true;
    }
    public ClassGoal getAt(int i){
        return list.get(i);
    }

    public int getSize(){return this.list.size();}

    public void reorderUp(int i){
        ClassGoal g2 = list.get(i);
        list.remove(i);
        list.add(i - 1, g2);

        this.saveToDatabase();
    }
    public void reorderDown(int i){

        Log.i("6705reorderD", "bef:" + list.toString());
        ClassGoal g2 = list.get(i);
        list.remove(i);

        list.add(i + 1, g2);
        Log.i("6705reorderD", "aft:" + list.toString());
        this.saveToDatabase();
    }

    public double getTotalPercentage() {
        double sum =0;
        for(ClassGoal g: list){
            sum+=(int) g.getPercentage2();
        Log.i("6705percent", ""+sum);
        }

        return (int) (sum/this.getSize()); //+ (sum%this.getSize());
    }

    public void updateGoal(int gpos, int bpos){

        Log.i("44331", "saved" + list.get(gpos).getButton(bpos));



        databaseHelper.updateGoalRow(list.get(gpos));

        Log.i("44331", "lo2 " + databaseHelper.getGoals(profile).size());

        Log.i("44331", "loaded" + databaseHelper.getGoals(profile).get(gpos).getButton(bpos));
        Log.i("44331", "loaded" + databaseHelper.getGoals(profile).get(gpos).name);


    }

    public void saveToDatabase(){

        databaseHelper.clearGoalsTbl(profile);

        for(int i=0; i< list.size(); i++){

            Log.i("44331", "saving"+list.get(i).getButton(0));

            databaseHelper.insertGoal(list.get(i));

        }

        List<ClassGoal> g = databaseHelper.getGoals(profile);
//        Log.i("44331 saved val", " " + g.get(0).getButton(0));

    }


    public boolean nameTaken(String name){

        for(int i=0; i< list.size(); i++){
            if(name.equals(list.get(i).name)){
                return true;
            }
        }
        return false;
    }



}