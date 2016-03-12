package com.parse.starter;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingsScrn extends AppCompatActivity implements View.OnClickListener{
    EditText username;
    EditText password;
    TextView user;
    Button signin;
    Button signup;
    Button logout;
    Button save;
    Button load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_scrn);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        user=(TextView)findViewById(R.id.textView2);
        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);

        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        logout.setTag("logout");

        signin.setOnClickListener(this);
        signin.setTag("signin");
        signup.setOnClickListener(this);
        signup.setTag("signup");

        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        save.setTag("save");
        load.setTag("load");
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        save.setVisibility(View.GONE);
        load.setVisibility(View.GONE);
        signin.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);

        checkSignedIn();



    }



    public void signInOrSignUp(View v){
        Button sign = (Button)v;
        if(sign.getTag().equals("signin")){

        ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(password.getText()), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {

                    Log.i("6705log", "Logged in");
                    Log.i("6705logcurrent", ParseUser.getCurrentUser().getUsername());
                    Toast.makeText(getApplication(), "Signed In Successfully!", Toast.LENGTH_LONG).show();
                    checkSignedIn();
                } else

                {
                    Toast.makeText(getApplication(), "ERROR:" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });}else {
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(String.valueOf(username.getText()));
                    newUser.setPassword(String.valueOf(password.getText()));

                    newUser.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {

                                Log.i("6705log", "Signed Up");
                                Toast.makeText(getApplication(), "Signed Up Successfully!", Toast.LENGTH_LONG).show();

                            } else {

                                e.printStackTrace();
                                Toast.makeText(getApplication(), "ERROR:" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }



    @Override
    public void onClick(View v) {

        if (v.getTag().equals("logout")) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Toast t = Toast.makeText(getApplicationContext(), "Thats ye logged oot", Toast.LENGTH_SHORT);
                    t.show();
                    checkSignedIn();
                }
            });
        }else if(v.getTag().equals("save") || v.getTag().equals("load")){
            //Log.i("6705saveToCloudswitch", "called");

            switch(v.getTag().toString()){
                case "save":
                    saveToCloud();
                    break;
                case "load":
                    loadFromCloud();
                    break;
            }

        }else{
            if (v instanceof Button) {
                signInOrSignUp(v);

            }
        }
    }


    public void checkSignedIn(){
        if(ParseUser.getCurrentUser() != null) {
            Log.i("6705logcurrent", ParseUser.getCurrentUser().getUsername());
            signin.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            user.setText("signed in as " + ParseUser.getCurrentUser().getUsername());
            save.setVisibility(View.VISIBLE);
            load.setVisibility(View.VISIBLE);

        }else{
            user.setText("not signed in");
            signin.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            load.setVisibility(View.GONE);
        }
    }


    static ArrayList<String> profilenames= new ArrayList<>();
    static ArrayList<JSONObject> JSONgoals = new ArrayList<>();
    static ArrayList<JSONObject> JSONFuturegoals = new ArrayList<>();
    static ArrayList<JSONObject> JSONPastTotals = new ArrayList<>();
    static SharedPreferences prefs;
    static int count;

    public void saveToCloud() {

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        count= prefs.getInt("count", 0);
        Log.i("6705saveToCloud", "calledcount:" + count);



        for (int i = 0; i < count; i++) {

            Log.i("6705saveToCloud", "inside loop");

            JSONgoals.add(convertGoalsToJSON(i));
            JSONFuturegoals.add(convertFutureGoalsToJSON(i));
            JSONPastTotals.add(convertPastTotalsToJSON(i));
            profilenames.add(prefs.getString("prof"+i+"Text", " "));

            Log.i("6705del", "profilenames: " +profilenames.get(i));

            }

        Log.i("6705saveToCloudJSON", "" + JSONPastTotals.size());


        ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
          //  query.whereNotContainedIn("Profile", profilenames);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> goalData, ParseException e) {
                    if (e == null) {
                        Log.d("6705del", goalData.size() + " scores dont match");
                        for (ParseObject goalRow : goalData) {
                            try{Log.i("6705del", goalRow.toString() + " being deleted?");
                            goalRow.deleteInBackground(new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Log.i("6705del", "save goals called");
                                        for (int ii = 0; ii < count; ii++) {


                                            //if goalData size is zero - call save method. else, delete then call save method

                                            ParseObject goal = new ParseObject("GoalData");
                                            goal.put("Goals", JSONgoals.get(ii));
                                            goal.put("username", ParseUser.getCurrentUser().getUsername());
                                            int num = ii;
                                            String profilename = prefs.getString("prof" + num + "Text", " ");
                                            goal.put("Profile", profilename);
                                            goal.put("FutureGoals", JSONFuturegoals.get(ii));
                                            goal.put("PastTotals", JSONPastTotals.get(ii));

                                            goal.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e == null) {
                                                        Toast t = Toast.makeText(getApplicationContext(), "Saved yer Goals fur ye!", Toast.LENGTH_SHORT);
                                                        t.show();
                                                    } else {
                                                        Toast t = Toast.makeText(getApplicationContext(), "Trouble savin yer goals pal, sorry!", Toast.LENGTH_SHORT);
                                                        e.printStackTrace();
                                                        t.show();
                                                    }
                                                }
                                            });
                                        }
                                    }


                                }
                            });}catch(Exception ee){}}}}
                            });

    }






    public void loadFromCloud(){

    }



    public JSONObject convertGoalsToJSON(int i){

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT * FROM goalsTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int totalIndex = c.getColumnIndex("total");
        int doneIndex = c.getColumnIndex("done");
        int percentIndex = c.getColumnIndex("percent");
        int b0Index = c.getColumnIndex("b0");
        int b1Index = c.getColumnIndex("b1");
        int b2Index = c.getColumnIndex("b2");
        int b3Index = c.getColumnIndex("b3");
        int b4Index = c.getColumnIndex("b4");
        int b5Index = c.getColumnIndex("b5");
        int b6Index = c.getColumnIndex("b6");

        c.moveToFirst();
        boolean cancel = false;
        int pos = 0;

        String strJson="{\"Goals\":[";

        while (cancel == false) {

            try {// why must i have this?? and the cancel bit too... tidy this all up



                    Log.i("6705saveToCloud", "inside Json loop");

                    if (pos == 0) {
                        strJson += "{";
                    } else {
                        strJson += ",{";
                    }

                    strJson += "" +

                            "\"name\":" + c.getString(nameIndex) + "," +
                            "\"total\":" + c.getInt(totalIndex) + "," +
                            "\"done\":" + c.getInt(doneIndex) + "," +
                            "\"percent\":" + c.getDouble(percentIndex) + "," +
                            "\"b0\":" + c.getInt(b0Index) + "," +
                            "\"b1\":" + c.getInt(b1Index) + "," +
                            "\"b2\":" + c.getInt(b2Index) + "," +
                            "\"b3\":" + c.getInt(b3Index) + "," +
                            "\"b4\":" + c.getInt(b4Index) + "," +
                            "\"b5\":" + c.getInt(b5Index) + "," +
                            "\"b6\":" + c.getInt(b6Index) +

                            "}";


            } catch (Exception e) {cancel = true;Log.i("6705why", "canceled from index out of bounds exception/or Json Error");e.printStackTrace();
                strJson +="}";}

            pos++;
            c.moveToNext();
        }
        strJson += "]}";


        try {JSONObject jsonRootGoal = new JSONObject(strJson);
            return jsonRootGoal;}catch(Exception e){e.printStackTrace();}

        return null;
    }

    public JSONObject convertFutureGoalsToJSON(int i) {

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);
        Cursor c = myDatabase.rawQuery("SELECT * FROM FgoalsTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int totalIndex = c.getColumnIndex("total");

        c.moveToFirst();
        boolean cancel = false;
        int pos = 0;

        String strJson = "{\"Goals\":[";

        while (c != null && cancel == false) {

            try {// why must i have this?? and the cancel bit too... tidy this all up
                Log.i("6705saveToCloud", "inside Json loop");

                if (pos == 0) {
                    strJson += "{";
                } else {
                    strJson += ",{";
                }

                strJson += "" +

                        "\"name\":" + c.getString(nameIndex) + "," +
                        "\"total\":" + c.getInt(totalIndex) +
                        "}";

            } catch (Exception e) {
                cancel = true;
                Log.i("6705why", "canceled from index out of bounds exception/or Json Error");
                e.printStackTrace();
                strJson +="}";
            }

            pos++;
            c.moveToNext();
        }
        strJson += "]}";


        try {
            JSONObject jsonRootGoal = new JSONObject(strJson);
            return jsonRootGoal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public JSONObject convertPastTotalsToJSON(int i) {


        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);

        Cursor c = myDatabase.rawQuery("SELECT * FROM pastTotalsTbl", null);
        int totalsIndex = c.getColumnIndex("totalPercent");

        c.moveToFirst();
        boolean cancel = false;
        int pos = 0;

        String strJson = "{\"Past\":[";

        while (c != null && cancel == false) {

            try {// why must i have this?? and the cancel bit too... tidy this all up
                Log.i("6705saveToCloud", "inside Json loop");

                if (pos == 0) {strJson += "{";} else {strJson += ",{";}

                strJson += "" +
                        "\"pastTotal\":" + c.getInt(totalsIndex) +"}";

            } catch (Exception e) {
                cancel = true;
                Log.i("6705why", "canceled from index out of bounds exception/or Json Error");
                e.printStackTrace();
                strJson +="}";
            }

            pos++;
            c.moveToNext();
        }
        strJson += "]}";


        try {
            JSONObject jsonRootGoal = new JSONObject(strJson);
            return jsonRootGoal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }















}
