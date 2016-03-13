package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.app.ListActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends ListActivity implements View.OnClickListener, View.OnLongClickListener {


    static ProfileDatastore profileDatastore;
    static CustomArrayAdapterProfiles adapter;
    static SQLiteDatabase profilesDatabase;

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


        adapter = new CustomArrayAdapterProfiles(this, profileDatastore.profiles);
        setListAdapter(adapter);
        Button newProfileButton =(Button) findViewById(R.id.newProfileButton);
        newProfileButton.setOnClickListener(this);
        newProfileButton.setTag("newgoal");

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
                profileDatastore.profiles.add(new Profile(c.getString(nameIndex), c.getInt(databaseNumIndex)));
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
                Profile prof = profileDatastore.profiles.get(i);
                profilesDatabase.execSQL("INSERT INTO profilesTbl (name, databaseNum) VALUES ('" + prof.name + "', " + prof.databaseNum + ")");
        }
    }



    public void onClick(final View view){


        if(view.getTag().equals("Accounts")){

            Intent intentAccount = new Intent(this, SettingsScrn.class)              ;
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
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name your Profile");
                final EditText profileInput = new EditText(this);
                builder.setView(profileInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {

                        profileDatastore.addProfile(profileInput.getText().toString());
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


    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
