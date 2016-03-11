package com.parse.starter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    Context context = this;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Intent intent;
    ImageView windowTitle;

    static int  count;
    static String profileString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.profiles_page);

        //using getdefault and getapplicationcontext, instead of 'this', so that i can access this from anywhere??
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        intent = new Intent(this, ProfileMainActivity.class);



        if (prefs.contains("started")) {
            Log.i("loadedfromprefs", "" + prefs.getString("started", ""));
        }

        TextView prof1Text = (TextView) findViewById(R.id.prof1Text);
        TextView prof2Text = (TextView) findViewById(R.id.prof2Text);
        TextView prof3Text = (TextView) findViewById(R.id.prof3Text);
        TextView prof4Text = (TextView) findViewById(R.id.prof4Text);

        LinearLayout prof1 = (LinearLayout) findViewById(R.id.prof1);
        LinearLayout prof2 = (LinearLayout) findViewById(R.id.prof2);
        LinearLayout prof3 = (LinearLayout) findViewById(R.id.prof3);
        LinearLayout prof4 = (LinearLayout) findViewById(R.id.prof4);

        prof1.setOnClickListener(this);
        prof2.setOnClickListener(this);
        prof3.setOnClickListener(this);
        prof4.setOnClickListener(this);
        prof1.setOnLongClickListener(this);
        prof2.setOnLongClickListener(this);
        prof3.setOnLongClickListener(this);
        prof4.setOnLongClickListener(this);

        Log.i("profiles on start",""+prefs.getString("profiles", "empty "));

        String profString = (prefs.getString("profiles", " "));
        if(profString.contains("0")){
            prof1.setVisibility(View.VISIBLE);
            prof1Text.setText(prefs.getString("prof1Text", " "));
        }
        if(profString.contains("1")){
            prof2.setVisibility(View.VISIBLE);
            prof2Text.setText(prefs.getString("prof2Text", " "));
        }

        if(profString.contains("2")){
            prof3.setVisibility(View.VISIBLE);
            prof3Text.setText(prefs.getString("prof3Text", " "));
        }

        if(profString.contains("3")){
            prof4.setVisibility(View.VISIBLE);
            prof4Text.setText(prefs.getString("prof4Text", " "));
        }

        prof1.setTag("0");
        prof2.setTag("1");
        prof3.setTag("2");
        prof4.setTag("3");


        Button newGoal = (Button) findViewById(R.id.newGoalButton);
        newGoal.setText("Add New Profile");
        newGoal.setTag("new");
        newGoal.setOnClickListener(this);


        windowTitle = (ImageView)findViewById(R.id.windowTitle);
        windowTitle.setOnClickListener(this);


    }


    public void onClick(View view) {




        if (view instanceof Button) {

            count= prefs.getInt("count", 0);

            Log.i("6705 count1", ""+count);


            profileString = prefs.getString("profiles", " ");

            if(count >=4){
                AlertDialog.Builder confirm = new AlertDialog.Builder(context);
                confirm.setTitle("Max Reached");
                confirm.setMessage("Sorry, Maximum of 4 Profiles Reached!");
                confirm.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                //pupup max profiles reached
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name your Profile");
                final EditText profileInput = new EditText(this);
                builder.setView(profileInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {

                        if (!profileString.contains("0")) {
                            profileString = profileString + "0";
                            count++;
                            LinearLayout prof = (LinearLayout) findViewById(R.id.prof1);
                            prof.setVisibility(View.VISIBLE);
                            TextView profText = (TextView) findViewById(R.id.prof1Text);
                            profText.setText(String.valueOf(profileInput.getText()));
                            editor.putString("prof1Text", String.valueOf(profileInput.getText()));
                        } else {
                            if (!profileString.contains("1")) {
                                profileString = profileString + "1";
                                count++;
                                LinearLayout prof = (LinearLayout) findViewById(R.id.prof2);
                                prof.setVisibility(View.VISIBLE);
                                TextView profText = (TextView) findViewById(R.id.prof2Text);
                                profText.setText(String.valueOf(profileInput.getText()));
                                editor.putString("prof2Text", String.valueOf(profileInput.getText()));
                            } else {
                                if (!profileString.contains("2")) {
                                    profileString = profileString + "2";
                                    count++;
                                    LinearLayout prof = (LinearLayout) findViewById(R.id.prof3);
                                    prof.setVisibility(View.VISIBLE);
                                    TextView profText = (TextView) findViewById(R.id.prof3Text);
                                    profText.setText(String.valueOf(profileInput.getText()));
                                    editor.putString("prof3Text", String.valueOf(profileInput.getText()));
                                } else {
                                    if (!profileString.contains("3")) {
                                        profileString = profileString + "3";
                                        count++;
                                        LinearLayout prof = (LinearLayout) findViewById(R.id.prof4);
                                        prof.setVisibility(View.VISIBLE);
                                        TextView profText = (TextView) findViewById(R.id.prof4Text);
                                        profText.setText(String.valueOf(profileInput.getText()));
                                        editor.putString("prof4Text", String.valueOf(profileInput.getText()));
                                    }
                                }
                            }
                        }

                        editor.putString("profiles", profileString);
                        editor.putInt("count", count);
                        editor.apply();

                        Log.i("6705 count2", "" + count);
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

        if(view instanceof LinearLayout){

            LinearLayout profile = (LinearLayout) view;
            int profileNum = Integer.parseInt(String.valueOf(profile.getTag()));

            intent.putExtra("profile", profileNum);
            startActivity(intent);

        }

        if(view instanceof ImageView){

            Intent intent2 = new Intent(this, SettingsScrn.class);
            startActivity(intent2);

        }




    }

    @Override
    public boolean onLongClick(View v) {

        final View vi = v;

        final CharSequence[] options = {"Rename", "Delete"};

        AlertDialog.Builder renameDelete = new AlertDialog.Builder(this);
        renameDelete.setCustomTitle(null);
        renameDelete.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Rename your Profile");
                    final EditText profileInput = new EditText(MainActivity.this);
                    builder.setView(profileInput);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            TextView prof;
                            String newTitle = profileInput.getText().toString();
                            switch(Integer.parseInt(vi.getTag().toString())){
                                case 0:
                                    editor.putString("prof1Text", String.valueOf(newTitle));
                                    prof = (TextView) findViewById(R.id.prof1Text);
                                    prof.setText(newTitle);
                                    break;
                                case 1:
                                    editor.putString("prof2Text", String.valueOf(newTitle));
                                    prof = (TextView) findViewById(R.id.prof2Text);
                                    prof.setText(newTitle);
                                    break;
                                case 2:
                                    editor.putString("prof3Text", String.valueOf(newTitle));
                                    prof = (TextView) findViewById(R.id.prof3Text);
                                    prof.setText(newTitle);
                                    break;
                                case 3:
                                    editor.putString("prof4Text", String.valueOf(newTitle));
                                    prof = (TextView) findViewById(R.id.prof4Text);
                                    prof.setText(newTitle);
                                    break;
                            }
                            editor.apply();

                        }});

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            dialog.cancel();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();

                }else

                        {
                            AlertDialog.Builder confirm = new AlertDialog.Builder(MainActivity.this);
                            confirm.setTitle("Delete Profile?");
                            confirm.setMessage("Are you sure you want to delete this profile?");
                            confirm.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    LinearLayout prof = (LinearLayout) vi;
                                    String profNum = String.valueOf(prof.getTag());
                                    String profileString = prefs.getString("profiles", " ");

                                    Log.i("profileString before ", profileString);

                                    profileString = profileString.replace(profNum, "");

                                    Log.i("profileString after ", profileString);

                                    editor.putString("profiles", profileString);
                                    int count = prefs.getInt("count", 0);
                                    Log.i("6705 count3", "" + count);

                                    count--;
                                    editor.putInt("count", count);
                                    editor.apply();


                                    switch (profNum) {

                                        case "0":
                                            prof = (LinearLayout) findViewById(R.id.prof1);
                                            prof.setVisibility(View.GONE);
                                            TextView profText = (TextView) findViewById(R.id.prof1Text);
                                            profText.setText("");
                                            editor.putString("prof1Text", " ");
                                            try {
                                                ProfileMainActivity.deleteDatabase(0);
                                            } catch (Exception e) {

                                            }
                                            break;
                                        case "1":
                                            prof = (LinearLayout) findViewById(R.id.prof2);
                                            prof.setVisibility(View.GONE);
                                            profText = (TextView) findViewById(R.id.prof2Text);
                                            profText.setText("");
                                            editor.putString("prof2Text", " ");
                                            try {
                                                ProfileMainActivity.deleteDatabase(1);
                                            } catch (Exception e) {

                                            }
                                            break;
                                        case "2":
                                            prof = (LinearLayout) findViewById(R.id.prof3);
                                            prof.setVisibility(View.GONE);
                                            profText = (TextView) findViewById(R.id.prof3Text);
                                            profText.setText("");
                                            editor.putString("prof3Text", " ");
                                            try {
                                                ProfileMainActivity.deleteDatabase(2);
                                            } catch (Exception e) {

                                            }
                                            break;
                                        case "3":
                                            prof = (LinearLayout) findViewById(R.id.prof4);
                                            prof.setVisibility(View.GONE);
                                            profText = (TextView) findViewById(R.id.prof4Text);
                                            profText.setText("");
                                            editor.putString("prof4Text", " ");
                                            try {
                                                ProfileMainActivity.deleteDatabase(3);
                                            } catch (Exception e) {

                                            }
                                            break;

                                    }

                                    editor.apply();

                                    Log.i("6705 count4", "" + count);


                                }
                            })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                        }
                    }

                });

                renameDelete.show();
                return true;

            }
        }