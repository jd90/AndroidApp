package com.parse.starter;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Borris on 09/02/2016.
 */
public class FutureGoals extends ListActivity {

    //this is a separate goalStore = however, it could possibly be held within MainActivity as static? and updated/accessed as needed?

    static GoalStore2 fgoalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.future_item_layout);

        fgoalStore = MainActivity.fgoalStore;

                //MainActivity.fgoalStore;



        Button newGoal = (Button) findViewById(R.id.newGoalButton);
        newGoal.setText("NEW");
        newGoal.setTag("new");

        CustomArrayAdapter4 adapter = new CustomArrayAdapter4(this, MainActivity.fgoalStore);
        setListAdapter(adapter);

        newGoal.setOnClickListener(adapter);
    }

    //this is somewhat set up to be opened from action bar as an activity_for_result. and below code sends it back with intent data
   // public void onBackButton(View view) {
  //      onBackPressed();
  //  }
    public void onBackPressed() {
        String message = "some message text";
        Intent intentBack = new Intent();
        intentBack.putExtra("Message", message);
        setResult(ListActivity.RESULT_OK, intentBack);

        Log.i("MethodCalledJ", "L");
        //why use intents when i can just access MainActivity.goalStore2?

        finish();
    }



}
