package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Borris on 06/03/2016.
 */
public class CustomArrayAdapter5 extends ArrayAdapter<Goal> implements View.OnClickListener {
    private final Context context;
    //holds a reference to mainAct goalStore. and when updated here, it updates there. i think.
    //this is because it has been made static - so accessible from whole app (previously i implemented a callback interface to frag2, then accessed its getActivity to edit in mainAct. bad.
    private  GoalStore1 goalStore;

    public CustomArrayAdapter5(Context context, GoalStore1 g) {
        super(context, R.layout.reorder_list_item, (List<Goal>) g.list);

        this.context = context;
        this.goalStore = g;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reorder_list_item, parent, false);
            holder = new ViewHolder();
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.buttonsLayout);
            holder.goalTitleView = (TextView) convertView.findViewById(R.id.goalTitle);
            holder.numberOutOfView = (TextView) convertView.findViewById(R.id.goalTargets);
            holder.percentView = (TextView) convertView.findViewById(R.id.goalPercent);

            holder.upCont = (LinearLayout) convertView.findViewById(R.id.upcont);
            holder.downCont = (LinearLayout) convertView.findViewById(R.id.downcont);

            Button up = (Button) convertView.findViewById(R.id.up);
            Button down = (Button) convertView.findViewById(R.id.down);
            up.setOnClickListener(this);
            up.setTag("up");down.setTag("down");
            down.setOnClickListener(this);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position==0){holder.upCont.setVisibility(View.GONE);}
        if(position==goalStore.getSize()-1){holder.downCont.setVisibility(View.GONE);}
        holder.linearLayout.setTag(position);
        holder.upCont.setTag(position);
        holder.downCont.setTag(position);
        Goal g = goalStore.getAt(position);
        holder.goalTitleView.setText(g.name);
        holder.numberOutOfView.setText(g.getTargets());
        holder.percentView.setText(g.getPercentage());


        return convertView;
    }

    @Override
    public void onClick(View v) {


        if(v instanceof Button) {

            LinearLayout l = (LinearLayout) v.getParent();

            Log.i("6705reorder", "goalstoreAt: "+Integer.parseInt(l.getTag().toString()));
            Goal g = goalStore.getAt(Integer.parseInt(l.getTag().toString()));

                Button pressed = (Button)v;
            Log.i("6705reodrrrr",String.valueOf(pressed.getTag()) );
                    if(String.valueOf(pressed.getTag()).equals("up")){
                        Log.i("6705reorder", "insideup");
                        goalStore.reorderUp(Integer.parseInt(l.getTag().toString()));
                    }else{
                        Log.i("6705reorder", "insidedo");
                        goalStore.reorderDown(Integer.parseInt(l.getTag().toString()));
                    }

                    ProfileMainActivity.goalStore.saveToDatabase();

                    //ProfileMainActivity.updateGoalStore(Integer.parseInt(l.getTag().toString()));
                    notifyDataSetChanged();





        }

    }

    static class ViewHolder {
        // this enables reuse. recyler view it is called? means that it only has to do a findviewbyid call once then reuse it, saving valuable processing time.
        //this means that things like scrolling etc are smoother
        private LinearLayout linearLayout;
        private LinearLayout upCont;
        private LinearLayout downCont;

        private TextView goalTitleView;
        private TextView numberOutOfView;
        private TextView percentView;
    }

}
