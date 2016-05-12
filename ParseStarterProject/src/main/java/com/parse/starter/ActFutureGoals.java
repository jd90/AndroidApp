package com.parse.starter;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Borris on 09/02/2016.
 */
public class ActFutureGoals extends ListActivity implements View.OnClickListener {

    //this is a separate goalStore = however, it could possibly be held within ProfileMainActivity as static? and updated/accessed as needed?

    static GoalStore2 fgoalStore;
    DatabaseHelper databaseHelper;
    GoalStore1 goalStore;
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        databaseHelper = new DatabaseHelper(this);
        goalStore = ActGoals.goalStore;
        setContentView(R.layout.future_item_layout);

        Bundle b;
        b = getIntent().getExtras();
        boolean firstweek = b.getBoolean("firstweek");

        profileName = b.getString("profileName");

        fgoalStore = ActGoals.fgoalStore;

                //ActGoals.fgoalStore;
        TextView title = (TextView) findViewById(R.id.windowTitle);
        title.setText("Next Week: " + ActGoals.daysToRefresh() + " days to refresh");

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        backButton.setTag("backButton");

        Button newGoal = (Button) findViewById(R.id.newGoalButton);
        newGoal.setText("Add Goal");
        newGoal.setTag("new");

        CustAdapterNewGoals adapter = new CustAdapterNewGoals(this, fgoalStore, profileName);
        setListAdapter(adapter);
        getListView().setDivider(null);
        newGoal.setOnClickListener(adapter);

        if(firstweek){
            title.setText("FIRST WEEK: PLEASE ADD GOALS");

            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setTag("start");
            startButton.setVisibility(View.VISIBLE);
            startButton.setOnClickListener(this);
        }
    }

    public void onClick(View view){

        onBackPressed();
    }
    public void onBackPressed() {

        Button startButton = (Button) findViewById(R.id.startButton);

        if(startButton.getVisibility() == View.GONE) {

            exit();
        }
        else{
            popUpConfirm();
        }
    }
    public void popUpConfirm(){

            AlertDialog.Builder confirm = new AlertDialog.Builder(this);
            confirm.setTitle("Save and Start");
            confirm.setMessage("Are you sure you want to commit to these Goals? \n" +
                    "Next refresh is in " + ActGoals.daysToRefresh() + " days");
            confirm.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    exit();
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.drawable.goal_shark_logo1)
                    .show();
    }
    public void exit(){

        //this possibly only needs to be done during the firstweek - however it doesnt cause any harm to do it all times
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_YEAR);
        int refresh= calendar.get(Calendar.DAY_OF_YEAR) + ActGoals.daysToRefresh();//possibly move to dbhelper method
        Log.i("refreshfromfuture", ""+ActGoals.daysToRefresh());
        Log.i("refreshfromfuture", ""+refresh);

        if(refresh > 365){refresh-=365;}
        databaseHelper.updateProfileRow(profileName, profileName, refresh);

        String message = "some message text";
        Intent intentBack = new Intent();
        intentBack.putExtra("profileName", profileName);

        setResult(RESULT_OK, intentBack);

        Log.i("refreshfromfuture", ""+refresh);
        //why use intents when i can just access ProfileMainActivity.goalStore2?
        finish();}


}
