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

    List<Goal> list;
    SQLiteDatabase myDatabase;
    Cursor c;


    public GoalStore2(SQLiteDatabase x) {
        myDatabase = x;
        list=new ArrayList<Goal>();

        setUpGoalStore();

    }

    public boolean add(Goal g) {
        this.list.add(g);
        return true;
    }

    public Goal getAt(int i){
        return list.get(i);
    }

    public void clear() {
        this.list.clear();
    }

    public int getSize()
    {
        return this.list.size();
    }

    public double getTotalPercentage() {
        double sum=0.0;
        for (Goal g:this.list)
            sum+=g.percentNum;
        return sum;
    }

    public void saveToDatabase(){

        myDatabase.execSQL("delete from FgoalsTbl");
        for(int i=0; i<this.getSize(); i++){
            Log.i("6705 2 saving", "" + this.getSize());
            myDatabase.execSQL("INSERT INTO FgoalsTbl (name, total) VALUES ('"
                    +this.getAt(i).name+"', "
                    +this.getAt(i).total+")");
        }

        Log.i("goalsnull", "being updated");
    }
    public void loadFromFutureDatabase(){
        this.clear();
        c = myDatabase.rawQuery("SELECT * FROM FgoalsTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int totalIndex = c.getColumnIndex("total");
        c.moveToFirst();
        int pos = 0;
        while (c != null) {
            Log.i("6705 2 load1", c.getString(nameIndex));

            this.add(new Goal(c.getString(nameIndex), c.getInt(totalIndex)));
            pos++;
            c.moveToNext();
        }

        Log.i("6705 2 load2", "goalstore length "+this.getSize());
    }


    public void setUpGoalStore(){
        Log.i("6705 2", "called setupgoalstore2");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FgoalsStarted (started INT(1))");
         try {
            Cursor cur = myDatabase.rawQuery("SELECT COUNT(*) FROM FgoalsStarted", null);
            if (cur != null) {
                cur.moveToFirst();                       // Always one row returned.
                if (cur.getInt (0) == 0) {
// Zero count means empty table.
                        myDatabase.execSQL("INSERT INTO FgoalsStarted (started) VALUES (1)");
                    Log.i("6705 2", "started set");

                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FgoalsTbl (name VARCHAR, total INT(3))");
                }
                else {
//not empty table
                    Log.i("6705 2", "already started- moving on");
                    this.loadFromFutureDatabase();
                }}}
         catch(Exception e){e.printStackTrace();}
    }

}
