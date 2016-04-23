package com.parse.starter;

import android.content.Context;
import android.content.Intent;
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

import java.util.Calendar;

public class ActGoals extends AppCompatActivity {

    static GoalStore1 goalStore;
    static GoalStore2 fgoalStore;
    static SQLiteDatabase myDatabase;
    static Context context;
    static String profile;
    static int profileNum;
    static MenuItem holiday;
    static Menu menu;
    DatabaseHelper databaseHelper;
    int dayofyear;
    static int day;
    Calendar calendar = Calendar.getInstance();
    ClassProfile p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =this.getApplicationContext();

        dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        day = calendar.get(Calendar.DAY_OF_WEEK);

        profile = getIntent().getExtras().getString("profileName");

        databaseHelper = new DatabaseHelper(this);
        Log.i("44331 buttonprobbef", "size "+databaseHelper.getGoals(profile).size());
        goalStore = new GoalStore1(databaseHelper.getGoals(profile), profile);

        Log.i("44331 emptysear", ""+databaseHelper.getFutureGoals(profile).size());
        Log.i("44331 name2", profile);

        fgoalStore = new GoalStore2(databaseHelper.getFutureGoals(profile), profile);
        goalStore.pastTotalsList = databaseHelper.getPastTotals(profile);

        Log.i("44331 buttonprob", "size "+databaseHelper.getGoals(profile).size());
        Log.i("44331 buttonprob", "prof " + profile);


        p =ActProfiles.profileDatastore.getProfile(profile);
        Log.i("44331 refreshday", ""+p.refreshDay);

        if(p.refreshDay==888){
            goalStore.firstweek=true;
        }else{goalStore.firstweek=false;}
        //fix - combine these two?
        if(goalStore.firstweek){

            Log.i("6705firstweek", "first week called");
            Intent intent = new Intent(this, ActFutureGoals.class);
            int a = 4; //request code? (receives back request code, resultcode, intent)?
            intent.putExtra("firstweek", true);
            intent.putExtra("profileName", profile);
            startActivityForResult(intent, a);
            //startActivity(intent);
         //goalStore.loadFromFutureDatabase();//one time on first load
      }

        if(p.refreshDay< dayofyear){

            //should also do the past totals thing here
            //and save a new refreshday
            goalStore.list = databaseHelper.getFutureGoals(profile);

        }


        setUpPage();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menu = menu;//overcame an issue here, was trying to find menu item from menu being called before menu was inflated..
        MenuItem holiday = menu.findItem(R.id.holiday).setTitle("hiya");

        if(ActGoals.goalStore.holidaymode){

            holiday.setTitle("Holiday Mode: ACTIVE");
        }else{holiday.setTitle("Holiday Mode: DISABLED");}


return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//this is set in menu_main as an item
            case R.id.action_next_seven:
                Intent intent = new Intent(this, ActFutureGoals.class);
                intent.putExtra("firstweek", false);
                int a = 4; //request code? (receives back request code, resultcode, intent)?
                startActivityForResult(intent, a);
                break;

        }

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int a =4;


        if(data!=null) {
            profile = data.getExtras().getString("profileName");
        }
        if (resultCode == RESULT_OK && requestCode == 4) {
            //if (data.hasExtra("returnKey1")) {
            //  Toast.makeText(this, data.getExtras().getString("returnKey1"),
            //        Toast.LENGTH_SHORT).show();}}

            Log.i("44331", "onactivity result");
            if(p.refreshDay == 888) //goalStore.firstweek){
            {
                goalStore.list = databaseHelper.getFutureGoals(profile);
                goalStore.saveToDatabase();
                Log.i("44331", "size of goalStorelist" + goalStore.list.size());
                int refresh = dayofyear + this.daysToRefresh();
                Log.i("44331 refresh1", ""+p.refreshDay);
                Log.i("44331 refresh2", ""+refresh);

                p.refreshDay=refresh;
                goalStore.refreshDay=refresh;
                databaseHelper.updateProfileRow(p.name, p.name, refresh);
            }
        }
        else{

            goalStore.list = databaseHelper.getGoals(profile);

            //i dont like this, surely this is "tightly coupled"?? maybe ask
            Frag2Goals.reload();



        }
    }

    public void setUpPage(){
        //this works because styles requests a noActionBar style and actionbar is set to false
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setup the viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CustPagerAdapter pagerAdapter = new CustPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        // Setup the Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        //tab selection events update the ViewPager and page changes update the selected tab.

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        tabLayout.getTabAt(1).select();
    }


    public static void deleteDatabase(int profileN){

        Log.i("44331", "ISSUE called from actGoals");
    }


    public void menuDropdown(MenuItem item) {
        Log.i("6705reorder", "ayyyp");

        switch(String.valueOf(item.getTitle())){
            case "Account":

                Intent intentAccount = new Intent(this, ActSettings.class)              ;
                startActivity(intentAccount);

                break;
            case "Re-order Goals":

                Log.i("6705reorder", "called");
                Intent intent = new Intent(this, ActReorderPage.class);
                startActivityForResult(intent, 8);

                break;
            case "Holiday Mode: DISABLED":
                goalStore.setHolidayMode(true);

                item.setTitle("Holiday Mode: ACTIVE");

                break;
            case "Holiday Mode: ACTIVE":
                goalStore.setHolidayMode(false);

                item.setTitle("Holiday Mode: DISABLED");

                break;
        }


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









    public static void setHolidayTitle(){



        //MenuItem holiday = (MenuItem) menu.findItem(R.id.settingsGroup);
        //MenuItem set = (MenuItem)menu.getItem(R.id.settingsGroup);
        //MenuItem holiday = (MenuItem) set.getSubMenu().getItem(R.id.holiday);
        // (MenuItem) set.getSubMenu().findItem(R.id.holiday);
        //set.getSubMenu().removeItem(R.id.holiday);
        //holiday =  menu.getItem(R.id.settingsGroup).getSubMenu().getItem(R.id.holiday);

     //   MenuItem holiday = menu.findItem(R.id.holiday).setTitle("hiya");
        //
       // if(mode){

     //       holiday.setTitle("Holiday Mode: ACTIVE");
   //     }else{holiday.setTitle("Holiday Mode: DISABLED");}
    }

}