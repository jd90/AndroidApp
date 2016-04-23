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

import java.util.ArrayList;
import java.util.List;

public class ActProfiles extends ListActivity implements View.OnClickListener, TextWatcher {


    static ProfileDatastore profileDatastore;
    static CustAdapterProfiles adapter;
    EditText profileInput;
    DatabaseHelper databaseHelper;
    TextView userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiles_page_attempt2);

        //load profiles into datastore
        profileDatastore=new ProfileDatastore();
        databaseHelper = new DatabaseHelper(this);// this should create/open all the tables here?
        List<ClassProfile> profileList = databaseHelper.getProfiles();
        for(int i = 0; i < profileList.size(); i++) {
            profileDatastore.addProfile(profileList.get(i));
        }





        //load profiles

        //save profiles when making a new one

        //in datastore = open goals that have profileID (or name and make input check not exists) matching

        //in datastore save goals when something changes - clear and save from datastore list? as already kind of does


        //setup page
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
                profileInput= new EditText(this);
                profileInput.addTextChangedListener(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name your Profile");
                builder.setView(profileInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {

                        if (profileDatastore.nameTaken(profileInput.getText().toString().toUpperCase())||profileInput.getText().toString().equals("")) {
                            Log.i("44331", "name taken");
                            dialog.cancel();
                        }//fix this to show a warning message of some sort
                        else {
                            String title = profileInput.getText().toString().toUpperCase();
                            ClassProfile p = new ClassProfile(title, 888); //should take a refreshDay here?
                            profileDatastore.addProfile(p);
                            adapter.notifyDataSetChanged();

                            databaseHelper.insertProfile(p);
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

                try {
                    builder.show();
                }catch (Exception e){
                    Log.i("bugproblem", e.toString());
                }
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

        if(profileDatastore.nameTaken(profileInput.getText().toString())){
            profileInput.setText("NAME TAKEN");
        }//fix this to show a warning message of some sort

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
