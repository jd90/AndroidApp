package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.acl.Group;

public class ProfileMainActivity extends AppCompatActivity {

    static GoalStore1 goalStore;
    static GoalStore2 fgoalStore;
    static SQLiteDatabase myDatabase;
    static Context context;
    int profile;
    static MenuItem holiday;
    static Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =this.getApplicationContext();


        profile = getIntent().getExtras().getInt("profile");

        Log.i("888888", "profilenum opening: " + profile);
//these three lines could just go inside of the profile class?? consider, save draft and attempt? (make sure to test fully tho.. could cause big issues)
        myDatabase = this.openOrCreateDatabase("GoalApp"+profile, MODE_PRIVATE, null);
        goalStore = new GoalStore1(myDatabase);
        fgoalStore = new GoalStore2(myDatabase);

//THIS IS CHECKING IF IT IS THE FIRST WEEK -- strange place for this to go?
        if(goalStore.firstweek){

            Log.i("6705firstweek", "first week called");
            Intent intent = new Intent(this, FutureGoals.class);
            int a = 4; //request code? (receives back request code, resultcode, intent)?
            intent.putExtra("firstweek", true);
            startActivityForResult(intent, a);
            //startActivity(intent);
         //goalStore.loadFromFutureDatabase();//one time on first load
      }


        setUpPage();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menu = menu;//overcame an issue here, was trying to find menu item from menu being called before menu was inflated..
        MenuItem holiday = menu.findItem(R.id.holiday).setTitle("hiya");

        if(ProfileMainActivity.goalStore.holidaymode){

            holiday.setTitle("Holiday Mode: ACTIVE");
        }else{holiday.setTitle("Holiday Mode: DISABLED");}


return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//this is set in menu_main as an item
            case R.id.action_next_seven:
                Intent intent = new Intent(this, FutureGoals.class);
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
        if (resultCode == RESULT_OK && requestCode == 4) {
            //if (data.hasExtra("returnKey1")) {
            //  Toast.makeText(this, data.getExtras().getString("returnKey1"),
            //        Toast.LENGTH_SHORT).show();}}
            if(goalStore.firstweek){

                goalStore.loadFromFutureDatabase();
            }
        }
        else{

            goalStore.loadFromDatabase();

            //i dont like this, surely this is "tightly coupled"?? maybe ask
            Fragment2.reload();



        }
    }

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


    public static void deleteDatabase(int profileN){
        Log.i("88888", "deleting from profmainact num: " + profileN);
        myDatabase.deleteDatabase(context.getDatabasePath("GoalApp"+profileN));

    }


    public void menuDropdown(MenuItem item) {
        Log.i("6705reorder", "ayyyp");

        switch(String.valueOf(item.getTitle())){
            case "Account":

                Intent intentAccount = new Intent(this, SettingsScrn.class)              ;
                startActivity(intentAccount);

                break;
            case "Re-order Goals":

                Log.i("6705reorder", "called");
                Intent intent = new Intent(this, ReorderPage.class);
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