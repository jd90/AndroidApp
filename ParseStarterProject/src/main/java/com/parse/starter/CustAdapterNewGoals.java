package com.parse.starter;

/**
 * Created by Borris on 09/02/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CustAdapterNewGoals extends ArrayAdapter<ClassGoal> implements View.OnClickListener {

    private final Context context;
    private GoalStore2 fGoalStore;
    FragmentManager fm;
    int count = 1;
    String profileName;

    public CustAdapterNewGoals(Context context, GoalStore2 g, String profileName) {
        super(context, R.layout.future_item_layout, (List<ClassGoal>) g.list);

        this.context = context;
        this.fGoalStore = g;
        this.profileName=profileName;
        fm = ((Activity) context).getFragmentManager();

        Log.i("44331 test", "" + ActGoals.profile);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //these two lines help to get the fragmentManager for using a dialogFragment

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.new_goal_list_item, parent, false);


        LinearLayout parentLayout = (LinearLayout) row_view.findViewById(R.id.containerLayout);
        parentLayout.setTag(position);

        Button delete = (Button) row_view.findViewById(R.id.close);
        delete.setOnClickListener(this);
        delete.setTag("delete");

        TextView titleInput = (TextView) row_view.findViewById(R.id.titleInput);
        titleInput.setHint(fGoalStore.getAt(position).getName());

        TextView freq = (TextView) row_view.findViewById(R.id.freq);
        freq.setText(String.valueOf(fGoalStore.getAt(position).getTotal())+" times a week");

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

                        DialogFragAddGoal dialogFragment = new DialogFragAddGoal();
                        dialogFragment.show(fm, "dialog");

                        //make an edit future goal button to go beside delete button

                    }
                    break;
                case "delete":
                    LinearLayout l = (LinearLayout) v.getParent().getParent().getParent();

                    //fGoalStore.list.remove(Integer.parseInt(l.getTag().toString()));
                    Log.i("45454545",""+Integer.parseInt(l.getTag().toString()));
                    fGoalStore.removeGoal(Integer.parseInt(l.getTag().toString()));
                    fGoalStore.saveToDatabase();
                    count--;
                    notifyDataSetChanged();

                    break;
            }
        }

    }

}
