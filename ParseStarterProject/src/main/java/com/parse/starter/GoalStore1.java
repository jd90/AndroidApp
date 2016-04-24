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
    List<Integer> pastTotalsList;


    SQLiteDatabase myDatabase;
    Cursor c;



    ArrayList<Integer> pastTotals = new ArrayList<>();


    ArrayList<String> pastDates = new ArrayList<>();
    boolean firstweek = false;
    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    static int dayofyear;
    static int day;
    int refreshDay;
    int emptyweeks;
    String profile;
    static Boolean holidaymode = false;
    DatabaseHelper databaseHelper;

    public GoalStore1(List<ClassGoal> goals, String profileName){


        databaseHelper = new DatabaseHelper(ActGoals.context);
        list = goals;
        profile = profileName;
        //refreshDay =

        Log.i("44331 sizego", ""+list.size());
        //this.list = databaseHelper.getGoals(profile);


    }


   /// public GoalStore1(SQLiteDatabase x) {
  //      myDatabase = x;
  //      list=new ArrayList<ClassGoal>();

  //      setDayVariables();

 //       setUpGoalStore();

   //     if(refreshDay >365){
    //        holidaymode =(true);
   //     }else{

   //         holidaymode = (false);
   //     }
   // }


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
        Log.i("44331 saved val", " " + g.get(0).getButton(0));

    }
    public void loadFromDatabase(){

        loadPastTotalsFromDB();

        this.clear();

        this.list= databaseHelper.getGoals(profile);
        //this.pastTotals= databaseHelper.getPastTotals(profile);

        }
    public void loadFromFutureDatabase(){

        if(!firstweek){
            loadFromDatabase();
            loadPastTotalsFromDB();
            Log.i("8888", "not first week");
            savePastTotalstoDB();}

        firstweek = false;

        Log.i("8888", "outside loop1");

        this.clear();
        this.list = databaseHelper.getFutureGoals(profile);


        this.saveToDatabase();//better here as its called whenever this is, rather than listing it a-new whenever loadfromfuture is called
    }
    public static int daysToRefresh(){
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
        //return daysToRefresh; // also change in settingsScrn
        return 1;
    }

    public void setUpGoalStore(){

        if(!firstweek) {
            calendar2.set(Calendar.DAY_OF_YEAR, refreshDay);
            String s = "" + new SimpleDateFormat("MMM").format(calendar2.getTime()) + " ";
            s += "" + calendar2.get(Calendar.DAY_OF_MONTH);
            //ArchiveItemDatastore archiveItemDatastore;
            //archiveItemDatastore.updateList();
        }

                    firstweek = true;


                  /*  c = myDatabase.rawQuery("SELECT * FROM refreshDay", null);
                    int refreshIndex = c.getColumnIndex("day");
                    c.moveToFirst();
                    refreshDay =c.getInt(refreshIndex);

                    Log.i("emptyweeks1re", ""+refreshDay);

                    if (dayofyear >= refreshDay) {

                        Log.i("8888", "moved to future load");
                        if((dayofyear-refreshDay)/1 > 0) {//should eventually be seven 7
                            emptyweeks = (dayofyear - refreshDay) / 1;//should eventually be seven 7
                        }else{emptyweeks =0;}
*/


                        this.loadFromFutureDatabase();


/*
                        Log.i("emptyweeks1em", ""+emptyweeks);
                        refreshDay = dayofyear + daysToRefresh();
                        if (refreshDay > 365) {
                            refreshDay -= 365;
                        }
                        myDatabase.execSQL("delete from refreshDay");
                        myDatabase.execSQL("INSERT INTO refreshDay (day) VALUES (" + refreshDay + ")");
                    } else{
                        Log.i("8888", "moved to load");
                        this.loadFromDatabase();
                    }
                }}}
        catch(Exception e){e.printStackTrace();}

        */

    }

    public void setDayVariables(){
        dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        day = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public void loadPastTotalsFromDB(){

        Cursor c = myDatabase.rawQuery("SELECT * FROM pastTotalsTbl", null);
        int totalsIndex = c.getColumnIndex("totalPercent");
        int dateIndex = c.getColumnIndex("date");
        c.moveToFirst();

        pastTotals.clear();
        pastDates.clear();
        boolean cancel = false;
        while (c != null && cancel == false) {
            try{

                pastTotals.add(c.getInt(totalsIndex));
                pastDates.add(c.getString(dateIndex));
            c.moveToNext();
            }catch(Exception e){cancel = true; Log.i("6705whyPASTTOTALSgstore", "canceled index out of bounds exception");}
        }
    }

    public void savePastTotalstoDB() {

        Log.i("8888", "" + pastTotals.size());
        pastTotals.remove(0);
        pastDates.remove(0);
        double percent;
        if(this.getTotalPercentage() >100){
            percent = 100;}
        else{percent = this.getTotalPercentage();}
        pastTotals.add(15, (int) percent);
        calendar2.set(Calendar.DAY_OF_YEAR, refreshDay);
        String s = ""+ new SimpleDateFormat("MMM").format(calendar2.getTime()) + " ";
        s+= ""+calendar2.get(Calendar.DAY_OF_MONTH);
        pastDates.add(15, s);
        myDatabase.execSQL("delete from pastTotalsTbl");

        //can possibly remove this and do it once at the end??
        for (int i = 0; i < pastTotals.size(); i++) {
            myDatabase.execSQL("INSERT INTO pastTotalsTbl (totalPercent, date) VALUES (" + pastTotals.get(i) + ", '"+ pastDates.get(i)+"')");

        }







        Log.i("8888", "" + pastTotals.size());

        Log.i("emptyweeks", "" + emptyweeks);

            int x = 0;
            while(x != emptyweeks) {
                pastTotals.remove(0);
                pastDates.remove(0);
                percent = 0;
                pastTotals.add(15, (int) percent);
                refreshDay+=1;//should eventually be seven 7
                calendar2.set(Calendar.DAY_OF_YEAR, refreshDay);
                s = ""+ new SimpleDateFormat("MMM").format(calendar2.getTime()) + " ";
                s+= ""+calendar2.get(Calendar.DAY_OF_MONTH);
                pastDates.add(15, s);


            x++;
            }//i removed this from the loop?? incase things dont work right
        myDatabase.execSQL("delete from pastTotalsTbl");

        for (int i = 0; i < pastTotals.size(); i++) {
            myDatabase.execSQL("INSERT INTO pastTotalsTbl (totalPercent, date) VALUES (" + pastTotals.get(i) + ", '" + pastDates.get(i)+"')");

        }

    }

    public void setHolidayMode(Boolean mode){

        if(mode){
            refreshDay = 366; //canna be reached

        }else{

            refreshDay = dayofyear + daysToRefresh();//refreshes it to restart, ye'll have a dead few days tho... possibly fine, could maybe change it to be a specialised first week if i have time
            if (refreshDay > 365) {
                refreshDay -= 365;
            }
        }
        myDatabase.execSQL("delete from refreshDay");
        myDatabase.execSQL("INSERT INTO refreshDay (day) VALUES (" + refreshDay + ")");

    }


}