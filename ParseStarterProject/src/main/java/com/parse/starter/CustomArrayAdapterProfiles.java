package com.parse.starter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustomArrayAdapterProfiles extends ArrayAdapter<Goal> implements View.OnClickListener, View.OnLongClickListener {

    private final Context context;
    ProfileDatastore profileDatastore;
    ArrayList<Profile> profiles;

    static LinearLayout profileContainer;

    //reuse one instance of confirm builder alert dialog - in all classes, that is

    public CustomArrayAdapterProfiles (Context context, ArrayList profiles) {
        super(context, R.layout.goal_list_item, profiles);

        this.profiles = profiles;
        this.profileDatastore=MainActivity.profileDatastore;

        this.context = context;
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

        Intent hi = new Intent(getContext(), ProfileMainActivity.class);
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
                    final EditText profileInput = new EditText(getContext());
                    builder.setView(profileInput);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            //change title of profile here
                            Profile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                            String title = profileInput.getText().toString().toUpperCase();
                            profile.renameProfile(title);
                            notifyDataSetChanged();
                            MainActivity.saveProfiles();
                            }});
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int num) {
                            dialog.cancel();
                        }});
                    builder.setCancelable(false);
                    builder.show();
                } else {
                    AlertDialog.Builder confirm = new AlertDialog.Builder(getContext());
                    confirm.setTitle("Delete Profile?");
                    confirm.setMessage("Are you sure you want to delete this profile?");
                    confirm.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Profile profile = profiles.get(Integer.parseInt(vi.getTag().toString()));
                            MainActivity.profileDatastore.removeProfile(profile);
                            notifyDataSetChanged();
                            MainActivity.saveProfiles();
                            MainActivity.saveCount();
                        }})
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }})
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }}});

        renameDelete.show();
        return true;


    }
}
