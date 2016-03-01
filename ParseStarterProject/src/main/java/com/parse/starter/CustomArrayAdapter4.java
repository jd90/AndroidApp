package com.parse.starter;

/**
 * Created by Borris on 09/02/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;


public class CustomArrayAdapter4 extends ArrayAdapter<Goal> implements View.OnClickListener {

    private final Context context;
    private GoalStore2 fGoalStore;
    FragmentManager fm;
    int count = 1;

    public CustomArrayAdapter4(Context context, GoalStore2 g) {
        super(context, R.layout.future_item_layout, (List<Goal>) g.list);

        this.context = context;
        this.fGoalStore = g;

        fm = ((Activity) context).getFragmentManager();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //these two lines help to get the fragmentManager for using a dialogFragment



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.new_goal_list_item, parent, false);


        RelativeLayout parentLayout = (RelativeLayout) row_view.findViewById(R.id.containerLayout);
        parentLayout.setTag(position);

        Button delete = (Button) row_view.findViewById(R.id.close);
        delete.setOnClickListener(this);
        delete.setTag("delete");

        TextView titleInput = (TextView) row_view.findViewById(R.id.titleInput);
        titleInput.setHint(fGoalStore.getAt(position).name);

        TextView freq = (TextView) row_view.findViewById(R.id.freq);
        freq.setText(String.valueOf(fGoalStore.getAt(position).total)+" times a week");

        return row_view;
    }

    @Override
    public void onClick(View v) {

        //this blue doesnt stick because it isnt modelled within the datastore. the goal object must hold which days have been put down so it can reset it at creation/refresh
        //v.setBackgroundColor(Color.BLUE);

        if (v instanceof Button) {

            switch (v.getTag().toString()) {

                case "new":
                    if (fGoalStore.getSize() > 6) {
                        Toast t = Toast.makeText(getContext(), "Maximum 7 Goals Already Created", Toast.LENGTH_SHORT);
                        t.show();
                    } else {

                        NewGoalFragment dialogFragment = new NewGoalFragment();
                        dialogFragment.show(fm, "dialog");

                        //make an edit future goal button to go beside delete button


                    }
                    break;
                case "delete":
                    RelativeLayout l = (RelativeLayout) v.getParent();
                    fGoalStore.list.remove(Integer.parseInt(l.getTag().toString()));
                    fGoalStore.saveToDatabase();
                    count--;
                    notifyDataSetChanged();

                    break;
            }
        }

    }
















}
