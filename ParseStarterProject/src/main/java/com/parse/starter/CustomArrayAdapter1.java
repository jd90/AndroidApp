package com.parse.starter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;

        import java.util.List;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustomArrayAdapter1 extends ArrayAdapter<Goal> implements View.OnClickListener {

    private final Context context;
    private final GoalStore1 fGoalStore;

    public CustomArrayAdapter1(Context context, GoalStore1 g) {
        super(context, R.layout.goal_list_item, (List<Goal>) g.list);

        this.context = context;
        this.fGoalStore = g;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.feed_fragment, parent, false);


        return row_view;
    }

    @Override
    public void onClick(View v) {

        //this blue doesnt stick because it isnt modelled within the datastore. the goal object must hold which days have been put down so it can reset it at creation/refresh
        //v.setBackgroundColor(Color.BLUE);

        //  notifyDataSetChanged();

    }
}
