package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment3 extends Fragment implements View.OnClickListener{

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



        LinearLayout barChart = (LinearLayout) view.findViewById(R.id.rect);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ff616161"));
        //width and height and then storage quality definition = Bitmap.Config
        Bitmap bg = Bitmap.createBitmap(810,510, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        canvas.drawColor(Color.parseColor("#ffffffff"));


//        int x = 5;
  //      int y = 95;  //also reset bitmap to 800x500 and height to 500-pasttotal, and height bottom to 500
        int x=10;
        int y=100;


        ArrayList<Integer> g = MainActivity.goalStore.pastTotals;
            for(int i=g.size()-1; i>=0; i--){ // this is backwards so that it is

                //max height = 500
                int height = 505-(g.get(i)*5);
                canvas.drawRect(x, height, y, 505, paint);
                x+=100;
                y+=100;
            }

        barChart.setBackgroundDrawable(new BitmapDrawable(bg));


        sharkFin = (LinearLayout) view.findViewById(R.id.sharkFin);
        sharkFin.setMinimumHeight(60);

        sharkFin.setOnClickListener(this);




        return view;

    }
    float per;
    @Override
    public void onClick(View v) {

        //must set translationX()to the value of goalstore2.getTotalPercentages() * 5
        //also set the totalPercent text to this number

        double percent = MainActivity.goalStore.getTotalPercentage();

        ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
        double x= percent*5;
        per = Float.parseFloat(""+x);
       // sharkFinPic.setImageResource(R.drawable.shark_fin_pic_moving);
        sharkFinPic.animate().translationX(per).setDuration(600);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView totalPercent = (TextView) view.findViewById(R.id.totalPercent);
                //totalPercent.setText(per+"%");
                totalPercent.setText("" +(int) MainActivity.goalStore.getTotalPercentage()+"%");
                //starts after 600
                //ImageView sharkFinPic = (ImageView) view.findViewById(R.id.sharkFinPic);
                //sharkFinPic.setImageResource(R.drawable.shark_fin_pic);
            }
        }, 600);



    }
}
