package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActGoals extends AppCompatActivity {

    static TabLayout.Tab t0;
    static GoalStore1 goalStore;
    static GoalStore2 fgoalStore;
    static ArchiveItemDatastore archiveItemDatastore;
    static Context context;
    static String profile;
    static int profileNum;
    static TabLayout tabLayout;

    static Menu menu;
    DatabaseHelper databaseHelper;
    int dayofyear;
    static int day;
    Calendar calendar = Calendar.getInstance();
    static ClassProfile p;// made static to reference refreshday for holiday mode - could be used in all my profilename refs too tho?... or put into dbhelper or something??
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =this.getApplicationContext();

        dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        day = calendar.get(Calendar.DAY_OF_WEEK);

        profile = getIntent().getExtras().getString("profileName");

        databaseHelper = new DatabaseHelper(this);

        goalStore = new GoalStore1(databaseHelper.getGoals(profile), profile);

        fgoalStore = new GoalStore2(databaseHelper.getFutureGoals(profile), profile);
        archiveItemDatastore = new ArchiveItemDatastore();
        archiveItemDatastore.list = databaseHelper.getPastTotals(profile);

        p =ActProfiles.profileDatastore.getProfile(profile);
        Log.i("44331 refreshday", ""+p.refreshDay);

        if(p.refreshDay==888){
            goalStore.firstweek=true;
        }else{goalStore.firstweek=false;}
        //fix - combine these two?
        if(goalStore.firstweek){

        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(1, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(2, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(3, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(4, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(5, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(6, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(7, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(8, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(9, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(10, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(11, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(12, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(13, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(14, "apr 4"));
        databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(15, "apr 4"));databaseHelper.insertPastTotal(ActGoals.profile, new ClassArchiveItem(16, "apr 4"));
            archiveItemDatastore = new ArchiveItemDatastore();
            archiveItemDatastore.list = databaseHelper.getPastTotals(profile);//repeated here because the table is yet to be populated above when loading from db



            Intent intent = new Intent(this, ActFutureGoals.class);
            int a = 4; //request code? (receives back request code, resultcode, intent)?
            intent.putExtra("firstweek", true);
            intent.putExtra("profileName", profile);
            startActivityForResult(intent, a);
            //startActivity(intent);
         //goalStore.loadFromFutureDatabase();//one time on first load
      }

        Log.i("44331 refresh", ""+p.refreshDay);
        Log.i("44331 refresh", ""+dayofyear);


        //p.refreshDay = dayofyear-2;

        if(p.refreshDay<= dayofyear){

            //should also do the past totals thing here
            //and save a new refreshday

            archiveItemDatastore.updateList(p.refreshDay, dayofyear);
            databaseHelper.updatePastTotals(profile, archiveItemDatastore.list);



            goalStore.list = databaseHelper.getFutureGoals(profile);
            databaseHelper.clearGoalsTbl(profile);
            for(int i = 0; i <goalStore.list.size(); i++ ){
            databaseHelper.insertGoal(goalStore.list.get(i));   //i should make a database helper method to do this
            }
            int refresh = dayofyear + daysToRefresh();
            databaseHelper.updateProfileRow(profile, profile, refresh);
            //here is where the past totals operation should go - have the logic inside the database helper?


        }


        setUpPage();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menu = menu;//overcame an issue here, was trying to find menu item from menu being called before menu was inflated..
        MenuItem holiday = menu.findItem(R.id.holiday).setTitle("hiya");

        if(p.refreshDay==366){

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
                intent.putExtra("profileName", profile);// this fixed a problem - was sending it during the firstweek opening but not here, so if reordering after resuming from this sequence the goals profile on resume was unknown
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
                int refresh = dayofyear + daysToRefresh();//possibly move to db method2
                if(refresh > 365){refresh-=365;}
                Log.i("44331 refresh1", ""+p.refreshDay);
                Log.i("44331 refresh2", ""+refresh);

                p.refreshDay=refresh;
                //goalStore.refreshDay=refresh;
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
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CustPagerAdapter pagerAdapter = new CustPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        // Setup the Tabs
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        //tab selection events update the ViewPager and page changes update the selected tab.

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        tabLayout.getTabAt(1).select();
        t0 = tabLayout.getTabAt(0);


        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =tab.getPosition();
                viewPager.setCurrentItem(position);
                if (tab.getPosition() == 0) {
                    Log.i("viewpager", "feed selected");

                    ConnectivityManager connect = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connect.getActiveNetworkInfo() != null) {

                        if (ParseUser.getCurrentUser() == null) {

                        }else{
                            try {
                                Log.i("78789777", "a " + "here");
                                List l = ParseUser.getCurrentUser().getList("followers");
                                l.add(ParseUser.getCurrentUser().getUsername());
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Feed");
                                query.whereContainedIn("username", l);
                                query.orderByAscending("createdAt");
                                query.findInBackground(new FindCallback<ParseObject>() {
                                    public void done(List<ParseObject> feedRows, ParseException e) {
                                        if (e == null) {

                                            for(int i =0; i<feedRows.size(); i++){

                                                List itemSeen = feedRows.get(i).getList("itemSeen");
                                                if(!itemSeen.contains(ParseUser.getCurrentUser().getUsername())){
                                                    itemSeen.add(ParseUser.getCurrentUser().getUsername());
                                                    feedRows.get(i).put("itemSeen", itemSeen);
                                                    feedRows.get(i).saveEventually();

                                                }
                                            }

                                                }
                                    }

                                });

                            } catch (Exception e) {

                            }

                        }
                    }


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    t0.setIcon(null);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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



                p.refreshDay=366;
                databaseHelper.updateProfileRow(profile,profile,p.refreshDay);

                item.setTitle("Holiday Mode: ACTIVE");

                break;
            case "Holiday Mode: ACTIVE":

                int refresh=dayofyear+daysToRefresh();
                if (refresh > 365){refresh -= 365;}
                p.refreshDay=refresh;
                databaseHelper.updateProfileRow(profile,profile,p.refreshDay);

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

    @Override
    public void onResume(){

        Log.i("profilename === ", ""+profile);
        //goalStore = new GoalStore1(databaseHelper.getGoals(profile), profile);
        //fgoalStore = new GoalStore2(databaseHelper.getFutureGoals(profile), profile);
        super.onResume();
    }


}