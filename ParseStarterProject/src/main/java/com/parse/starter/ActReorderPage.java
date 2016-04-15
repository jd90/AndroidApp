package com.parse.starter;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Borris on 06/03/2016.
 */

public class ActReorderPage extends ListActivity implements View.OnClickListener{

    // Required empty public constructor
    public ActReorderPage() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_item_layout);
        TextView title = (TextView) findViewById(R.id.windowTitle);
        title.setText("Re-order Goals");
        Button saveButton = (Button) findViewById(R.id.newGoalButton);
        saveButton.setText("Save");
        saveButton.setOnClickListener(this);
        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        CustAdapterReorder adapter = new CustAdapterReorder(this, ActGoals.goalStore);
        setListAdapter(adapter);
    }

    public void onBackPressed(){


            Log.i("MethodCalledJ", "L");
            //why use intents when i can just access ProfileMainActivity.goalStore2?
            finish();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}

