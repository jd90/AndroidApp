package com.parse.starter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Borris on 24/02/2016.
 */
public class GoalStore1 {

    boolean firstweek = false;

    List<Goal> list;

    SQLiteDatabase myDatabase;
    Cursor c;

    Calendar calendar = Calendar.getInstance();
    int dayofyear;
    int day;
    ArrayList<Integer> pastTotals = new ArrayList<>();


    public GoalStore1(SQLiteDatabase x, boolean y) {
        myDatabase = x;
        list=new ArrayList<Goal>();
        //test if database exists
        //if empty fill arraylist and fill database
        //if full fill arraylist from database

        dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        day = calendar.get(Calendar.DAY_OF_WEEK);

        setUpGoalStore();
// this lets you see a graph of pastTotals if it is too early to have data

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
    public int getSize(){return this.list.size();}

    public void reorderUp(int i){

        Log.i("6705reorderU", "bef:"+list.toString());

        Goal g2 = list.get(i);
        list.remove(i);
        list.add(i - 1, g2);

        Log.i("6705reorderU", "aft:" + list.toString());
        this.saveToDatabase();
    }
    public void reorderDown(int i){

        Log.i("6705reorderD", "bef:" + list.toString());
        Goal g2 = list.get(i);
        list.remove(i);

        list.add(i + 1, g2);
        Log.i("6705reorderD", "aft:" + list.toString());
        this.saveToDatabase();
    }


    public double getTotalPercentage() {
        /**int sum=0;
        for (Goal g:this.list)
            sum+=(int) g.percent;
        return sum;
         */
        double sum =0;
        for(Goal g:this.list){
            sum+=(int) g.percent;
        Log.i("6705percent", ""+sum);
        }
        //check if this casting works/is right in a separate small netbeans window

        Log.i("6705percentfinal1", "" + (sum / this.getSize()) + (sum % this.getSize()));
        Log.i("6705percentfinal2", "" + (int) (sum / this.getSize()) + (sum % this.getSize()));

        return (int) (sum/this.getSize()); //+ (sum%this.getSize());

    }

    public void saveToDatabase(){
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
    }
    public void loadFromDatabase(){

        loadPastTotalsFromDB();

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
boolean cancel=false;
            int pos = 0;
            while (c != null&& cancel==false) {


                try {// why must i have this?? and the cancel bit too... tidy this all up

                this.add(new Goal(c.getString(nameIndex), c.getInt(totalIndex)));
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
                }catch(Exception e){cancel = true; Log.i("6705why", "canceled from index out of bounds exception");}
            }
        Log.i("6705 1 load2", "goalstore length "+this.getSize());
        }
    public void loadFromFutureDatabase(){
        Log.i("6705reset", "loadfromfuture called");
        firstweek = false;

        savePastTotalstoDB();

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
        boolean cancel = false;
        while (c != null && cancel==false) {
            try {// why must i have this?? and the cancel bit too... tidy this all up
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
            }catch(Exception e){cancel = true; Log.i("6705why", "canceled from index out of bounds exception");}

        }
        Log.i("6705 2 load2", "goalstore length "+this.getSize());
        this.saveToDatabase();//better here as its called whenever this is, rather than listing it a-new whenever loadfromfuture is called
    }
    public int daysToRefresh(){
        int daysToRefresh=0;
        switch(day) {
            case 2:daysToRefresh += 6;
                break;
            case 3:daysToRefresh += 5;
                break;
            case 4:daysToRefresh += 4;
                break;
            case 5:daysToRefresh += 3;
                break;
            case 6:daysToRefresh += 2;
                break;
            case 7:daysToRefresh += 1;
                break;
            case 1:daysToRefresh += 7;
                break;
        }
        //return daysToRefresh;
        return 1;
        //this is temporary, to test that it does a daily reset properly,
        // then it would follow that the above would work every monday, test that too after success from the daily
    }

    public void setUpGoalStore(){
        Log.i("6705 1", "called setupgoalstore1");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS goalsStarted (started INT(1))");
        try {
            Cursor cur = myDatabase.rawQuery("SELECT COUNT(*) FROM goalsStarted", null);
            if (cur != null) {
                cur.moveToFirst();                       // Always one row returned.
                if (cur.getInt (0) == 0) {
// Zero count means empty table.
                    myDatabase.execSQL("INSERT INTO goalsStarted (started) VALUES (1)");
                    Log.i("6705 2", "started set");

                    int refreshDayOfYear = dayofyear + daysToRefresh();
                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS refreshDay (day INT(1))");
                    myDatabase.execSQL("INSERT INTO refreshDay (day) VALUES (" + refreshDayOfYear + ")");

                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS pastTotalsTbl (totalPercent INT(3))");

                    pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);
                    pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);
                    pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);pastTotals.add(50);
                    pastTotals.add(50);


                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS goalsTbl (name VARCHAR, total INT(3), done INT(3), b0 INT(1),b1 INT(1),b2 INT(1),b3 INT(1),b4 INT(1),b5 INT(1),b6 INT(1), percent INT(3))");
                    //this should make a "nae goals started" screen

                    firstweek = true;

                }
                else {

                    //firstweek = false;//this is possibly redundant - as the data does not persist
//not empty table
                    Log.i("6705 2", "already started- moving on");




//this could likely be a shorter query, single result etc... rather than needing a cursor?
                    c = myDatabase.rawQuery("SELECT * FROM refreshDay", null);
                    int refreshIndex = c.getColumnIndex("day");
                    c.moveToFirst();
                    int refreshDay =c.getInt(refreshIndex);

Log.i("6705 today", ""+dayofyear);
Log.i("6705 refreshdate", ""+refreshDay);
                    if (dayofyear >= refreshDay) {
                        //refresh goals
                        //and refresh refreshDay
Log.i("6705reset", "day is greater");
                        int newTotal = (int) this.getTotalPercentage();
                     //   myDatabase.execSQL("CREATE TABLE IF NOT EXISTS pastTotalsTbl (totalPercent INT(3))");
                        //if this was empty - it would mean that app has started, but one week
                        // has not passed yet.. could set a boolean for first week or something? - need to be put above this tho - in the isnt passsed refresh check
                      //  myDatabase.execSQL("INSERT INTO pastTotalsTbl (totalPercent) VALUES (" + newTotal + ")");

                        Log.i("6705reset", "day is greater2");

                        this.loadFromFutureDatabase();

                        refreshDay = dayofyear + daysToRefresh();

                        if (refreshDay > 365) {
                            refreshDay -= 365;
                        }
                        myDatabase.execSQL("delete from refreshDay");
                        myDatabase.execSQL("INSERT INTO refreshDay (day) VALUES ("+refreshDay+")");

                        loadPastTotalsFromDB();

                    } else{
                        //dont refresh goals
                        this.loadFromDatabase();
                    }
                }}}
        catch(Exception e){e.printStackTrace();}
    }








    public void loadPastTotalsFromDB(){

        Cursor c = myDatabase.rawQuery("SELECT * FROM pastTotalsTbl", null);
        int totalsIndex = c.getColumnIndex("totalPercent");
        c.moveToFirst();
        boolean cancel = false;

        while (c != null && cancel == false) {
            try{
                pastTotals.add(c.getInt(totalsIndex));
            c.moveToNext();
            }catch(Exception e){cancel = true; Log.i("6705why", "canceled from index out of bounds exception");}
        }
    }

    public void savePastTotalstoDB(){

        if(this.list.size() >0){
//if its greater than zero... so that it doesnt save when you are first starting your goals profile
            pastTotals.remove(0);
            pastTotals.add(15, (int)this.getTotalPercentage());


        }

        myDatabase.execSQL("delete from pastTotalsTbl");

        for(int i=0; i<pastTotals.size(); i++){
            myDatabase.execSQL("INSERT INTO pastTotalsTbl (totalPercent) VALUES (" + pastTotals.get(i) + ")");
        }






    }



}
