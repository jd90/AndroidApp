package com.parse.starter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Borris on 05/04/2016.
 */

public class FollowersArrayAdapter extends ArrayAdapter<ClassGoal> implements View.OnClickListener {

    private final Context context;
    private ArrayList<String> usernames;
    CheckBox c;TextView t;
    public FollowersArrayAdapter(Context context, ArrayList<String> g) {
        super(context, R.layout.friend_list_item, (List) g);

        this.context = context;
        this.usernames = g;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.friend_list_item, parent, false);

        t = (TextView) row_view.findViewById(R.id.title);
        c = (CheckBox) row_view.findViewById(R.id.check);
        LinearLayout l = (LinearLayout) row_view.findViewById(R.id.container);

       String user = usernames.get(position);
            Log.i("78789", "" + user);

        t.setText(user);

            List<Object> a = ParseUser.getCurrentUser().getList("followers");
            Log.i("78789", "" + a.toString());
            if (a.contains(user)) {
                Log.i("78789", "match" + user);
                c.setChecked(true);
            }

        c.setOnClickListener(this);
        l.setTag(position);

        return row_view;
    }

    @Override
    public void onClick(View v) {


        Log.i("7878user", "hi");

       LinearLayout l = (LinearLayout) v.getParent();
        int position = Integer.parseInt(l.getTag().toString());

       CheckBox check = (CheckBox) v;
        if(!check.isChecked()) {//this shouldnt be !not - why is it working like this??
            check.setChecked(false);
            Log.i("7878user", usernames.get(position));
            //ParseUser.getCurrentUser().getList("followers").remove(usernames.get(position));
            List<Object> hi = ParseUser.getCurrentUser().getList("followers");
            hi.remove(usernames.get(position));
            ParseUser.getCurrentUser().put("followers", hi);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e!=null){Log.i("787878", "remove "+e.toString());}
                    else{Log.i("787878", "removed ");
              //          notifyDataSetChanged();
                    }

                }
            });
        }else{
            check.setChecked(true);
            Log.i("7878user", usernames.get(position));
            //ParseUser.getCurrentUser().getList("followers").add(usernames.get(position));
            List<Object> hi = ParseUser.getCurrentUser().getList("followers");
            hi.add(usernames.get(position));
            ParseUser.getCurrentUser().put("followers", hi);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.i("787878", "add " + e.toString());
                    } else {
                        Log.i("787878", "added ");
                   //    notifyDataSetChanged();
                    }

                }
            });
        }


    }
}
