package com.parse.starter;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Borris on 09/02/2016.
 */
public class FutureGoals extends ListActivity implements View.OnClickListener {

    //this is a separate goalStore = however, it could possibly be held within ProfileMainActivity as static? and updated/accessed as needed?

    static GoalStore2 fgoalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.future_item_layout);

        Bundle b;
        b = getIntent().getExtras();
        boolean firstweek = b.getBoolean("firstweek");

        fgoalStore = ProfileMainActivity.fgoalStore;

                //ProfileMainActivity.fgoalStore;
        TextView title = (TextView) findViewById(R.id.windowTitle);
        title.setText("Next Week: " + ProfileMainActivity.goalStore.daysToRefresh() + " days to refresh");

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        backButton.setTag("backButton");

        Button newGoal = (Button) findViewById(R.id.newGoalButton);
        newGoal.setText("Add Goal");
        newGoal.setTag("new");

        CustomArrayAdapter4 adapter = new CustomArrayAdapter4(this, fgoalStore);
        setListAdapter(adapter);

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
                    "Next refresh is in " + ProfileMainActivity.goalStore.daysToRefresh() + " days");
            confirm.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    exit();
                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
    }
    public void exit(){String message = "some message text";
        Intent intentBack = new Intent();
        intentBack.putExtra("Message", message);
        setResult(RESULT_OK, intentBack);

        Log.i("MethodCalledJ", "L");
        //why use intents when i can just access ProfileMainActivity.goalStore2?
        finish();}


}
