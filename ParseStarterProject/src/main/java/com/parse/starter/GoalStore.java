

package com.parse.starter;


/**
 * Created by Borris on 21/02/2016.
 */
/*
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GoalStore {

    List<ClassGoal> list;

    SQLiteDatabase myDatabase;
    Cursor c;

    boolean whichDatastore;

    public GoalStore(SQLiteDatabase x, boolean y) {
        myDatabase = x;
        list=new ArrayList<ClassGoal>();
        //test if database exists
        //if empty fill arraylist and fill database
        //if full fill arraylist from database
        whichDatastore = y;
        setUpGoalStore();


    }

    public boolean add(ClassGoal g) {


        this.list.add(g);
        return true;
    }

    public ClassGoal getAt(int i){
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
        for (ClassGoal g:this.list)
            sum+=g.percentNum;
        return sum;
    }


    public void saveToDatabase(){

        //Log.d("before updateGoalStore", "" + this.getAt(position).done);
        myDatabase.execSQL("delete from goalsTbl");
        for(int i=0; i<this.getSize(); i++){
            myDatabase.execSQL("INSERT INTO goalsTbl (name, done, total, b0,b1,b2,b3,b4,b5,b6, percent) VALUES ('"
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
        //Log.d("after updateGoalStore", "" + this.getAt(position).done);


    }
    public void loadFromDatabase(){

        if(whichDatastore) {
            this.clear();

            c = myDatabase.rawQuery("SELECT * FROM goalsTbl", null);
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
                Log.i("JOASHNAME", c.getString(nameIndex));
                Log.i("JOASHNAME2", "" + c.getInt(totalIndex));
                Log.i("JOASHNAME2", "" + c.getInt(doneIndex));
                Log.i("JOASHNAME2", "" + c.getDouble(percentIndex));
                Log.i("JOASHNAME3", "" + this.getAt(0).name);

                this.add(new ClassGoal(c.getString(nameIndex), c.getInt(totalIndex)));
                //this.getAt(pos).name = c.getString(nameIndex);
                //this.getAt(pos).total = c.getInt(totalIndex);
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

            Log.i("JOASH", "goalstore length "+this.getSize());
        }
            else{


                c = myDatabase.rawQuery("SELECT * FROM goalsTbl", null);
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
                    Log.i("JOASHNAME", c.getString(nameIndex));
                    Log.i("JOASHNAME2", "" + c.getInt(totalIndex));
                    Log.i("JOASHNAME2", "" + c.getInt(doneIndex));
                    Log.i("JOASHNAME2", "" + c.getDouble(percentIndex));
                    Log.i("JOASHNAME3", "" + this.getAt(0).name);

                    this.getAt(pos).name = c.getString(nameIndex);
                    this.getAt(pos).total = c.getInt(totalIndex);
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
            }

    }


    public void setUpGoalStore(){


        this.add(new ClassGoal("ClassGoal one", 5));
        this.add(new ClassGoal("ClassGoal 2", 5));
        this.add(new ClassGoal("ClassGoal 3", 5));
        this.add(new ClassGoal("ClassGoal 4", 5));
        this.add(new ClassGoal("ClassGoal 5", 5));
        this.add(new ClassGoal("ClassGoal 6", 5));
        this.add(new ClassGoal("ClassGoal 7", 5));

        Log.i("6705", "called setupgoalstore");

//        myDatabase = this.openOrCreateDatabase("Goals", MODE_PRIVATE, null);

        //remove thismyDatabase.execSQL("DROP TABLE IF EXISTS goalsTbl");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS goalsTbl (name VARCHAR, total INT(3), done INT(3), b0 INT(1),b1 INT(1),b2 INT(1),b3 INT(1),b4 INT(1),b5 INT(1),b6 INT(1), percent INT(3), id INT PRIMARY KEY)");
        try {

            //this cursor is for my update query?
            //if changing table, must clear first//myDatabase.execSQL("delete from goalsTbl");
            Cursor cur = myDatabase.rawQuery("SELECT COUNT(*) FROM goalsTbl", null);
            if (cur != null) {
                cur.moveToFirst();                       // Always one row returned.
                if (cur.getInt (0) == 0) {        // Zero count means empty table.


                    for(int i=0; i<this.getSize(); i++){
                        //should remove the primary key from here?

                        myDatabase.execSQL("INSERT INTO goalsTbl (name, done, total, b0, b1, b2, b3, b4, b5, b6, percent) VALUES ('"
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
                    //Log.i("goalsnull", "being filled");
                }
                else
                {this.loadFromDatabase();}
            }
        }
        catch(Exception e){e.printStackTrace();}
/**
 //for(int i=0;i<goalStore.getSize();i++) {
 //Log.i("goalsinsto",  goalStore.getAt(i).name);
 //}

    };

}


*/