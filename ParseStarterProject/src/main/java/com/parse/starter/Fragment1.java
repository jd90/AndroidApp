package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


public class Fragment1 extends ListFragment {

    SQLiteDatabase database;

    // Required empty public constructor
    public Fragment1() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), ProfileMainActivity.goalStore);
        setListAdapter(adapter);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(null);


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


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setPressed(true);
    }

}