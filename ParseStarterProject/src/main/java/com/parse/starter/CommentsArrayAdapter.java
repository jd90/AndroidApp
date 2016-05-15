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

public class CommentsArrayAdapter extends ArrayAdapter<List> implements View.OnClickListener {

    private final Context context;
    private ArrayList<String> comments;
    CheckBox c;TextView t;
    public CommentsArrayAdapter(Context context, ArrayList<String> g) {
        super(context, R.layout.friend_list_item, (List) g);

        this.context = context;
        this.comments = g;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.friend_list_item, parent, false);

        t = (TextView) row_view.findViewById(R.id.title);
        c = (CheckBox) row_view.findViewById(R.id.check);
        c.setVisibility(View.GONE);
        LinearLayout l = (LinearLayout) row_view.findViewById(R.id.container);

        String msg = comments.get(position);
        Log.i("78789", "" + msg);

        t.setText(msg);



        return row_view;
    }

    @Override
    public void onClick(View v) {




    }
}
