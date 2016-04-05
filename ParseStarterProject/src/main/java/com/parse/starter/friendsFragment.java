package com.parse.starter;

import android.app.ListActivity;
import android.content.Context;
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
    FollowersArrayAdapter adapter;
    ListView lView;
    // Required empty public constructor
    public friendsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_feed);

        usernames = new ArrayList<>();
        adapter = new FollowersArrayAdapter(this.getApplicationContext(), usernames);

        if(ParseUser.getCurrentUser().getList("followers")==null){//must initialise array column in parse to empty list rather than nullpointer?
            ArrayList<String> followers = new ArrayList<>();
            ParseUser.getCurrentUser().put("followers", followers);
        }
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    usernames.clear();
                    for (ParseUser username : objects) {
                        usernames.add(username.getUsername());

                        Log.i("78789", "a " + usernames.toString());
                    }

                } else {
                    Log.i("787878", "problem with usernames search");
                    Log.i("787878", "" + e.toString());
                }
                Log.i("78789", "here");
                Log.i("7878789 ", "" + usernames.toString());

                setListAdapter(adapter);
            }


        });




        /*
        if(ParseUser.getCurrentUser().getList("followers")==null){//must initialise array column in parse to empty list rather than nullpointer?
                ArrayList<String> followers = new ArrayList<>();
                ParseUser.getCurrentUser().put("followers", followers);
        }

        usernames = new ArrayList<>();
        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, usernames);

        adapter = new FollowersArrayAdapter(this, usernames);

        lView = getListView();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    usernames.clear();
                    for (ParseUser username : objects) {
                        usernames.add(username.getUsername());

                        Log.i("78789", "a " + usernames.toString());
                    }
                    adapter.notifyDataSetChanged();


                } else {
                    Log.i("787878", "problem with usernames search");
                    Log.i("787878", "" + e.toString());
                }
                Log.i("78789", "here");
                Log.i("7878789 ", "" + usernames.toString());
                for (String user : usernames) {
                    Log.i("78789", "" + user);

                    List<Object> a = ParseUser.getCurrentUser().getList("followers");
                    Log.i("78789", "" + a.toString());
                    if (a.contains(user)) {
                        Log.i("78789", "match" + user);
                        lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//had to do this to make it work

                        lView.setItemChecked(usernames.indexOf(user), true);
                        //lView.setItemChecked(usernames.indexOf(user), true);
                        //       CheckedTextView c = (CheckedTextView) lView.getItemAtPosition(usernames.indexOf(user));
                        //       c.setChecked(true);

                    }
                }

                adapter.notifyDataSetChanged();

            }
        });

        FollowersArrayAdapter adapter = new FollowersArrayAdapter(this, usernames);
        setListAdapter(adapter);
        //CustomArrayFriends adapter = new CustomArrayFriends(this, hi, 1111);
         ListView listView = (ListView) findViewById(R.id.listviewfeed);
       //listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
       */
    }


 //   @Override
  //  public void onListItemClick(ListView l, View v, int position, long id) {
        /*
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
                    else{Log.i("787878", "removed ");
                        adapter.notifyDataSetChanged();
                    }

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
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }
        */
  //  }

}