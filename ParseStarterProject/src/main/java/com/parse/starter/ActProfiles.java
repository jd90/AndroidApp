package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;


import android.app.ListActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

public class ActProfiles extends ListActivity implements View.OnClickListener, TextWatcher {


    static ProfileDatastore profileDatastore;
    static CustAdapterProfiles adapter;
    static SQLiteDatabase profilesDatabase;
    EditText profileInput;

    TextView userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiles_page_attempt2);

        profileDatastore=new ProfileDatastore();


        profilesDatabase =  this.openOrCreateDatabase("Profiles", MODE_PRIVATE, null);
        profilesDatabase.execSQL("CREATE TABLE IF NOT EXISTS profilesTbl (name VARCHAR, databaseNum INT(3))");
        profilesDatabase.execSQL("CREATE TABLE IF NOT EXISTS countTbl (count INT(3))");

        loadProfiles();
        loadCount();

        ImageView sharkLogo =(ImageView) findViewById(R.id.windowTitle);
        sharkLogo.setOnClickListener(this);
        sharkLogo.setTag("Accounts");

        userStatus =(TextView) findViewById(R.id.user_status);
        userStatus.setTag("Accounts");
        userStatus.setOnClickListener(this);
        checkSignedInStatus();

        adapter = new CustAdapterProfiles(this, profileDatastore.profiles);
        setListAdapter(adapter);
        Button newProfileButton =(Button) findViewById(R.id.newProfileButton);
        newProfileButton.setOnClickListener(this);
        newProfileButton.setTag("newgoal");
        profileInput= new EditText(this);
        profileInput.addTextChangedListener(this);
    }
    public void loadCount(){
        profileDatastore.count=0;
        Cursor c = profilesDatabase.rawQuery("SELECT * FROM countTbl", null);
        int countIndex = c.getColumnIndex("count");

        c.moveToFirst();
        boolean cancel=false;
        while (c != null&& cancel==false) {
            try {
                profileDatastore.count = (c.getInt(countIndex));
                c.moveToNext();
            }catch(Exception e){cancel = true; Log.i("6705why", "canceled from index out of bounds exception");}
        }
    }
    public void loadProfiles(){
        profileDatastore.profiles.clear();
        Cursor c = profilesDatabase.rawQuery("SELECT * FROM profilesTbl", null);
        int nameIndex = c.getColumnIndex("name");
        int databaseNumIndex = c.getColumnIndex("databaseNum");

        c.moveToFirst();
        boolean cancel=false;
        while (c != null&& cancel==false) {
            try {
                profileDatastore.profiles.add(new ClassProfile(c.getString(nameIndex), c.getInt(databaseNumIndex)));
                c.moveToNext();
            }catch(Exception e){cancel = true; Log.i("6705why", "canceled from index out of bounds exception");}
        }
    }
    public static void saveCount(){
        profilesDatabase.execSQL("delete from countTbl");
        profilesDatabase.execSQL("INSERT INTO countTbl (count) VALUES (" + profileDatastore.count + ")");
    }
    public static void saveProfiles(){

        profilesDatabase.execSQL("delete from profilesTbl");

            for(int i=0; i<profileDatastore.profiles.size(); i++){
                ClassProfile prof = profileDatastore.profiles.get(i);
                profilesDatabase.execSQL("INSERT INTO profilesTbl (name, databaseNum) VALUES ('" + prof.name + "', " + prof.databaseNum + ")");
        }
    }


    public void onClick(final View view){


        if(view.getTag().equals("Accounts")){

            Intent intentAccount = new Intent(this, ActSettings.class);
            startActivity(intentAccount);

        }else {

            if (profileDatastore.profiles.size() >= 4) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(this);
                confirm.setTitle("Max Reached");
                confirm.setMessage("Sorry, Maximum of 4 Profiles Reached!");
                confirm.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                        .setIcon(R.drawable.goal_shark_logo1)
                        .show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name your Profile");
                builder.setView(profileInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {

                        String title = profileInput.getText().toString().toUpperCase();
                        profileDatastore.addProfile(title);
                        adapter.notifyDataSetChanged();
                        saveCount();
                        saveProfiles();
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

        }

    }
    public void checkSignedInStatus() {

        if (ParseUser.getCurrentUser() != null) {
            userStatus.setText("signed in as " + ParseUser.getCurrentUser().getUsername());
        } else {
            userStatus.setText("Click to Sign In");
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("contains", profileInput.getText().toString());

        if(profileInput.getText().toString().contains("'")||profileInput.getText().toString().contains("\"")||profileInput.getText().toString().contains("\\")){
            profileInput.setText(profileInput.getText().toString().substring(0, profileInput.length()-1));
            profileInput.setSelection(profileInput.getText().toString().length());//changes cursor to still be at the end
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
