package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

        ArrayList<Integer> g = ProfileMainActivity.goalStore.pastTotals;
            for(int i=0; i<4; i++){

                int height = (int) (g.get(i)*2.5);
                Log.i("heightbargraph", ""+g.get(i) * 2);
                LinearLayout l;
                switch(i){
                    case 0:
                        l =(LinearLayout) view.findViewById(R.id.bar1);
                        l.setOnLongClickListener(this);
                        l.setTag(0);
                        l.setMinimumHeight(height);
                        break;
                    case 1:
                        l =(LinearLayout) view.findViewById(R.id.bar2);
                        l.setOnLongClickListener(this);
                        l.setTag(1);
                        l.setMinimumHeight(height);
                        break;
                    case 2:
                        l =(LinearLayout) view.findViewById(R.id.bar3);
                        l.setOnLongClickListener(this);
                        l.setTag(2);
                        l.setMinimumHeight(height);
                        break;
                    case 3:
                        l =(LinearLayout) view.findViewById(R.id.bar4);
                        l.setOnLongClickListener(this);
                        l.setTag(3);
                        l.setMinimumHeight(height);
                        break;
                }

            }

        for(int i=4; i<8; i++){

            int height = (int) (g.get(i)*2.5);
            Log.i("heightbargraph", ""+g.get(i) * 2);
            LinearLayout l;
            switch(i){
                case 4:
                    l =(LinearLayout) view.findViewById(R.id.bar5);
                    l.setOnLongClickListener(this);
                    l.setTag(4);
                    l.setMinimumHeight(height);
                    break;
                case 5:
                    l =(LinearLayout) view.findViewById(R.id.bar6);
                    l.setOnLongClickListener(this);
                    l.setTag(5);
                    l.setMinimumHeight(height);
                    break;
                case 6:
                    l =(LinearLayout) view.findViewById(R.id.bar7);
                    l.setOnLongClickListener(this);
                    l.setTag(6);
                    l.setMinimumHeight(height);
                    break;
                case 7:
                    l =(LinearLayout) view.findViewById(R.id.bar8);
                    l.setOnLongClickListener(this);
                    l.setTag(7);
                    l.setMinimumHeight(height);
                    break;
            }

        }

        TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
        bgTitle.setOnClickListener(this);
        sharkFin = (LinearLayout) view.findViewById(R.id.sharkFin);
        sharkFin.setMinimumHeight(60);

        sharkFin.setOnClickListener(this);

        LinearLayout graphContainer = (LinearLayout) view.findViewById(R.id.bargraphcontainer);
        graphContainer.setOnClickListener(this);



        return view;

    }
    float per;
    @Override
    public void onClick(View v) {



        if( v instanceof LinearLayout || v instanceof TextView){

            LinearLayout l = (LinearLayout) view.findViewById(R.id.rect);
            if(l.getVisibility() == View.GONE){
                l.setVisibility(View.VISIBLE);
                TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
                bgTitle.setText("8 Week View of Performance Totals");

            }else {
                l.setVisibility(View.GONE);
                TextView bgTitle = (TextView) view.findViewById(R.id.bargraphtitle);
                bgTitle.setText("4 Week View of Performance Totals");
            }
        }

        else {


            //must set translationX()to the value of goalstore2.getTotalPercentages() * 5
            //also set the totalPercent text to this number

            double percent = ProfileMainActivity.goalStore.getTotalPercentage();

            ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
            double x = percent * 5;
            per = Float.parseFloat("" + x);
            // sharkFinPic.setImageResource(R.drawable.shark_fin_pic_moving);
            sharkFinPic.animate().translationX(per).setDuration(600);


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView totalPercent = (TextView) view.findViewById(R.id.totalPercent);
                    //totalPercent.setText(per+"%");
                    totalPercent.setText("" + (int) ProfileMainActivity.goalStore.getTotalPercentage() + "%");
                    //starts after 600
                    //ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
                    //sharkFinPic.setImageResource(R.drawable.shark_fin_pic);
                }
            }, 600);

        }
    }


    @Override
    public boolean onLongClick(View v) {

        LinearLayout l = (LinearLayout)v;
        int x = Integer.parseInt(l.getTag().toString());
        Toast t = Toast.makeText(getActivity(), "YOU HIT " + x, Toast.LENGTH_SHORT);
        t.show();

        return false;
    }
}
