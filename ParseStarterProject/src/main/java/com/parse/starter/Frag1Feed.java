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
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class Frag1Feed extends ListFragment {

    SQLiteDatabase database;
    ArrayList<String> usernames;
    List<ClassFeedItem> feedList = new ArrayList<>();
    ArrayList<Integer> pastTotalsArray = new ArrayList<>();
    ArrayList<String> pastDatesArray = new ArrayList<>();
    int i;

    // Required empty public constructor
    public Frag1Feed() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CustAdapterFeed adapter = new CustAdapterFeed(getActivity(), feedList);
        setListAdapter(adapter);

        ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect.getActiveNetworkInfo() != null) {

            if (ParseUser.getCurrentUser() == null) {
                feedList.clear();
                feedList.add(new ClassFeedItem("Error retrieving feed!", " No user signed in", 200, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
                adapter = new CustAdapterFeed(getActivity(), feedList);
                setListAdapter(adapter);
                //List users = ParseUser.getCurrentUser().getList("followers");
            }else{
            usernames = new ArrayList<>();
            try {
                Log.i("78789777", "a " + "here");
                List l = ParseUser.getCurrentUser().getList("followers");
                l.add(ParseUser.getCurrentUser().getUsername());
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Feed");
                query.whereContainedIn("username", l);
                query.orderByAscending("createdAt");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> feedRows, ParseException e) {
                        if (e == null) {
                            Log.i("78789777", "a " + "here2");
                            if (feedRows.size() == 0) {feedList.clear();
                                feedList.add(new ClassFeedItem("No feed items", "", 200, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
                                CustAdapterFeed adapter = new CustAdapterFeed(getActivity(), feedList);
                                setListAdapter(adapter);
                            } else {
                                feedList.clear();
                                for (i = feedRows.size() - 1; i >= 0; i--) {
                                    Log.i("78789777", "a " + "here3");
                                    try {
                                        String username = feedRows.get(i).getString("username");
                                        String profileName = feedRows.get(i).getString("profilename");
                                        int percent = feedRows.get(i).getInt("percent");
                                        String date = feedRows.get(i).getString("date");
                                        List comments = feedRows.get(i).getList("comments");
                                        List likes = feedRows.get(i).getList("likes");
                                        List itemSeen = feedRows.get(i).getList("itemSeen");

                                        if(!itemSeen.contains(ParseUser.getCurrentUser().getUsername())){
                                            ActGoals.t0.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);
                                        }

                                        String id = feedRows.get(i).getObjectId();
                                        Log.i("78789777", "a " + "here4");

                                        feedList.add(new ClassFeedItem(username, profileName, percent, date, comments, likes, itemSeen, id));
                                    } catch (Exception e3) {
                                        Log.i("6705del", " problem making JSONobject3" + e3.toString());feedList.clear();
                                        feedList.add(new ClassFeedItem("Error retrieving feed", "" + e3.toString(), 0, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
                                        CustAdapterFeed adapter = new CustAdapterFeed(getActivity(), feedList);
                                        setListAdapter(adapter);
                                    }

                                }

                                try {
                                    CustAdapterFeed adapter = new CustAdapterFeed(getActivity(), feedList);
                                    setListAdapter(adapter);
                                }catch(Exception exc){
                                    //feedList.add(new ClassFeedItem("Error retrieving feed#", "" + exc.toString(), 0, "", new ArrayList(11), new ArrayList(11), new ArrayList(11), ""));
                                    //CustAdapterFeed adapter = new CustAdapterFeed(getActivity(), feedList);
                                    //setListAdapter(adapter);
                                    //this was removed as it was causing the error??
                                    }

                            }

                        }
                    }

                });

            } catch (Exception e) {feedList.clear();
                feedList.add(new ClassFeedItem("Error retrieving feed", "" + e.toString(), 0, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
                adapter = new CustAdapterFeed(getActivity(), feedList);
                setListAdapter(adapter);
            }

        }
        } else {
            if (connect.getActiveNetworkInfo() == null) {feedList.clear();
                feedList.add(new ClassFeedItem("Error retrieving feed!", " Please check network connection", 200, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
            } else {
                if (ParseUser.getCurrentUser() == null) {
                    feedList.clear();
                    feedList.add(new ClassFeedItem("Error retrieving feed!", " No user signed in", 200, "", new ArrayList(), new ArrayList(), new ArrayList(), ""));
                }
            }
            adapter = new CustAdapterFeed(getActivity(), feedList);
            setListAdapter(adapter);
        }



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);

}


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setPressed(true);
    }

}