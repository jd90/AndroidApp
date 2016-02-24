package com.parse.starter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static GoalStore1 goalStore;
    static GoalStore2 fgoalStore;
    static SQLiteDatabase myDatabase;
    static SQLiteDatabase myDatabase2;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        myDatabase = this.openOrCreateDatabase("GoalApp", MODE_PRIVATE, null);
        goalStore = new GoalStore1(myDatabase, false);

//this is not yet tested through -- but maybe it naturally already works. it is a new database.
        //ideally, i think datastore should take the same database and and also a String name which can be the database table name.
        //then it might just naturally work? then on certain dates tbl1 can be set to tbl2 and tbl2 can be wipe? or stay as tbl2 i guess.
        myDatabase2 = this.openOrCreateDatabase("GoalApp", MODE_PRIVATE, null);
        fgoalStore = new GoalStore2(myDatabase2, true);
        //setUpGoalStore();
        setUpPage();


    }
/*
    public void retrieveGoalstoreFromDATABASE(){

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

        int pos =0;
        while (c != null) {
            Log.i("JOASHNAME", c.getString(nameIndex));
            Log.i("JOASHNAME2", ""+c.getInt(totalIndex));
            Log.i("JOASHNAME2", ""+c.getInt(doneIndex));
            Log.i("JOASHNAME2", ""+c.getDouble(percentIndex));
            Log.i("JOASHNAME3", ""+goalStore.getAt(0).name);
            goalStore.getAt(pos).name=c.getString(nameIndex);
            goalStore.getAt(pos).total=c.getInt(totalIndex);
            goalStore.getAt(pos).done=c.getInt(doneIndex);
            goalStore.getAt(pos).percent=c.getDouble(percentIndex);
            goalStore.getAt(pos).setButton(0, c.getInt(b0Index));
            goalStore.getAt(pos).setButton(1, c.getInt(b1Index));
            goalStore.getAt(pos).setButton(2, c.getInt(b2Index));
            goalStore.getAt(pos).setButton(3, c.getInt(b3Index));
            goalStore.getAt(pos).setButton(4, c.getInt(b4Index));
            goalStore.getAt(pos).setButton(5, c.getInt(b5Index));
            goalStore.getAt(pos).setButton(6, c.getInt(b6Index));
            pos++;
            c.moveToNext();
        }

    }

    public static void updateGoalStore(int position) {

        Log.d("before updateGoalStore", "" + goalStore.getAt(position).done);
        myDatabase.execSQL("delete from goalsTbl");
        for(int i=0; i<goalStore.getSize(); i++){
            myDatabase.execSQL("INSERT INTO goalsTbl (name, done, total, b0,b1,b2,b3,b4,b5,b6, percent) VALUES ('"
                    +goalStore.getAt(i).name+"', "
                    +goalStore.getAt(i).done+", "
                    +goalStore.getAt(i).total+", "
                    +goalStore.getAt(i).getButton(0) +", "
                    +goalStore.getAt(i).getButton(1) +", "
                    +goalStore.getAt(i).getButton(2) +", "
                    +goalStore.getAt(i).getButton(3) +", "
                    +goalStore.getAt(i).getButton(4) +", "
                    +goalStore.getAt(i).getButton(5) +", "
                    +goalStore.getAt(i).getButton(6) +", "
                    + goalStore.getAt(i).percent+")");
        }

        Log.i("goalsnull", "being updated");
        Log.d("after updateGoalStore", "" + goalStore.getAt(position).done);
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//this is set in menu_main as an item
            case R.id.action_next_seven:
                Intent intent = new Intent(this, FutureGoals.class);
                int a = 4; //request code? (receives back request code, resultcode, intent)?
                startActivityForResult(intent, a);

                break;
        }

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int a =4;
        if (resultCode == RESULT_OK && requestCode == 4) {
            if (data.hasExtra("returnKey1")) {
                Toast.makeText(this, data.getExtras().getString("returnKey1"),
                        Toast.LENGTH_SHORT).show();}}
    }

/*
    public void setUpGoalStore(){

        goalStore=new GoalStore();
        goalStore.list.add(new Goal("Goal one", 5));
        goalStore.list.add(new Goal("Goal 2", 5));
        goalStore.list.add(new Goal("Goal 3", 5));
        goalStore.list.add(new Goal("Goal 4", 5));
        goalStore.list.add(new Goal("Goal 5", 5));
        goalStore.list.add(new Goal("Goal 6", 5));
        goalStore.list.add(new Goal("Goal 7", 5));

        myDatabase = this.openOrCreateDatabase("Goals", MODE_PRIVATE, null);
        //remove thismyDatabase.execSQL("DROP TABLE IF EXISTS goalsTbl");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS goalsTbl (name VARCHAR, total INT(3), done INT(3), b0 INT(1),b1 INT(1),b2 INT(1),b3 INT(1),b4 INT(1),b5 INT(1),b6 INT(1), percent INT(3), id INT PRIMARY KEY)");
        try {
            //this cursor is for my update query?
            //if changing table, must clear first//myDatabase.execSQL("delete from goalsTbl");
            Cursor cur = myDatabase.rawQuery("SELECT COUNT(*) FROM goalsTbl", null);
            if (cur != null) {
                cur.moveToFirst();                       // Always one row returned.
                if (cur.getInt (0) == 0) {               // Zero count means empty table.
                    for(int i=0; i<goalStore.getSize(); i++){
                        //should remove the primary key from here?
                        myDatabase.execSQL("INSERT INTO goalsTbl (name, done, total, b0, b1, b2, b3, b4, b5, b6, percent) VALUES ('"
                                +goalStore.getAt(i).name+"', "
                                +goalStore.getAt(i).done+", "
                                +goalStore.getAt(i).total+", "
                                +goalStore.getAt(i).getButton(0) +", "
                                +goalStore.getAt(i).getButton(1) +", "
                                +goalStore.getAt(i).getButton(2) +", "
                                +goalStore.getAt(i).getButton(3) +", "
                                +goalStore.getAt(i).getButton(4) +", "
                                +goalStore.getAt(i).getButton(5) +", "
                                +goalStore.getAt(i).getButton(6) +", "
                                +goalStore.getAt(i).percent+")");
                    }
                    //Log.i("goalsnull", "being filled");
                }
                else
                {retrieveGoalstoreFromDATABASE();}
            }
        }
        catch(Exception e){e.printStackTrace();}

 //for(int i=0;i<goalStore.getSize();i++) {
 //Log.i("goalsinsto",  goalStore.getAt(i).name);
 //}

    };
*/
    public void setUpPage(){
        //this works because styles requests a noActionBar style and actionbar is set to false
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        // Setup the Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        //tab selection events update the ViewPager and page changes update the selected tab.
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }

}