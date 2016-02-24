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

    boolean whichDatastore;

    public GoalStore2(SQLiteDatabase x, boolean y) {
        myDatabase = x;
        list=new ArrayList<Goal>();
        //test if database exists
        //if empty fill arraylist and fill database
        //if full fill arraylist from database
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
            myDatabase.execSQL("INSERT INTO FgoalsTbl (name, done, total, b0,b1,b2,b3,b4,b5,b6, percent) VALUES ('"
                    +this.getAt(i).name+"', "
                    +this.getAt(i).done+", "
                    +this.getAt(i).total+", "
                    +this.getAt(i).getButton(0) +", "
                    +this.getAt(i).getButton(1) +", "
                    +this.getAt(i).getButton(2) +", "
                    +this.getAt(i).getButton(3) +", "
                    +this.getAt(i).getButton(4) +", "
                    +this.getAt(i).getButton(5) +", "
                    +this.getAt(i).getButton(6) +", "
                    +this.getAt(i).percent+")");
        }

        Log.i("goalsnull", "being updated");
    }
    public void loadFromFutureDatabase(){
        this.clear();
        c = myDatabase.rawQuery("SELECT * FROM FgoalsTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int totalIndex = c.getColumnIndex("total");
        int doneIndex = c.getColumnIndex("done");
        int percentIndex = c.getColumnIndex("percent");
        int b0Index = c.getColumnIndex("b0");
        int b1Index = c.getColumnIndex("b1");
        int b2Index = c.getColumnIndex("b2");
        int b3Index = c.getColumnIndex("b3");
        int b4Index = c.getColumnIndex("b4");
        int b5Index = c.getColumnIndex("b5");
        int b6Index = c.getColumnIndex("b6");
        c.moveToFirst();
        int pos = 0;
        while (c != null) {
            Log.i("6705 2 load1", c.getString(nameIndex));

            this.add(new Goal(c.getString(nameIndex), c.getInt(totalIndex)));
            this.getAt(pos).done = c.getInt(doneIndex);
            this.getAt(pos).percent = c.getDouble(percentIndex);
            this.getAt(pos).setButton(0, c.getInt(b0Index));
            this.getAt(pos).setButton(1, c.getInt(b1Index));
            this.getAt(pos).setButton(2, c.getInt(b2Index));
            this.getAt(pos).setButton(3, c.getInt(b3Index));
            this.getAt(pos).setButton(4, c.getInt(b4Index));
            this.getAt(pos).setButton(5, c.getInt(b5Index));
            this.getAt(pos).setButton(6, c.getInt(b6Index));
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

                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS FgoalsTbl (name VARCHAR, total INT(3), done INT(3), b0 INT(1),b1 INT(1),b2 INT(1),b3 INT(1),b4 INT(1),b5 INT(1),b6 INT(1), percent INT(3))");
                }
                else {
//not empty table
                    Log.i("6705 2", "already started- moving on");
                    this.loadFromFutureDatabase();
                }}}
         catch(Exception e){e.printStackTrace();}
    }

}
