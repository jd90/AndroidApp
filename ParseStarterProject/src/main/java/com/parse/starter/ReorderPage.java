package com.parse.starter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Borris on 06/03/2016.
 */

public class ReorderPage extends ListActivity {

    // Required empty public constructor
    public ReorderPage() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomArrayAdapter5 adapter = new CustomArrayAdapter5(this, ProfileMainActivity.goalStore);
        setListAdapter(adapter);

    }

    public void onBackPressed(){


            Log.i("MethodCalledJ", "L");
            //why use intents when i can just access ProfileMainActivity.goalStore2?
            finish();
    }

    }

