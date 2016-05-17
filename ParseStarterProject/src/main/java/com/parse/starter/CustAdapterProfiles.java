package com.parse.starter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustAdapterProfiles extends ArrayAdapter<ClassGoal> implements View.OnClickListener, View.OnLongClickListener, TextWatcher {

    private final Context context;
    ProfileDatastore profileDatastore;
    static ArrayList<ClassProfile> profiles;
    TextView warningMessage;
    boolean saveClickedBool = false;
    EditText profileInput;
    DatabaseHelper databaseHelper;
    AlertDialog.Builder renameDelete = new AlertDialog.Builder(getContext());

    static LinearLayout profileContainer;

    //reuse one instance of confirm builder alert dialog - in all classes, that is

    public CustAdapterProfiles(Context context, ArrayList profiles) {
        super(context, R.layout.goal_list_item, profiles);

        databaseHelper = new DatabaseHelper(getContext());

        this.profiles = profiles;
        this.profileDatastore= new ProfileDatastore();
        profileDatastore.profiles=profiles;
        this.context = context;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Log.i("qqqq2", "size: "+profileDatastore.profiles.size());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.profiles_item, parent, false);

        TextView prof1 = (TextView)row_view.findViewById(R.id.profileText);
        prof1.setText(profiles.get(position).getName());
        profileContainer = (LinearLayout) row_view.findViewById(R.id.profileContainer);
        profileContainer.setOnLongClickListener(this);
        profileContainer.setOnClickListener(this);
        profileContainer.setTag(position);

        return row_view;
    }

    @Override
    public void onClick(View v) {


       // Log.i("ZZZZ", "size click prof: "+ databaseHelper.getAllGoals().size());
      //  Log.i("zzzz", "size click prof profilename: "+databaseHelper.getGoals(profileDatastore.getProfile(Integer.parseInt(v.getTag().toString())).getName()).size());
      //  Log.i("zzzz", "profilename:" + profileDatastore.getProfile(Integer.parseInt(v.getTag().toString())).getName());



        ArrayList<ClassGoal> ggs =databaseHelper.getAllGoals();
//        Log.i("zzzzx", "profName:"+ggs.get(0).profileName);

        profileDatastore.profiles=databaseHelper.getAllProfiles();//this needs to be here - means that the profiles name is accurately passed - opening the right goals after a rename
        Intent hi = new Intent(getContext(), ActGoals.class);
        hi.putExtra("profileName", profileDatastore.getProfile(Integer.parseInt(v.getTag().toString())).getName());
        //hi.putExtra("profile", databaseNum);
        Log.i("44331", "opening profile " + profileDatastore.getProfile(Integer.parseInt(v.getTag().toString())).getName());
        getContext().startActivity(hi);

    }


    @Override
    public boolean onLongClick(View v) {

        final View vi = v;

        final CharSequence[] options = {"Rename", "Delete"};

        profileInput = new EditText(getContext());
        profileInput.addTextChangedListener(this);

        AlertDialog.Builder menu = new AlertDialog.Builder(getContext());
        menu.setCustomTitle(null);
        menu.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    renameDelete.setTitle("Rename your Profile");

                    renameDelete.setView(profileInput);
                    renameDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {

                            //change title of profile here
                            if (profileDatastore.nameTaken(profileInput.getText().toString().toUpperCase()) || profileInput.getText().toString().equals("")) {
                                Log.i("44331", "name taken");
                                Toast t = Toast.makeText(getContext(), "Name already taken!", Toast.LENGTH_SHORT);
                                t.show();
                                dialog.cancel();
                                dialog.dismiss();
                            }//fix this to show a warning message of some sort
                            else {
                                saveClickedBool = true;
                                ClassProfile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                                String oldTitle = profiles.get(Integer.parseInt(vi.getTag().toString())).getName();
                                int refresh = profiles.get(Integer.parseInt(vi.getTag().toString())).getRefreshDay();
                                String newTitle = profileInput.getText().toString().toUpperCase();
                                profile.renameProfile(newTitle);
                                dialog.cancel();
                                dialog.dismiss();
                                notifyDataSetChanged();
                                databaseHelper.renameProfileThroughout(oldTitle, newTitle);

                            }
                        }
                    });
                    renameDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            dialog.cancel();
                        }});
                    renameDelete.setCancelable(false);
                    renameDelete.setIcon(R.drawable.goal_shark_logo1);
                    renameDelete.show();
                } else {

                    renameDelete.setTitle("Delete Profile?");
                    renameDelete.setMessage("Are you sure you want to delete this profile?");
                    renameDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ClassProfile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                            String title = profiles.get(Integer.parseInt(vi.getTag().toString())).getName();
                            Log.i("xxx", "size1: " + ActProfiles.profileDatastore.getSize());
                            //ActProfiles.profileDatastore.removeProfile(Integer.parseInt(vi.getTag().toString()));
                            Log.i("xxx", "size2: " + ActProfiles.profileDatastore.getSize());

                            profiles.remove(profile);

                            notifyDataSetChanged();
                            databaseHelper.deleteProfileRow(title);

                            dialog.cancel();
                            dialog.dismiss();




                        }});
                    renameDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }});
                    renameDelete.setIcon(R.drawable.goal_shark_logo1);
                    renameDelete.show();
                }}});

        menu.show();
        return true;


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