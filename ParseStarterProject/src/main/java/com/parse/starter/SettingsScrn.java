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

    static ArrayList<String> profilenames= new ArrayList<>();
    static ArrayList<JSONObject> JSONgoals = new ArrayList<>();
    static ArrayList<JSONObject> JSONFuturegoals = new ArrayList<>();
    static ArrayList<JSONObject> JSONPastTotals = new ArrayList<>();
    static SharedPreferences prefs;
    static int count;
    static int size;
    static int counter;
    static String profString;
    static ArrayList<Integer> profileNames= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_scrn);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        count= prefs.getInt("count", 0);
        Log.i("6705del", "calledcount1:" + count);
        profString = (prefs.getString("profiles", " "));
        Log.i("6705del", "profString =" + profString);

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
                    Toast.makeText(getApplication(), "Signed In Successfully!", Toast.LENGTH_LONG).show();
                    checkSignedIn();
                } else {
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

    public void saveToCloud() {

//this section searches through all 4 possible profiles.
//and if they exist, it adds its details to the JSONobjects using JSONmethods()

//this must be cleared?? otherwise if you decrease the size and it hasnt been shut to stop persistence then its still got more in it = nullpointer because its used as the loop i<?
        JSONgoals.clear();JSONFuturegoals.clear();JSONPastTotals.clear();profileNames.clear();

        for (int i = 0; i < 4; i++) {


            switch(i){
                case 0:
                    if(profString.contains("0")){
                        profileNames.add(0);
                        Log.i("6705del", "contains 0 called");
                    toJSONMethods(0);}
                    break;
                case 1:
                    if(profString.contains("1")){
                        profileNames.add(1);
                        Log.i("6705del", "contains 1 called");
                        toJSONMethods(1);}
                    break;
                case 2:
                    if(profString.contains("2")){
                        profileNames.add(2);
                        Log.i("6705del", "contains 2 called");
                        toJSONMethods(2);}
                    break;
                case 3:
                    if(profString.contains("3")){
                        profileNames.add(3);
                        Log.i("6705del", "contains 3 called");
                        toJSONMethods(3);}
                    break;
            }
        }

//this section wipes goals that are already in the user's Parse cloud
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
          //  query.whereNotContainedIn("Profile", profilenames);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> goalData, ParseException e) {
                    if (e == null) {
                        size = goalData.size();
                        counter = 1;
                        Log.d("6705del", goalData.size() + " scores dont match");
                        if (goalData.size() < 1) {
                            saveToParseE();
                        } else {
                            for (ParseObject goalRow : goalData) {
                                try {
                                    Log.i("6705del", goalRow.toString() + " being deleted?");
                                    goalRow.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
//this inner section calls saveToParseE() once the cloud data has been wiped. Also done above, if it has nothing to wipe.
                                                if (counter == size) {
                                                    saveToParseE();
                                                }
                                                counter++;
                                            }
                                        }
                                    });
                                } catch (Exception ee) {
                                }
                            }
                        }
                    }
                }
            });
        }

    public void toJSONMethods(int x){
 //this section calls the JSONconversion methods, using the same variable number - which is the profile number/name

         Log.i("6705del JSONMETHCALLED", "inside loop");
        JSONgoals.add(convertGoalsToJSON(x));
        JSONFuturegoals.add(convertFutureGoalsToJSON(x));
        JSONPastTotals.add(convertPastTotalsToJSON(x));
        profilenames.add(prefs.getString("prof" + x + "Text", " "));
        Log.i("6705del", "JSONMETHOD" + JSONgoals.toString());
    }

    public JSONObject convertGoalsToJSON(int i){
//this section is ok? it opens the profilename I's database and saves to the JSONobject
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);
        String strJson;
        try {
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

        strJson="{\"Goals\":[";

        while (cancel == false) {

            try {
                Log.i("6705saveToCloud", "inside Json loop");
                    if (pos == 0){strJson += "{";}else{strJson += ",{";}
//strings need to be held inside \" \" to allow for spaces?!!
                    strJson += "" +
                            "\"name\":" + " \" " + c.getString(nameIndex) + " \" " + "," +
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
                            "}";}
            catch (Exception e) {
                cancel = true;Log.i("6705why", "canceled from index out of bounds exception/or Json Error");e.printStackTrace();
                strJson +="}";
            }

            pos++;
            c.moveToNext();
        }
        strJson += "]}";
        }catch(Exception e){strJson= "{\"Goals\":[]}";}//if nae table opened yet - eg profile made but not started = empty?
        try {return new JSONObject(strJson);}
        catch(Exception e){e.printStackTrace();
Log.i("6705del", "NULL OBJECT RETURNED BECAUSE OF EXCEPTION");

            Log.i("6705del", "NULL OBJECT "+e.toString());

            return null;}


    }

    public JSONObject convertFutureGoalsToJSON(int i) {

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);
        String strJson;
        try{
        Cursor c = myDatabase.rawQuery("SELECT * FROM FgoalsTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int totalIndex = c.getColumnIndex("total");

        c.moveToFirst();
        boolean cancel = false;
        int pos = 0;

        strJson = "{\"Goals\":[";

        while (c != null && cancel == false) {

            try {// why must i have this?? and the cancel bit too... tidy this all up
                Log.i("6705saveToCloud", "inside Json loop");

                if (pos == 0) {
                    strJson += "{";
                } else {
                    strJson += ",{";
                }

                strJson += "" +

                        "\"name\":" + " \" " + c.getString(nameIndex) + " \" " + "," +
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

        }catch(Exception e){strJson= "{\"Goals\":[]}";}//if nae table opened yet - eg profile made but not started = empty?
        try {return new JSONObject(strJson);}
        catch(Exception e){e.printStackTrace();}

        return null;
    }

    public JSONObject convertPastTotalsToJSON(int i) {


        SQLiteDatabase myDatabase = this.openOrCreateDatabase("GoalApp" + i, MODE_PRIVATE, null);
        String strJson;
        try {
            Cursor c = myDatabase.rawQuery("SELECT * FROM pastTotalsTbl", null);
            int totalsIndex = c.getColumnIndex("totalPercent");

            c.moveToFirst();
            boolean cancel = false;
            int pos = 0;

            strJson = "{\"Past\":[";

            while (c != null && cancel == false) {

                try {// why must i have this?? and the cancel bit too... tidy this all up
                    Log.i("6705saveToCloud", "inside Json loop");

                    if (pos == 0) {
                        strJson += "{";
                    } else {
                        strJson += ",{";
                    }

                    strJson += "" +
                            "\"pastTotal\":" + c.getInt(totalsIndex) + "}";

                } catch (Exception e) {
                    cancel = true;
                    Log.i("6705why", "canceled from index out of bounds exception/or Json Error");
                    e.printStackTrace();
                    strJson += "}";
                }

                pos++;
                c.moveToNext();
            }
            strJson += "]}";

        }catch(Exception e){strJson="{\"Past\":[]}";}//this makes sure that if table isnt created beyond profile being made, then it passes an empty JSON object rahter than erroring at TableNotCreated
        try {return new JSONObject(strJson);}
        catch(Exception e) {e.printStackTrace();}

        return null;

    }

    public void saveToParseE(){

        Log.i("6705del", "save goals called");
        Log.i("6705del", "save goals called "+JSONgoals.size());
        for (int ii = 0; ii < JSONgoals.size(); ii++) {

            Log.i("6705del", "save goals called loop");

            ParseObject goal = new ParseObject("GoalData");
            goal.put("Goals", JSONgoals.get(ii));

            Log.i("6705del", "save goals called looppast");
            goal.put("username", ParseUser.getCurrentUser().getUsername());

            String profilename = prefs.getString("prof" + profileNames.get(ii) + "Text", " ");
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

    public void loadFromCloud(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> goalData, ParseException e) {
                if (e == null) {

                    String name="";
                    int total=800;
                    int done=800;
                    double percent=800;
                    int b0=800;
                    int b1=800;
                    int b2=800;
                    int b3=800;
                    int b4=800;
                    int b5=800;
                    int b6=800;
                    int pastTotals=800;

                    Log.d("6705del", goalData.size() + " scores loading down");
                    if (goalData.size() < 1) {
                        //do something if no rows returned
                    } else {

                        SharedPreferences.Editor editor= prefs.edit();
                        editor.clear();
                        editor.apply();

                        int count=0;
                        String profileString="";
                        for (ParseObject goalRow : goalData) {

                            profileString = profileString + count;
                            editor.putString("prof" + count + "Text", goalRow.getString("Profile"));
                            count++;

                            try {
                                JSONObject goal = goalRow.getJSONObject("Goals");
                                Log.i("6705del", "JSONobject WORKED1" + goalRow.getJSONObject("Goals"));
                                JSONArray jsonArray1 = goal.optJSONArray("Goals");
                                Log.i("wheres the error", ""+0);
                                //Iterate the jsonArray and print the info of JSONObjects
                                for (int i = 0; i < jsonArray1.length()-1; i++) {
                                    JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                    Log.i("wheres the error", ""+1);
                                    name = jsonObject.optString("name");
                                    Log.i("wheres the error", ""+2+ " " +name);
                                    if(jsonObject.optString("total").equals("")){
                                        Log.i("6705del", "reporting empty profile");
                                    }else{
                                    total = Integer.parseInt(jsonObject.optString("total"));
                                    Log.i("wheres the error", ""+3);
                                    done = Integer.parseInt(jsonObject.optString("done"));
                                    Log.i("wheres the error", ""+4);
                                    percent = Double.parseDouble(jsonObject.optString("percent"));
                                    Log.i("wheres the error", ""+5);
                                    b0 = Integer.parseInt(jsonObject.optString("b0"));
                                    Log.i("wheres the error", ""+6);
                                    b1 = Integer.parseInt(jsonObject.optString("b1"));
                                    Log.i("wheres the error", ""+7);
                                    b2 = Integer.parseInt(jsonObject.optString("b2"));
                                    Log.i("wheres the error", ""+8);
                                    b3 = Integer.parseInt(jsonObject.optString("b3"));
                                    Log.i("wheres the error", ""+9);
                                    b4 = Integer.parseInt(jsonObject.optString("b4"));
                                    Log.i("wheres the error", ""+10);
                                    b5 = Integer.parseInt(jsonObject.optString("b5"));
                                    Log.i("wheres the error", ""+11);
                                    b6 = Integer.parseInt(jsonObject.optString("b6"));
                                    Log.i("wheres the error", ""+13);
                                    //end of a goal
                                    }
                                }//end of all goals - a profile's goals.
                            } catch (Exception e1) {
                                Log.i("6705del", " problem making JSONobject1" + e1.toString());}
                                try {
                                    JSONObject futureGoal = goalRow.getJSONObject("FutureGoals");
                                    Log.i("6705del", "JSONobject WORKED2" + goalRow.getJSONObject("FutureGoals"));
                                    JSONArray jsonArray2 = futureGoal.optJSONArray("Goals");
                                    //Iterate the jsonArray and print the info of JSONObjects
                                    for (int i = 0; i < jsonArray2.length()-1; i++) {
                                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                        name = jsonObject.optString("name");
                                        total = Integer.parseInt(jsonObject.optString("total"));
                                    }
                                } catch (Exception e2) {
                                    Log.i("6705del", " problem making JSONobject2" + e2.toString());}
                                    try {
                                        JSONObject pastTotalsObject = goalRow.getJSONObject("PastTotals");
                                        Log.i("6705del", "JSONobject WORKED3" + goalRow.getJSONObject("PastTotals"));
                                        JSONArray jsonArray3 = pastTotalsObject.optJSONArray("Past");
                                        //Iterate the jsonArray and print the info of JSONObjects
                                        for (int i = 0; i < jsonArray3.length()-1; i++) {
                                            JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                            pastTotals = Integer.parseInt(jsonObject.optString("pastTotal"));
                                        }
                                    } catch (Exception e3) {
                                        Log.i("6705del", " problem making JSONobject3" + e3.toString());}


                            editor.putString("profiles", profileString);
                            editor.putInt("count", count);
                            editor.apply();

                        //end of looping through rows

                        }
                    }
                }
            }
        });
    }

}
