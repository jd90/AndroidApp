package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
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
import java.util.TooManyListenersException;


public class Fragment1 extends ListFragment {

    SQLiteDatabase database;
    ArrayList<String> usernames;
    List<FeedItem> feedList = new ArrayList<>();
    ArrayList<Integer> pastTotalsArray = new ArrayList<>();
    ArrayList<String> pastDatesArray = new ArrayList<>();
    // Required empty public constructor
    public Fragment1() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
             if(connect.getActiveNetworkInfo() != null) {


                 List users = ParseUser.getCurrentUser().getList("followers");


                 usernames = new ArrayList<>();
                 try {
                     Log.i("78789777", "a " + "here");
                     ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
                     query.whereContainedIn("username", ParseUser.getCurrentUser().getList("followers"));
                     query.findInBackground(new FindCallback<ParseObject>() {
                         public void done(List<ParseObject> followerRows, ParseException e) {
                             if (e == null) {
                                 Log.i("7878oopsy", "herep" + followerRows.toString());
                                 for (int i = 15; i >=0; i--) {
                                     for (ParseObject row : followerRows) {

                                         try {
                                             String username = row.getString("username") + " completed ";
                                             String profileName = " of " + row.getString("ProfileName") + " goals";


                                             try {
                                                 JSONObject pastTotalsObject = row.getJSONObject("PastTotals");
                                                 Log.i("6705del", "JSONobject WORKED3" + row.getJSONObject("PastTotals"));
                                                 JSONArray jsonArray3 = pastTotalsObject.optJSONArray("Past");
                                                 //Iterate the jsonArray and print the info of JSONObjects
                                                 int pastTotals;

                                                 JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                                 pastTotals = Integer.parseInt(jsonObject.optString("pastTotal"));
                                                 //feedList.add(new FeedItem(username, profileName, pastTotals));
                                                 //JSONObject jsonObject2 = jsonArray3.getJSONObject(i);
                                                 String pastDates = (jsonObject.optString("pastDate"));
                                                 feedList.add(new FeedItem(username, profileName, pastTotals, pastDates));
                                                 Log.i("pastDate 7878", pastDates);

                                             } catch (Exception e3) {
                                                 Log.i("6705del", " problem making JSONobject3" + e3.toString());
                                                 feedList.add(new FeedItem("Error retrieving feed", "" + e3.toString(), 0, ""));
                                                 CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), feedList);
                                                 setListAdapter(adapter);
                                             }

                                             Log.i("7878oopsyOutput", "" + username + " " + profileName);
                                         } catch (Exception ee) {
                                             Log.i("7878oopsy", "e " + ee.toString());
                                         }


                                     }
                                 }
                                 Log.i("78789", "here");
                                 Log.i("7878789 ", "a " + usernames.toString());
                                 CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), feedList);
                                 setListAdapter(adapter);

                             } else {
                                 Log.i("787878", "problem with usernames search");
                                 Log.i("787878", "" + e.toString());
                                 feedList.add(new FeedItem("Error retrieving feed", "" + e.toString(), 0, ""));
                                 CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), feedList);
                                 setListAdapter(adapter);
                             }

                         }
                     });

                 } catch (Exception e) {
                     feedList.add(new FeedItem("Error retrieving feed", "" + e.toString(), 0, ""));
                     CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), feedList);
                     setListAdapter(adapter);
                 }

             }else{
                 feedList.add(new FeedItem("Error retrieving feed", ". Please check network connection!", 200, ""));
                 CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), feedList);
                 setListAdapter(adapter);
             }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);



}

          /*
        database = ProfileMainActivity.myDatabase;


        ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> goalData, ParseException e) {
                if (e == null) {


                    int pastTotals=800;
                    String pastDates="";

                    Log.d("6705del", goalData.size() + " scores loading down");
                    if (goalData.size() < 1) {
                        //do something if no rows returned
                        Log.d("6705del", goalData.size() + "no goals to load");
                        Toast t = Toast.makeText(getApplicationContext(), "Yu'v nae goals tae load, ya pudding!", Toast.LENGTH_SHORT);
                        t.show();
                    } else {
                int count =0;
                   for (ParseObject goalRow : goalData) {
                       count++;
                       try {
                           JSONObject goal = goalRow.getJSONObject("Goals");
                           Log.i("6705del", "JSONobject WORKED1" + goalRow.getJSONObject("Goals"));
                           JSONArray jsonArray1 = goal.optJSONArray("Goals");

                           int profileCount = 0;

                           database.execSQL("CREATE TABLE IF NOT EXISTS pastGoals (goalsJson VARCHAR)");
                           database.execSQL("delete from pastGoals");


                           ArrayList<Integer> pastTotalsArray = new ArrayList<>();
                           ArrayList<String> pastDatesArray = new ArrayList<>();
                           try {
                               JSONObject pastTotalsObject = goalRow.getJSONObject("PastTotals");
                               Log.i("6705del", "JSONobject WORKED3" + goalRow.getJSONObject("PastTotals"));
                               JSONArray jsonArray3 = pastTotalsObject.optJSONArray("Past");
                               //Iterate the jsonArray and print the info of JSONObjects

                               for (int i = 0; i < jsonArray3.length() - 1; i++) {
                                   JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                   pastTotals = Integer.parseInt(jsonObject.optString("pastTotal"));
                                   pastTotalsArray.add(i, pastTotals);
                               }
                               for (int i = 0; i < jsonArray3.length() - 1; i++) {
                                   JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                   pastDates = (jsonObject.optString("pastDate"));
                                   pastDatesArray.add(i, pastDates);
                               }
                           } catch (Exception e3) {
                               Log.i("6705del", " problem making JSONobject3" + e3.toString());
                           }

                           for (int i = 0; i < pastTotalsArray.size(); i++) {
                               database.execSQL("INSERT INTO pastTotalsTbl (totalPercent, date) VALUES (" + pastTotalsArray.get(i) + ", '" + pastDatesArray.get(i) + "')");

                           }

                       } catch (Exception ee) {
                       }

                   }
                    }}}});
                    */



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setPressed(true);
    }

}