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

public class ProfileMainActivity extends AppCompatActivity {

    static GoalStore1 goalStore;
    static GoalStore2 fgoalStore;
    static SQLiteDatabase myDatabase;
    static Context context;
    Cursor c;
    int profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =this.getApplicationContext();

        profile = getIntent().getExtras().getInt("profile");


        myDatabase = this.openOrCreateDatabase("GoalApp"+profile, MODE_PRIVATE, null);
        goalStore = new GoalStore1(myDatabase, false);
        fgoalStore = new GoalStore2(myDatabase, true);

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
        myDatabase.deleteDatabase(context.getDatabasePath("GoalApp"+profileN));

    }


    public void menuDropdown(MenuItem item) {
        Log.i("6705reorder", "ayyyp");

        switch(String.valueOf(item.getTitle())){
            case "settings":
                

                break;
            case "Re-order Goals":

                Log.i("6705reorder", "called");
                Intent intent = new Intent(this, ReorderPage.class);
                startActivityForResult(intent, 8);

                break;
        }


    }

}