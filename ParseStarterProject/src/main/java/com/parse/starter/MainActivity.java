package com.parse.starter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.profiles_page);

        prefs = this.getPreferences(Context.MODE_PRIVATE);
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
        if(profString.contains("1")){
            prof1.setVisibility(View.VISIBLE);
            prof1Text.setText(prefs.getString("prof1Text", " "));
        }
        if(profString.contains("2")){
            prof2.setVisibility(View.VISIBLE);
            prof2Text.setText(prefs.getString("prof2Text", " "));
        }

        if(profString.contains("3")){
            prof3.setVisibility(View.VISIBLE);
            prof3Text.setText(prefs.getString("prof3Text", " "));
        }

        if(profString.contains("4")){
            prof4.setVisibility(View.VISIBLE);
            prof4Text.setText(prefs.getString("prof4Text", " "));
        }

        prof1.setTag("1");
        prof2.setTag("2");
        prof3.setTag("3");
        prof4.setTag("4");


        Button newGoal = (Button) findViewById(R.id.newGoalButton);
        newGoal.setText("Add New Profile");
        newGoal.setTag("new");
        newGoal.setOnClickListener(this);

    }


    public void onClick(View view) {

        if (view instanceof Button) {
            Button button = (Button) view;

            if (button.getTag() == "new") {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Name your Profile");

                final EditText profileInput = new EditText(this);


                builder.setView(profileInput);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {
                        //m_Text = input.getText().toString();

                        int count = prefs.getInt("count", 0);
                        String profileString = prefs.getString("profiles", " ");

                          //      if (count == 0) {
                        //            count++;
                      //              //profileString = "1";
                      //         } else {
                        if(count >3){
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
                        }else{
                            //   if (count > 0) {


                            if (!profileString.contains("1")) {
                                profileString = profileString + "1";
                                count++;
                                LinearLayout prof = (LinearLayout) findViewById(R.id.prof1);
                                prof.setVisibility(View.VISIBLE);
                                TextView profText = (TextView) findViewById(R.id.prof1Text);
                                profText.setText(String.valueOf(profileInput.getText()));
                                editor.putString("prof1Text", String.valueOf(profileInput.getText()));
                            } else {
                                if (!profileString.contains("2")) {
                                    profileString = profileString + "2";
                                    count++;
                                    LinearLayout prof = (LinearLayout) findViewById(R.id.prof2);
                                    prof.setVisibility(View.VISIBLE);
                                    TextView profText = (TextView) findViewById(R.id.prof2Text);
                                    profText.setText(String.valueOf(profileInput.getText()));
                                    editor.putString("prof2Text", String.valueOf(profileInput.getText()));
                                } else {
                                    if (!profileString.contains("3")) {
                                        profileString = profileString + "3";
                                        count++;
                                        LinearLayout prof = (LinearLayout) findViewById(R.id.prof3);
                                        prof.setVisibility(View.VISIBLE);
                                        TextView profText = (TextView) findViewById(R.id.prof3Text);
                                        profText.setText(String.valueOf(profileInput.getText()));
                                        editor.putString("prof3Text", String.valueOf(profileInput.getText()));
                                    } else {
                                        if (!profileString.contains("4")) {
                                            profileString = profileString + "4";
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
                        }
                        //   }
                        //   }

                        editor.putString("profiles", profileString);
                        editor.putInt("count", count);
                        editor.apply();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int num) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        }


        if(view instanceof LinearLayout){

            LinearLayout profile = (LinearLayout) view;
            int profileNum = Integer.parseInt(String.valueOf(profile.getTag()));

            intent.putExtra("profile", profileNum);
            startActivity(intent);



        }





    }

    @Override
    public boolean onLongClick(View v) {

        LinearLayout prof = (LinearLayout)v;
        String profNum = String.valueOf(prof.getTag());
        String profileString = prefs.getString("profiles", " ");

        Log.i("profileString before ", profileString);

        profileString = profileString.replace(profNum, "");

        Log.i("profileString after ", profileString);

        editor.putString("profiles", profileString);
        int count =prefs.getInt("count", 0);
        count--;
        editor.putInt("count", count);
        editor.apply();


        switch(profNum){

            case "1":
                prof = (LinearLayout) findViewById(R.id.prof1);
                prof.setVisibility(View.GONE);
                TextView profText = (TextView) findViewById(R.id.prof1Text);
                profText.setText("");
                editor.putString("prof1Text", " ");
                try {
                    ProfileMainActivity.deleteDatabase(1);
                }catch(Exception e){

                }
                break;
            case "2":
                prof = (LinearLayout) findViewById(R.id.prof2);
                prof.setVisibility(View.GONE);
                profText = (TextView) findViewById(R.id.prof2Text);
                profText.setText("");
                editor.putString("prof2Text", " ");
                try {
                    ProfileMainActivity.deleteDatabase(2);
                }catch(Exception e){

                }
                break;
            case "3":
                prof = (LinearLayout) findViewById(R.id.prof3);
                prof.setVisibility(View.GONE);
                profText = (TextView) findViewById(R.id.prof3Text);
                profText.setText("");
                editor.putString("prof3Text", " ");
                try {
                    ProfileMainActivity.deleteDatabase(3);
                }catch(Exception e){

                }
                break;
            case "4":
                prof = (LinearLayout) findViewById(R.id.prof4);
                prof.setVisibility(View.GONE);
                profText = (TextView) findViewById(R.id.prof4Text);
                profText.setText("");
                editor.putString("prof4Text", " ");
                try {
                    ProfileMainActivity.deleteDatabase(4);
                }catch(Exception e){

                }
                break;

        }

        editor.apply();


        return true;
    }
}