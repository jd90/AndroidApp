package com.parse.starter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ActSettings extends AppCompatActivity implements View.OnClickListener{
    EditText username;
    EditText password;
    TextView user;
    Button signin;
    Button signup;
    Button logout;
    Button save;
    Button load;
    ImageView cloud;
    ImageView friends;
    ImageView settings;
    ImageView notifications;
    ImageView goalSharkLogo;
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    ArrayList<JSONObject> JSONgoals = new ArrayList<>();
    ArrayList<JSONObject> JSONFuturegoals = new ArrayList<>();
    ArrayList<JSONObject> JSONPastTotals = new ArrayList<>();
    ArrayList<JSONObject> JSONPastGoals = new ArrayList<>();
    ArrayList<JSONObject> JSONProfiles = new ArrayList<>();
    static int count;
    static int size;
    static int counter;


    LinearLayout grid1;
    LinearLayout grid2;
    LinearLayout loginform;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_scrn2);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        databaseHelper = new DatabaseHelper(this);


        grid1 = (LinearLayout) findViewById(R.id.grid1);
        grid2 = (LinearLayout) findViewById(R.id.grid2);
        loginform = (LinearLayout) findViewById(R.id.loginform);
        goalSharkLogo =(ImageView) findViewById(R.id.windowTitle);


        back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);
        back.setTag("back");

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

      //  save = (Button) findViewById(R.id.save);
       // load = (Button) findViewById(R.id.load);
//        save.setTag("save");
     //   load.setTag("load");
   //     save.setOnClickListener(this);
   //     load.setOnClickListener(this);
        cloud = (ImageView) findViewById(R.id.cloud);
        friends = (ImageView) findViewById(R.id.friends);
        settings = (ImageView) findViewById(R.id.settings);
        notifications = (ImageView) findViewById(R.id.notifications);
        notifications.setOnClickListener(this);
        notifications.setTag("notifications");
        settings.setOnClickListener(this);
        settings.setTag("settings");
        cloud.setOnClickListener(this);
        friends.setOnClickListener(this);
        cloud.setTag("cloud");
        friends.setTag("friends");

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

        if(v.getTag().equals("back")){
            onBackPressed();

        }else {
            ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connect.getActiveNetworkInfo() != null) {
                if (v.getTag().equals("logout")) {
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast t = Toast.makeText(getApplicationContext(), "Thats ye logged oot", Toast.LENGTH_SHORT);
                            t.show();
                            checkSignedIn();
                        }
                    });
                } else if (v.getTag().equals("cloud") || v.getTag().equals("friends") || v.getTag().equals("settings") || v.getTag().equals("notifications")) {
                    //Log.i("6705saveToCloudswitch", "called");


                    switch (v.getTag().toString()) {
                        case "notifications":

                            //NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this);
                            //notifBuilder.setSmallIcon(R.drawable.goal_shark_logo1);
                            //notifBuilder.setContentTitle("Enjoying Yer Doss, Aye?");
                            //notifBuilder.setContentText("Get Yer Goals Done Ya Weapon!");
                            //notifBuilder.setTicker("Enjoying Yer Doss, AYEEE?");
                            //notifBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
                            //notifBuilder.setAutoCancel(true);

                            //Intent openProfiles = new Intent(this, ActProfiles.class);
                            //TaskStackBuilder tstack = TaskStackBuilder.create(this);
                            //tstack.addParentStack(ActProfiles.class);
                            //tstack.addNextIntent(openProfiles);

                            //PendingIntent pendingIntent = tstack.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                            //notifBuilder.setContentIntent(pendingIntent);
                            //for when user clicks it - pendingIntent handles - takes the activity from TaskStackBuilder

                            //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            //int notificationID = 1;
                            //notificationManager.notify(notificationID, notifBuilder.build());

                           /* Calendar calendar = Calendar.getInstance();
                            long alertTime = new GregorianCalendar().getTimeInMillis()+5000;
                            Intent alertIntent = new Intent(this, AlertReceiver.class);
                            PendingIntent alarmIntent = PendingIntent.getService(this, 0, alertIntent, 0);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, alarmIntent);
*/          PopupMenu popup2 = new PopupMenu(getApplicationContext(), notifications);

                            //Inflating the Popup using xml file
                            popup2.getMenuInflater().inflate(R.menu.notifications_menu, popup2.getMenu());

                            //registering popup with OnMenuItemClickListener
                            popup2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {

                                    if (String.valueOf(item.getTitle()).equals("Set Notifications On")) {

                                        Intent alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
                                        final PendingIntent pendingIntent =
                                                PendingIntent.getBroadcast
                                                        (getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        //pendingintent setting what to do - using intent-

                                        Calendar calendar = new GregorianCalendar();
                                        calendar.set(Calendar.HOUR_OF_DAY, 20);
                                        calendar.set(Calendar.MINUTE, 0);

                                        Long alertTime = calendar.getTimeInMillis();
                                        //Long alertTime = new
                                        //   GregorianCalendar().getTimeInMillis()+5*1000;
                                        //had to put use permission into manifest for set alarm
                                        AlarmManager alarmMan =
                                                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        //alarmMan.set(AlarmManager.RTC_WAKEUP,alertTime,pendingIntent );
                                        alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY, pendingIntent);
                                        //alarmmanager sets to wakeup after the time and uses pendingintent to call the broadcast AlertReceiver

                                    } else {
                                        Intent alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);

                                        final PendingIntent pendingIntent =
                                                PendingIntent.getBroadcast
                                                        (getApplicationContext(), 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                        AlarmManager alarmMan =
                                                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                        alarmMan.cancel(pendingIntent);
                                    }

                                return true;}

                            });
                            popup2.show();

                            break;
                        case "settings":
                            //Creating the instance of PopupMenu
                            if(ParseUser.getCurrentUser() ==null){Toast t = Toast.makeText(this, "Option Available to Signed in Users Only", Toast.LENGTH_SHORT);t.show();}
                            else {
                                PopupMenu popup1 = new PopupMenu(getApplicationContext(), settings);

                                //Inflating the Popup using xml file
                                popup1.getMenuInflater().inflate(R.menu.settings_menu, popup1.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {

                                        if (String.valueOf(item.getTitle()).equals("Change Password")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActSettings.this);
                                            builder.setTitle("Change Password");
                                            LinearLayout l = new LinearLayout(ActSettings.this);
                                            l.setOrientation(LinearLayout.VERTICAL);
                                            final EditText oldInput = new EditText(ActSettings.this);
                                            final EditText newInput = new EditText(ActSettings.this);
                                            oldInput.setTag("old");
                                            newInput.setTag("new");
                                            oldInput.setHint("Enter Your New Password...");
                                            LinearLayout ldivider = new LinearLayout(getApplicationContext());
                                            ldivider.setMinimumHeight(2);
                                            newInput.setHint("Confirm New Password...");
                                            l.addView(oldInput);
                                            l.addView(ldivider);
                                            l.addView(newInput);
                                            builder.setView(l);
                                            builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int num) {

                                                    if (oldInput.getText().toString().equals(newInput.getText().toString())) {

                                                        ParseUser parseUser = ParseUser.getCurrentUser();
                                                        parseUser.setPassword(oldInput.getText().toString());
                                                        parseUser.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {
                                                                if (e == null) {
                                                                    user.setText("signed in as " + ParseUser.getCurrentUser().getUsername());
                                                                    Toast t = Toast.makeText(getApplicationContext(), "Change Successful!", Toast.LENGTH_SHORT);
                                                                    t.show();
                                                                }
                                                            }
                                                        });
                                                        dialog.cancel();
                                                    } else {
                                                        Toast t = Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT);
                                                        t.show();
                                                    }
                                                }


                                            });

                                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int num) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.setCancelable(false);

                                            builder.show();

                                        } else {


                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActSettings.this);
                                            builder.setTitle("Set Account Email");
                                            LinearLayout l = new LinearLayout(ActSettings.this);
                                            l.setOrientation(LinearLayout.VERTICAL);
                                            final EditText oldInput = new EditText(ActSettings.this);
                                            final EditText newInput = new EditText(ActSettings.this);
                                            oldInput.setTag("old");
                                            newInput.setTag("new");
                                            oldInput.setHint("Enter Your Email Address");
                                            l.addView(oldInput);
                                            builder.setView(l);
                                            builder.setPositiveButton("Set Email", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int num) {

                                                    if (!oldInput.getText().toString().contains("@") || !oldInput.getText().toString().contains(".")) {
                                                        Toast t = Toast.makeText(getApplicationContext(), "Invalid Email Address!", Toast.LENGTH_SHORT);
                                                        t.show();
                                                    } else {

                                                        ParseUser parseUser = ParseUser.getCurrentUser();
                                                        parseUser.setEmail(oldInput.getText().toString());
                                                        parseUser.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {
                                                                if (e == null) {
                                                                    //user.setText("signed in as " + ParseUser.getCurrentUser().getUsername());
                                                                    Toast t = Toast.makeText(getApplicationContext(), "Email Address Set!", Toast.LENGTH_SHORT);
                                                                    t.show();
                                                                }
                                                            }
                                                        });

                                                        dialog.cancel();
                                                    }
                                                }


                                            });

                                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int num) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.setCancelable(false);

                                            builder.show();
                                        }

                                        return true;

                                    }
                                });

                                popup1.show();//showing popup menu

                            }
                            break;
                        case "friends":
                            if(ParseUser.getCurrentUser() ==null){Toast t = Toast.makeText(this, "Option Available to Signed in Users Only", Toast.LENGTH_SHORT);t.show();}
                            else {
                                Intent intent = new Intent(this, Frag5Friends.class);
                                //intent.putExtra("firstweek", false);
                                int a = 4; //request code? (receives back request code, resultcode, intent)?
                                startActivityForResult(intent, a);
                            }
                            break;
                        case "cloud":
                            if(ParseUser.getCurrentUser() ==null){Toast t = Toast.makeText(this, "Option Available to Signed in Users Only", Toast.LENGTH_SHORT);t.show();}
                            else {
                                //Creating the instance of PopupMenu
                                PopupMenu popup3 = new PopupMenu(getApplicationContext(), cloud);
                                //Inflating the Popup using xml file
                                popup3.getMenuInflater().inflate(R.menu.cloud_menu, popup3.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {

                                        if (String.valueOf(item.getTitle()).equals("Save To Cloud")) {
                                            saveToCloud();

                                        } else {
                                            loadFromParse();
                                        }

                                        return true;
                                    }
                                });

                                popup3.show();//showing popup menu
                            }
                            break;
                    }

                } else {
                    if (v instanceof Button) {
                        signInOrSignUp(v);

                    } else {
                        if (v.getTag().equals("cloud")) {
                            save.setVisibility(View.VISIBLE);
                            load.setVisibility(View.VISIBLE);
                            friends.setVisibility(View.GONE);
                            save.setText("SAVE TO CLOUD");
                            save.setTag("save");
                            load.setTag("load");
                            load.setText("LOAD FROM CLOUD");
                        }
                        if (v.getTag().equals("friends")) {
                            cloud.setVisibility(View.GONE);
                            save.setVisibility(View.VISIBLE);
                            load.setVisibility(View.VISIBLE);
                            save.setText("MANAGE FRIENDS");
                            save.setTag("add");
                            load.setTag("privacy");
                            load.setText("SHARE SETTINGS");

                        }

                    }
                }

            } else {
                Toast t = Toast.makeText(this, "Please check network connection!", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public void checkSignedIn(){
        if(ParseUser.getCurrentUser() != null) {
            Log.i("6705logcurrent", ParseUser.getCurrentUser().getUsername());

            user.setText("signed in as " + ParseUser.getCurrentUser().getUsername());
            //save.setVisibility(View.VISIBLE);
            //load.setVisibility(View.VISIBLE);
            loginform.setVisibility(View.GONE);
            grid1.setVisibility(View.VISIBLE);
            grid2.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            goalSharkLogo.setVisibility(View.VISIBLE);

        }else{
            user.setText("not signed in");
            loginform.setVisibility(View.VISIBLE);
            grid1.setVisibility(View.VISIBLE);
            grid2.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            goalSharkLogo.setVisibility(View.GONE);

        }
    }

    public void saveToCloud() {

        //this must be cleared?? otherwise if you decrease the size and it hasnt been shut to stop persistence then its still got more in it = nullpointer because its used as the loop i<?
        JSONgoals.clear();JSONFuturegoals.clear();JSONPastTotals.clear();JSONPastGoals.clear();

        JSONgoals.add(convertGoalsToJSON());
        Log.i("44331", "json2"+convertGoalsToJSON().toString());
        JSONFuturegoals.add(convertFutureGoalsToJSON());
        JSONPastTotals.add(convertPastTotalsToJSON());
        JSONProfiles.add(convertProfilesToJSON());


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
                            saveToParse();
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
                                                    saveToParse();
                                                }

                                                counter++;
                                            }
                                        }
                                    });
                                } catch (Exception ee) {
                                    Log.i("6705ERRORsave", ee.toString());
                                }
                            }
                        }
                    }
                }
            });
        }



    public JSONObject convertProfilesToJSON() {

        ArrayList<ClassProfile> profileList = databaseHelper.getAllProfiles();
        String strJson;

        if(profileList.size()>0){


            strJson = "{\"Profile\":[";
            for (int i = 0; i < profileList.size(); i++) {
                if (i == 0) {
                    strJson += "{";
                } else {
                    strJson += ",{";
                }

                strJson += "" +
                        "\"name\":\"" + profileList.get(i).name + "\"," +
                        "\"refreshDay\": '" + profileList.get(i).refreshDay + "' ";
                strJson += "}";
            }
            strJson += "]}";
        }else {
            strJson = "{\"Profile\":[]}";//this makes sure that if table isnt created beyond profile being made, then it passes an empty JSON object rahter than erroring at TableNotCreated
        }
        try {return new JSONObject(strJson);}
        catch(Exception e) {e.printStackTrace();
            Log.i(";;;;", e.toString());}

        return null;
    }

    public JSONObject convertGoalsToJSON(){


        ArrayList<ClassGoal> goalList = databaseHelper.getAllGoals();
        String strJson;

        if(goalList.size()>0){

            strJson = "{\"Goals\":[";
            for (int i = 0; i < goalList.size(); i++) {
                if (i == 0) {
                    strJson += "{";
                } else {
                    strJson += ",{";
                }
                strJson += "" +
                        "\"profileName\":"+ "\"" + goalList.get(i).profileName + "\""+ "," +//maybe i should be quoting these to protect the strings?
                        "\"name\":"+ "\"" + goalList.get(i).getName() + "\""+ "," +
                        "\"total\":" + goalList.get(i).getTotal() + "," +
                        "\"done\":" + goalList.get(i).getDone() + "," +
                        "\"b0\":" + goalList.get(i).getButton(0) + "," +
                        "\"b1\":" + goalList.get(i).getButton(1) + "," +
                        "\"b2\":" + goalList.get(i).getButton(2) + "," +
                        "\"b3\":" + goalList.get(i).getButton(3) + "," +
                        "\"b4\":" + goalList.get(i).getButton(4) + "," +
                        "\"b5\":" + goalList.get(i).getButton(5) + "," +
                        "\"b6\":" + goalList.get(i).getButton(6) + "," +
                        "\"bt0\":" + goalList.get(i).buttonsThrough[0] + "," +
                        "\"bt1\":" + goalList.get(i).buttonsThrough[1] + "," +
                        "\"bt2\":" + goalList.get(i).buttonsThrough[2] + "," +
                        "\"bt3\":" + goalList.get(i).buttonsThrough[3] + "," +
                        "\"bt4\":" + goalList.get(i).buttonsThrough[4] + "," +
                        "\"bt5\":" + goalList.get(i).buttonsThrough[5] + "," +
                        "\"bt6\":" + goalList.get(i).buttonsThrough[6] + ",";
                int typeNum;
                        if(goalList.get(i).type){typeNum =1;}else{typeNum=0;}
                strJson += "\"type\": '"+typeNum+"' ";
                        //"\"type\": '" + goalList.get(i).type + "' ";
                strJson += "}";
            }
            strJson += "]}";
        }else {
            strJson = "{\"Goals\":[]}";//this makes sure that if table isnt created beyond profile being made, then it passes an empty JSON object rahter than erroring at TableNotCreated
        }
        Log.i("44331", "json3"+strJson);
        try {return new JSONObject(strJson);}
        catch(Exception e) {e.printStackTrace();
            Log.i("44331", "jsonE" + e.toString());
        }

        return null;


    }

    public JSONObject convertFutureGoalsToJSON() {

        ArrayList<ClassGoal> fgoalList = databaseHelper.getAllFutureGoals();
        String strJson;

        if(fgoalList.size()>0){


            strJson = "{\"Goals\":[";
            for (int i = 0; i < fgoalList.size(); i++) {
                if (i == 0) {
                    strJson += "{";
                } else {
                    strJson += ",{";
                }
                strJson += "" +
                        "\"profileName\":" + "\""+ fgoalList.get(i).profileName + "\""+ "," +//maybe i should be quoting these to protect the strings?
                        "\"name\":" + "\""+ fgoalList.get(i).getName() +  "\""+"," +
                        "\"total\":" + fgoalList.get(i).getTotal() + ",";
                int typeNum;
                if(fgoalList.get(i).type){typeNum =1;}else{typeNum=0;}
                strJson += "\"type\": '"+typeNum+"' ";
                //"\"type\": '" + goalList.get(i).type + "' ";
                strJson += "}";
            }
            strJson += "]}";
        }else {
            strJson = "{\"Goals\":[]}";//this makes sure that if table isnt created beyond profile being made, then it passes an empty JSON object rahter than erroring at TableNotCreated
        }
        try {return new JSONObject(strJson);}
        catch(Exception e) {e.printStackTrace();
        Log.i("44331", "jsonfuture"+e.toString());}

        return null;
    }

    public JSONObject convertPastTotalsToJSON() {

        ArrayList<ClassArchiveItem> archiveList = databaseHelper.getAllPastTotals();
        String strJson;

        if(archiveList.size()>0){


        strJson = "{\"Past\":[";
        for (int i = 0; i < archiveList.size(); i++) {
            if (i == 0) {
                strJson += "{";
            } else {
                strJson += ",{";
            }

            strJson += "" +
                    "\"profileName\":" + "\""+ archiveList.get(i).profileName + "\""+ "," +
                    "\"pastTotal\":" + archiveList.get(i).percent + "," +
                    "\"pastDate\":" + "\""+ archiveList.get(i).date + "\"";
            strJson += "}";
        }
        strJson += "]}";
    }else {
            strJson = "{\"Past\":[]}";//this makes sure that if table isnt created beyond profile being made, then it passes an empty JSON object rahter than erroring at TableNotCreated
        }
        try {return new JSONObject(strJson);}
        catch(Exception e) {e.printStackTrace();}

    return null;
    }


    public void saveToParse(){


        if(JSONgoals.size()==0){Toast t = Toast.makeText(getApplicationContext(), "Yu'v nae profiles tae save, ya chancer!", Toast.LENGTH_SHORT);t.show();}
        for (int ii = 0; ii < JSONgoals.size(); ii++) {

            Log.i("44331", "json" + JSONgoals.get(ii).toString());

            ParseObject goal = new ParseObject("GoalData");


            Log.i("6705del", "save goals called looppast");
            goal.put("username", ParseUser.getCurrentUser().getUsername());

            goal.put("Goals", JSONgoals.get(ii));
            goal.put("FutureGoals", JSONFuturegoals.get(ii));
            goal.put("PastTotals", JSONPastTotals.get(ii));
            goal.put("Profiles", JSONProfiles.get(ii));


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

    public void loadFromParse() {


        //get profiles and delete them and their associated databases here?

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GoalData");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> goalData, ParseException e) {
                if (e == null) {


                    String goalName = "";
                    String profileName = "";
                    int total = 800;
                    int done = 800;
                    double percent = 800;
                    int b0 = 800;
                    int b1 = 800;
                    int b2 = 800;
                    int b3 = 800;
                    int b4 = 800;
                    int b5 = 800;
                    int b6 = 800;
                    int bt0 = 800;
                    int bt1 = 800;
                    int bt2 = 800;
                    int bt3 = 800;
                    int bt4 = 800;
                    int bt5 = 800;
                    int bt6 = 800;
                    int type = 3;
                    int pastTotals = 800;
                    String pastDates = "";

                    if (goalData.size() < 1) {
                        //do something if no rows returned
                        Toast t = Toast.makeText(getApplicationContext(), "Yu'v nae goals tae load, ya pudding!", Toast.LENGTH_SHORT);
                        t.show();
                    } else {

                        databaseHelper.clearAllTbls();
                        Log.i("44331", "jsonClearingtbls");


                    for (ParseObject goalRow : goalData) {


                        count++;
                        try {
                            JSONObject futureGoal = goalRow.getJSONObject("Profiles");
                            JSONArray jsonArray2 = futureGoal.optJSONArray("Profile");
                            //Iterate the jsonArray and print the info of JSONObjects
                            for (int i = 0; i < jsonArray2.length()  ; i++) {
                                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                profileName = jsonObject.optString("name");
                                int refreshday = jsonObject.optInt("refreshDay");

                                ClassProfile prof = new ClassProfile(profileName, refreshday);
                                databaseHelper.insertProfile(prof);


                            }
                        } catch (Exception e2) {
                            Log.i("6705del", " problem making JSONobject2" + e2.toString());
                        }
                        try {
                            JSONObject goal = goalRow.getJSONObject("Goals");
                            Log.i("6705del", "JSONobject WORKED1" + goalRow.getJSONObject("Goals"));
                            JSONArray jsonArray1 = goal.optJSONArray("Goals");

                            //THIS BENEFITS FROM THE EMPTY GOAL AT THE END? MEANING ITS STARTED, ELSE IT IS EMPTY COMPLETELY?
                            if (jsonArray1.length() == 0) {//used to delete goals so profile new it wasnt yet started or something here??
                            }//this line checks if profile is empty and sets it to be unstarted(Y)


                            for (int i = 0; i <= jsonArray1.length(); i++) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);


                                goalName = jsonObject.optString("name");
                                profileName = jsonObject.optString("profileName");
                                if (jsonObject.optString("total").equals("")) {
                                    Log.i("6705del", "reporting empty profile");
                                } else {
                                    total = Integer.parseInt(jsonObject.optString("total"));
                                    done = Integer.parseInt(jsonObject.optString("done"));
                                    b0 = Integer.parseInt(jsonObject.optString("b0"));
                                    b1 = Integer.parseInt(jsonObject.optString("b1"));
                                    b2 = Integer.parseInt(jsonObject.optString("b2"));
                                    b3 = Integer.parseInt(jsonObject.optString("b3"));
                                    b4 = Integer.parseInt(jsonObject.optString("b4"));
                                    b5 = Integer.parseInt(jsonObject.optString("b5"));
                                    b6 = Integer.parseInt(jsonObject.optString("b6"));
                                    bt0 = Integer.parseInt(jsonObject.optString("bt0"));
                                    bt1 = Integer.parseInt(jsonObject.optString("bt1"));
                                    bt2 = Integer.parseInt(jsonObject.optString("bt2"));
                                    bt3 = Integer.parseInt(jsonObject.optString("bt3"));
                                    bt4 = Integer.parseInt(jsonObject.optString("bt4"));
                                    bt5 = Integer.parseInt(jsonObject.optString("bt5"));
                                    bt6 = Integer.parseInt(jsonObject.optString("bt6"));
                                    int typeNum;
                                    typeNum = Integer.parseInt(jsonObject.optString("type"));

                                    boolean typeBool;
                                    if (typeNum == 1) {
                                        typeBool = true;
                                    } else {
                                        typeBool = false;
                                    }
                                    ClassGoal g = new ClassGoal(goalName, total, typeBool, profileName);
                                    g.setDone(done);
                                    g.setButton(0, b0);
                                    g.setButton(1, b1);
                                    g.setButton(2, b2);
                                    g.setButton(3, b3);
                                    g.setButton(4, b4);
                                    g.setButton(5, b5);
                                    g.setButton(6, b6);
                                    g.buttonsThrough[0] = bt0;
                                    g.buttonsThrough[1] = bt1;
                                    g.buttonsThrough[2] = bt2;
                                    g.buttonsThrough[3] = bt3;
                                    g.buttonsThrough[4] = bt4;
                                    g.buttonsThrough[5] = bt5;
                                    g.buttonsThrough[6] = bt6;
                                    databaseHelper.insertGoal(g);
                                    Log.i("443312", ""+g.toString());
                                }
                            }

                            //end of all goals - a profile's goals.

                        } catch (Exception e1) {
                            Log.i("6705del", " problem loading profile/goals" + e1.toString());
                        }
                        try {
                            JSONObject futureGoal = goalRow.getJSONObject("FutureGoals");
                            JSONArray jsonArray2 = futureGoal.optJSONArray("Goals");
                            //Iterate the jsonArray and print the info of JSONObjects
                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                                goalName = jsonObject.optString("name");
                                profileName = jsonObject.optString("profileName");
                                total = Integer.parseInt(jsonObject.optString("total"));
                                int typeNum;
                                typeNum = Integer.parseInt(jsonObject.optString("type"));

                                boolean typeBool;
                                if (typeNum == 1) {
                                    typeBool = true;
                                } else {
                                    typeBool = false;
                                }
                                ClassGoal g = new ClassGoal(goalName, total, typeBool, profileName);
                                databaseHelper.insertFutureGoal(profileName, goalName, total, typeNum);


                            }
                        } catch (Exception e2) {
                            Log.i("6705del", " problem making JSONobject2" + e2.toString());
                        }

                        try {
                            JSONObject pastTotalsObject = goalRow.getJSONObject("PastTotals");
                            Log.i("6705del", "JSONobject WORKED3" + goalRow.getJSONObject("PastTotals"));
                            JSONArray jsonArray3 = pastTotalsObject.optJSONArray("Past");
                            //Iterate the jsonArray and print the info of JSONObjects

                            for (int i = 0; i < jsonArray3.length(); i++) {
                                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                                pastTotals = Integer.parseInt(jsonObject.optString("pastTotal"));
                                pastDates = jsonObject.optString("pastDate");
                                profileName = jsonObject.optString("profileName");
                                databaseHelper.insertPastTotal(profileName, new ClassArchiveItem(pastTotals, pastDates));
                            }


                        } catch (Exception e3) {
                            Log.i("6705del", " problem making JSONobject3" + e3.toString());
                        }

                    }

                    }

                    //ActProfiles.profileDatastore.RELOAD METHOD??
                    //CustAdapterProfiles.profiles=databaseHelper.getAllProfiles();//AGAIN MENTAL
                    //CustAdapterProfiles.profiles=databaseHelper.getAllProfiles();
                    //ActProfiles.adapter.notifyDataSetChanged();
                    //here i must reload tables
                    Toast t = Toast.makeText(getApplicationContext(), "Load Successful!", Toast.LENGTH_SHORT);
                    t.show();

                }
            }


        });
    }




  //make an on back pressed to go to profile page? so it can work from ingoal settings dropdown?
    //that or remove that drop down access option
  public void onBackPressed(){

      if(ParseUser.getCurrentUser()!=null){

      Log.i("MethodCalledJ", "L");
      if((cloud.getVisibility()==View.VISIBLE && friends.getVisibility()==View.VISIBLE) || (cloud.getVisibility()==View.GONE && friends.getVisibility()==View.GONE)) {

          ActProfiles.profileDatastore.profiles = databaseHelper.getAllProfiles();
          finish();
          //Intent intentHome = new Intent(this, ActProfiles.class);
          //startActivity(intentHome);

      }
          /*
      }else{
          if(cloud.getVisibility() == View.GONE){
              checkSignedIn();
          }
          if(cloud.getVisibility() == View.VISIBLE){
              checkSignedIn();
          }
      */
      }else{finish();}

  }

}
