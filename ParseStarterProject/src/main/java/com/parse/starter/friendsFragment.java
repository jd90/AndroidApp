package com.parse.starter;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
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
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class friendsFragment extends ListActivity {

    SQLiteDatabase database;
    ArrayList<String> usernames;
    ArrayAdapter adapter;
    // Required empty public constructor
    public friendsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(ParseUser.getCurrentUser().getList("followers")==null){//must initialise array column in parse to empty list rather than nullpointer?
                ArrayList<String> followers = new ArrayList<>();
                ParseUser.getCurrentUser().put("followers", followers);
        }

        usernames = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, usernames);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    usernames.clear();
                    for (ParseUser username : objects) {
                        usernames.add(username.getUsername());

                    }
                    adapter.notifyDataSetChanged();

                } else {
                    Log.i("787878", "problem with usernames search");
                    Log.i("787878", ""+e.toString());


                }
            }
        });

        //CustomArrayFriends adapter = new CustomArrayFriends(this, hi, 1111);
         // ListView listView = (ListView) findViewById(R.id.listviewfeed);
       // setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        setListAdapter(adapter);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CheckedTextView check = (CheckedTextView) v;
        if(check.isChecked()) {
            check.setChecked(false);
            Log.i("7878user", usernames.get(position));
            //ParseUser.getCurrentUser().getList("followers").remove(usernames.get(position));
            List<Object> hi = ParseUser.getCurrentUser().getList("followers");
            hi.remove(usernames.get(position));
            ParseUser.getCurrentUser().put("followers", hi);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e!=null){Log.i("787878", "remove "+e.toString());}
                    else{Log.i("787878", "removed ");}
                }
            });
        }else{
            check.setChecked(true);
            Log.i("7878user", usernames.get(position));
            //ParseUser.getCurrentUser().getList("followers").add(usernames.get(position));
            List<Object> hi = ParseUser.getCurrentUser().getList("followers");
            hi.add(usernames.get(position));
            ParseUser.getCurrentUser().put("followers", hi);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.i("787878", "add " + e.toString());
                    } else {
                        Log.i("787878", "added ");
                    }
                }
            });
        }
    }

}