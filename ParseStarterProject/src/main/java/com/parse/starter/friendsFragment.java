package com.parse.starter;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Borris on 04/02/2016.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class friendsFragment extends ListActivity {


    // Required empty public constructor
    public friendsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List hi = new ArrayList<String>();
        hi.add("hi");
                hi.add("bye");
        CustomArrayFriends adapter = new CustomArrayFriends(this, hi, 1111);
        setListAdapter(adapter);


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setPressed(true);
    }

}