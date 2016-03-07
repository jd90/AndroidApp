package com.parse.starter;

import android.app.ListActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Borris on 06/03/2016.
 */

public class ReorderPage extends ListActivity implements View.OnClickListener{

    // Required empty public constructor
    public ReorderPage() {
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

        CustomArrayAdapter5 adapter = new CustomArrayAdapter5(this, ProfileMainActivity.goalStore);
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

