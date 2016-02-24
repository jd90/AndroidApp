package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Fragment3 extends Fragment {


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

        View view = inflater.inflate(R.layout.statistics_fragment, container, false);

        LinearLayout barChart = (LinearLayout) view.findViewById(R.id.rect);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#47aff3"));
        //width and height and then storage quality definition = Bitmap.Config


        Bitmap bg = Bitmap.createBitmap(800,500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        //take height from database saved past total percentage scores. * by 5 first so that they fit the 500 height scale?.
        //the bitmap fills the space as it is match_parent in the layout.
        //the numbers in the drawRect method for width are stretched to that.

        canvas.drawRect(5, 500, 95, 0, paint);
        canvas.drawRect(105, 500, 195, 0, paint);
        canvas.drawRect(205, 500, 295, 0, paint);
        canvas.drawRect(305, 500, 395, 0, paint);
        canvas.drawRect(405, 500, 495, 0, paint);
        canvas.drawRect(505, 500, 595, 0, paint);
        canvas.drawRect(605, 500, 695, 0, paint);
        canvas.drawRect(705, 500, 795, 0, paint);



        //coulg use getWidth and then divisions to get dimensions that fill these areas
        //if made new BitmapDrawable(getResources(), bg) - it nearly doubles in height size - why?
        //may need a: if sdk level <16 then setbackgrounddrawable, else if > 16 setBackground...
        barChart.setBackgroundDrawable(new BitmapDrawable(bg));
        //ll.setBackground(new BitmapDrawable(bg));

        return view;

    }

}
