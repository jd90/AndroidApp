package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Fragment3 extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    View view;
    LinearLayout sharkFin;

    public Fragment3() {
        // Required empty public constructor
    }
    public static Fragment3 newInstance(){
        return new Fragment3();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.statistics_fragment, container, false);

        try {

            ArrayList<Integer> g = ProfileMainActivity.goalStore.pastTotals;
            for (int i = 0; i < 4; i++) {

                int height = (int) (g.get(i) * 3);
                Log.i("heightbargraph", "" + g.get(i) * 2);
                LinearLayout l;
                switch (i) {
                    case 0:
                        l = (LinearLayout) view.findViewById(R.id.bar0);
                        l.setOnLongClickListener(this);
                        l.setTag(0);
                        l.setMinimumHeight(height);
                        break;
                    case 1:
                        l = (LinearLayout) view.findViewById(R.id.bar1);
                        l.setOnLongClickListener(this);
                        l.setTag(1);
                        l.setMinimumHeight(height);
                        break;
                    case 2:
                        l = (LinearLayout) view.findViewById(R.id.bar2);
                        l.setOnLongClickListener(this);
                        l.setTag(2);
                        l.setMinimumHeight(height);
                        break;
                    case 3:
                        l = (LinearLayout) view.findViewById(R.id.bar3);
                        l.setOnLongClickListener(this);
                        l.setTag(3);
                        l.setMinimumHeight(height);
                        break;
                }

            }

            for (int i = 4; i < 8; i++) {

                int height = (int) (g.get(i) * 3);
                Log.i("heightbargraph", "" + g.get(i) * 2);
                LinearLayout l;
                switch (i) {
                    case 4:
                        l = (LinearLayout) view.findViewById(R.id.bar4);
                        l.setOnLongClickListener(this);
                        l.setTag(4);
                        l.setMinimumHeight(height);
                        break;
                    case 5:
                        l = (LinearLayout) view.findViewById(R.id.bar5);
                        l.setOnLongClickListener(this);
                        l.setTag(5);
                        l.setMinimumHeight(height);
                        break;
                    case 6:
                        l = (LinearLayout) view.findViewById(R.id.bar6);
                        l.setOnLongClickListener(this);
                        l.setTag(6);
                        l.setMinimumHeight(height);
                        break;
                    case 7:
                        l = (LinearLayout) view.findViewById(R.id.bar7);
                        l.setOnLongClickListener(this);
                        l.setTag(7);
                        l.setMinimumHeight(height);
                        break;
                }
            }
            for (int i = 8; i < 16; i++) {

                int height = (int) (g.get(i) * 3);
                LinearLayout l;
                Log.i("heightbargraph", "" + g.get(i) * 2);
                switch (i) {
                    case 8:
                        l = (LinearLayout) view.findViewById(R.id.bar8);
                        l.setOnLongClickListener(this);
                        l.setTag(8);
                        l.setMinimumHeight(height);
                        break;
                    case 9:
                        l = (LinearLayout) view.findViewById(R.id.bar9);
                        l.setOnLongClickListener(this);
                        l.setTag(9);
                        l.setMinimumHeight(height);
                        break;
                    case 10:
                        l = (LinearLayout) view.findViewById(R.id.bar10);
                        l.setOnLongClickListener(this);
                        l.setTag(10);
                        l.setMinimumHeight(height);
                        break;
                    case 11:
                        l = (LinearLayout) view.findViewById(R.id.bar11);
                        l.setOnLongClickListener(this);
                        l.setTag(11);
                        l.setMinimumHeight(height);
                        break;
                    case 12:
                        l = (LinearLayout) view.findViewById(R.id.bar12);
                        l.setOnLongClickListener(this);
                        l.setTag(12);
                        l.setMinimumHeight(height);
                        break;
                    case 13:
                        l = (LinearLayout) view.findViewById(R.id.bar13);
                        l.setOnLongClickListener(this);
                        l.setTag(13);
                        l.setMinimumHeight(height);
                        break;
                    case 14:
                        l = (LinearLayout) view.findViewById(R.id.bar14);
                        l.setOnLongClickListener(this);
                        l.setTag(14);
                        l.setMinimumHeight(height);
                        break;
                    case 15:
                        l = (LinearLayout) view.findViewById(R.id.bar15);
                        l.setOnLongClickListener(this);
                        l.setTag(15);
                        l.setMinimumHeight(height);
                        break;
                }

            }
        }catch(Exception e){Log.i("67056705", "past totals frag3 error");}

        TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
        bgTitle.setOnClickListener(this);
        sharkFin = (LinearLayout) view.findViewById(R.id.sharkFin);
        sharkFin.setMinimumHeight(60);
        sharkFin.setTag("sharkSwim");
        sharkFin.setOnClickListener(this);

        LinearLayout graphContainer = (LinearLayout) view.findViewById(R.id.bargraphcontainer);
        graphContainer.setOnClickListener(this);



        return view;

    }
    float per;
    @Override
    public void onClick(View v) {



if(v.getTag() == "sharkSwim") {
    //must set translationX()to the value of goalstore2.getTotalPercentages() * 5
    //also set the totalPercent text to this number


    ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
    sharkFinPic.setTranslationX(0);
    TextView totalPercentAve = (TextView) view.findViewById(R.id.totalPercentAve);
    totalPercentAve.setText("");

    TextView weektotalAveTitle = (TextView) view.findViewById(R.id.weektotalAveTitle);
    weektotalAveTitle.setText("");

    TextView totalPercent = (TextView) view.findViewById(R.id.totalPercent);
    totalPercent.setText("");

    TextView weektotalTitle = (TextView) view.findViewById(R.id.weektotalTitle);
    weektotalTitle.setText("");

    double percent = ProfileMainActivity.goalStore.getTotalPercentage();
    if(percent>100){
        percent = 100;
    }
    double x = percent * 4.73;
    per = Float.parseFloat("" + x);
    // sharkFinPic.setImageResource(R.drawable.shark_fin_pic_moving);
    sharkFinPic.animate().translationX(per).setDuration(600);


    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            TextView totalPercent = (TextView) view.findViewById(R.id.totalPercent);
            TextView weektotalTitle = (TextView) view.findViewById(R.id.weektotalTitle);
            //totalPercent.setText(per+"%");
            int total = (int) ProfileMainActivity.goalStore.getTotalPercentage();
            if(total>100){
                total = 100;
            }
            totalPercent.setText(total+"%");
            weektotalTitle.setText("Week Total:");
            TextView totalPercentAve = (TextView) view.findViewById(R.id.totalPercentAve);
            int aveTotal=0;

            Log.i("HEREYEGOARRAY", " "+ProfileMainActivity.goalStore.pastTotals.toString());
            Log.i("HEREYEGOARRAY", " "+ProfileMainActivity.goalStore.pastTotals.size());


            for(int i=12; i<16; i++ ){

                aveTotal += ProfileMainActivity.goalStore.pastTotals.get(i);
            }

            totalPercentAve.setText(aveTotal / 4 + "%");

            TextView weektotalAveTitle = (TextView) view.findViewById(R.id.weektotalAveTitle);
            weektotalAveTitle.setText("4 Week Average");
            //starts after 600
            //ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
            //sharkFinPic.setImageResource(R.drawable.shark_fin_pic);
        }
    }, 600);

}
        else {//if( v instanceof LinearLayout || v instanceof TextView){

            LinearLayout l = (LinearLayout) view.findViewById(R.id.rect2);
    LinearLayout ll = (LinearLayout) view.findViewById(R.id.rect3);
    if (l.getVisibility() == View.GONE && ll.getVisibility() == View.GONE){
                l.setVisibility(View.VISIBLE);
                TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
                bgTitle.setText("8 Week View of Performance Totals");

            }else {if(l.getVisibility() == View.VISIBLE && ll.getVisibility() == View.GONE){
        LinearLayout bgcontainer = (LinearLayout) view.findViewById(R.id.bargraphcontainer);
        bgcontainer.setWeightSum(8);
        ll.setVisibility(View.VISIBLE);
        TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
        bgTitle.setText("16 Week View of Performance Totals");
    }else {
        LinearLayout bgcontainer = (LinearLayout) view.findViewById(R.id.bargraphcontainer);
        bgcontainer.setWeightSum(6);
        l.setVisibility(View.GONE);
        ll.setVisibility(View.GONE);
        TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
        bgTitle.setText("4 Week View of Performance Totals");
    }
            }



        }
    }


    @Override
    public boolean onLongClick(View v) {

        LinearLayout l = (LinearLayout)v;
        int x = Integer.parseInt(l.getTag().toString());
        int y = ProfileMainActivity.goalStore.pastTotals.get(x);
        Toast t = Toast.makeText(getActivity(), y + "%", Toast.LENGTH_SHORT);
        t.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Past Week");
        builder.setMessage("" + y + "%");

        //this will inflate a whole dialog for me
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.goal_list_item, null));
        //i should put this into a wee scrollpane so that it only displays two or three goals and you scroll through them?
        //keeping the pop up quite small?

        /**

        holder.goalTitleView.setText(g.name);
        holder.numberOutOfView.setText(g.getTargets());
        holder.percentView.setText(g.getPercentage());
        holder.percentageBar.setProgress((int) g.percent);
        holder.b0.setOnClickListener(this);
        holder.b0.setTag(0);
        if(g.buttons[0]) {
            holder.b0.setImageResource(R.drawable.m1);}
        else{holder.b0.setImageResource(R.drawable.m2);}
        holder.b1.setOnClickListener(this);
        holder.b1.setTag(1);
        if(g.buttons[1]) {
            holder.b1.setImageResource(R.drawable.t1);}
        else{holder.b1.setImageResource(R.drawable.t2);}
        holder.b2.setOnClickListener(this);
        holder.b2.setTag(2);
        if(g.buttons[2]) {
            holder.b2.setImageResource(R.drawable.w1);}
        else{holder.b2.setImageResource(R.drawable.w2);}
        holder.b3.setOnClickListener(this);
        holder.b3.setTag(3);
        if(g.buttons[3]) {
            holder.b3.setImageResource(R.drawable.t1);}
        else{holder.b3.setImageResource(R.drawable.t2);}
        holder.b4.setOnClickListener(this);
        holder.b4.setTag(4);
        if(g.buttons[4]) {
            holder.b4.setImageResource(R.drawable.f1);}
        else{holder.b4.setImageResource(R.drawable.f2);}
        holder.b5.setOnClickListener(this);
        holder.b5.setTag(5);
        if(g.buttons[5]) {
            holder.b5.setImageResource(R.drawable.s1);}
        else{holder.b5.setImageResource(R.drawable.s2);}
        holder.b6.setOnClickListener(this);
        holder.b6.setTag(6);
        if(g.buttons[6]) {
            holder.b6.setImageResource(R.drawable.s1);}
        else{holder.b6.setImageResource(R.drawable.s2);}

         */

        builder.show();


        return false;
    }
}
