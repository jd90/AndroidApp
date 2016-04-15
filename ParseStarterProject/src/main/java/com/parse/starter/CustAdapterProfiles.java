package com.parse.starter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustAdapterProfiles extends ArrayAdapter<ClassGoal> implements View.OnClickListener, View.OnLongClickListener, TextWatcher {

    private final Context context;
    ProfileDatastore profileDatastore;
    ArrayList<ClassProfile> profiles;
    TextView warningMessage;
    boolean saveClickedBool = false;
    EditText profileInput = new EditText(getContext());

    static LinearLayout profileContainer;

    //reuse one instance of confirm builder alert dialog - in all classes, that is

    public CustAdapterProfiles(Context context, ArrayList profiles) {
        super(context, R.layout.goal_list_item, profiles);

        this.profiles = profiles;
        this.profileDatastore= ActProfiles.profileDatastore;
        this.context = context;
        profileInput.addTextChangedListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.profiles_item, parent, false);

        TextView prof1 = (TextView)row_view.findViewById(R.id.profileText);
        prof1.setText(profiles.get(position).name);
        profileContainer = (LinearLayout) row_view.findViewById(R.id.profileContainer);
        profileContainer.setOnLongClickListener(this);
        profileContainer.setOnClickListener(this);
        profileContainer.setTag(position);

        return row_view;
    }

    @Override
    public void onClick(View v) {

        int databaseNum = profiles.get(Integer.parseInt(v.getTag().toString())).databaseNum;

        Intent hi = new Intent(getContext(), ActGoals.class);
        hi.putExtra("profileNumber", Integer.parseInt(v.getTag().toString()));
        hi.putExtra("profile", databaseNum);
        Log.i("67056705", "databaseNum "+databaseNum);
        getContext().startActivity(hi);

    }


    @Override
    public boolean onLongClick(View v) {

        final View vi = v;

        final CharSequence[] options = {"Rename", "Delete"};

        AlertDialog.Builder renameDelete = new AlertDialog.Builder(getContext());
        renameDelete.setCustomTitle(null);
        renameDelete.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Rename your Profile");

                    builder.setView(profileInput);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            //change title of profile here
                            saveClickedBool=true;
                            ClassProfile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                            String title = profileInput.getText().toString().toUpperCase();
                            profile.renameProfile(title);
                            notifyDataSetChanged();
                            ActProfiles.saveProfiles();

                            }});
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            dialog.cancel();
                        }});
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.goal_shark_logo1);
                    builder.show();
                } else {
                    AlertDialog.Builder confirm = new AlertDialog.Builder(getContext());
                    confirm.setTitle("Delete Profile?");
                    confirm.setMessage("Are you sure you want to delete this profile?");
                    confirm.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ClassProfile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                            ActProfiles.profileDatastore.removeProfile(profile);
                            notifyDataSetChanged();
                            ActProfiles.saveProfiles();
                            ActProfiles.saveCount();
                        }});
                            confirm.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }});
                            confirm.setIcon(R.drawable.goal_shark_logo1);
                            confirm.show();
                }}});

        renameDelete.show();
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


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
