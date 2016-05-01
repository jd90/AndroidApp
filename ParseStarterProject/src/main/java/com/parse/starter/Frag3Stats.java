package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Frag3Stats extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    View view;
    LinearLayout sharkFin;

    ArchiveItemDatastore archiveItemDatastore;

    RelativeLayout relativeLayout;
    TextView goalTitleView;
    TextView numberOutOfView;
    TextView percentView;
    ImageView b0,b1,b2,b3,b4,b5,b6;
    ProgressBar percentageBar;
    int y;
    String z;

    public Frag3Stats() {
        // Required empty public constructor
    }
    public static Frag3Stats newInstance(){
        return new Frag3Stats();
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

            archiveItemDatastore = ActGoals.archiveItemDatastore;

            ArrayList<ClassArchiveItem> g = archiveItemDatastore.list;
            for (int i = 0; i < 4; i++) {

                int height = (int) (g.get(i).percent * 3);
                Log.i("heightbargraph", "" + g.get(i).percent * 2);
                LinearLayout l;
                switch (i) {
                    case 0:
                        l = (LinearLayout) view.findViewById(R.id.bar0);
                        l.setOnLongClickListener(this);
                        l.setTag(0);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 1:
                        l = (LinearLayout) view.findViewById(R.id.bar1);
                        l.setOnLongClickListener(this);
                        l.setTag(1);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 2:
                        l = (LinearLayout) view.findViewById(R.id.bar2);
                        l.setOnLongClickListener(this);
                        l.setTag(2);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 3:
                        l = (LinearLayout) view.findViewById(R.id.bar3);
                        l.setOnLongClickListener(this);
                        l.setTag(3);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                }

            }

            for (int i = 4; i < 8; i++) {

                int height = (int) (g.get(i).percent * 3);
                Log.i("heightbargraph", "" + g.get(i).percent * 2);
                LinearLayout l;
                switch (i) {
                    case 4:
                        l = (LinearLayout) view.findViewById(R.id.bar4);
                        l.setOnLongClickListener(this);
                        l.setTag(4);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 5:
                        l = (LinearLayout) view.findViewById(R.id.bar5);
                        l.setOnLongClickListener(this);
                        l.setTag(5);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 6:
                        l = (LinearLayout) view.findViewById(R.id.bar6);
                        l.setOnLongClickListener(this);
                        l.setTag(6);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 7:
                        l = (LinearLayout) view.findViewById(R.id.bar7);
                        l.setOnLongClickListener(this);
                        l.setTag(7);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                }
            }
            for (int i = 8; i < 16; i++) {

                int height = (int) (g.get(i).percent * 3);
                LinearLayout l;
                height+=5;
                Log.i("heightbargraph", "" + g.get(i).percent * 2);
                switch (i) {
                    case 8:
                        l = (LinearLayout) view.findViewById(R.id.bar8);
                        l.setOnLongClickListener(this);
                        l.setTag(8);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 9:
                        l = (LinearLayout) view.findViewById(R.id.bar9);
                        l.setOnLongClickListener(this);
                        l.setTag(9);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 10:
                        l = (LinearLayout) view.findViewById(R.id.bar10);
                        l.setOnLongClickListener(this);
                        l.setTag(10);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 11:
                        l = (LinearLayout) view.findViewById(R.id.bar11);
                        l.setOnLongClickListener(this);
                        l.setTag(11);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 12:
                        l = (LinearLayout) view.findViewById(R.id.bar12);
                        l.setOnLongClickListener(this);
                        l.setTag(12);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 13:
                        l = (LinearLayout) view.findViewById(R.id.bar13);
                        l.setOnLongClickListener(this);
                        l.setTag(13);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 14:
                        l = (LinearLayout) view.findViewById(R.id.bar14);
                        l.setOnLongClickListener(this);
                        l.setTag(14);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
                        break;
                    case 15:
                        l = (LinearLayout) view.findViewById(R.id.bar15);
                        l.setOnLongClickListener(this);
                        l.setTag(15);
                        l.setMinimumHeight(height);if(height==5){l.setBackgroundColor(Color.RED);}
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

    double percent = ActGoals.goalStore.getTotalPercentage();
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
            int total = (int) ActGoals.goalStore.getTotalPercentage();
            if(total>100){
                total = 100;
            }
            totalPercent.setText(total+"%");
            weektotalTitle.setText("Week Total:");
            TextView totalPercentAve = (TextView) view.findViewById(R.id.totalPercentAve);
            int aveTotal=0;

            //Log.i("HEREYEGOARRAY", " "+ ActGoals.goalStore.pastTotals.toString());
            //Log.i("HEREYEGOARRAY", " "+ ActGoals.goalStore.pastTotals.size());


            for(int i=12; i<16; i++ ){

                aveTotal += archiveItemDatastore.list.get(i).percent;
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


        LinearLayout l = (LinearLayout) v;
        int x = Integer.parseInt(l.getTag().toString());
        y = archiveItemDatastore.list.get(x).percent;
        z = archiveItemDatastore.list.get(x).date;

        Log.i("pastDate", z);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Week of " + z);
        builder.setMessage(y + "%");
        builder.setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int num) {
                ConnectivityManager connect = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connect.getActiveNetworkInfo() != null) {
                    if(ParseUser.getCurrentUser() ==null){Toast t = Toast.makeText(getActivity(), "Error! User must be signed in", Toast.LENGTH_SHORT);
                    t.show();}else {
                        ParseObject feed = new ParseObject("Feed");
                        ParseACL acl = new ParseACL();
                        acl.setPublicWriteAccess(true);
                        acl.setPublicReadAccess(true);
                        feed.setACL(acl);
                        List likes = new ArrayList<>();
                        List comments = new ArrayList();
                        List itemSeen = new ArrayList();
                        feed.put("likes", likes);
                        feed.put("comments", comments);
                        feed.put("percent", y);
                        feed.put("date", z);
                        feed.put("username", ParseUser.getCurrentUser().getUsername());
                        feed.put("itemSeen", itemSeen);

                        feed.put("profilename", ActProfiles.profileDatastore.getProfile(ActGoals.profileNum).name);
                        feed.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast t = Toast.makeText(getActivity(), "Shared!", Toast.LENGTH_SHORT);
                                    t.show();
                                } else {
                                    Toast t = Toast.makeText(getActivity(), "Trouble sharing, sorry!", Toast.LENGTH_SHORT);
                                    e.printStackTrace();
                                    t.show();
                                }
                            }
                        });


                    }
                }else{Toast t = Toast.makeText(getActivity(), "Please check network connection!", Toast.LENGTH_SHORT);
                t.show();
                }


            }


        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int num) {
                dialog.cancel();
            }
        });
        //builder.setCancelable(false);
        builder.show();


        //dialog.show();


        return false;

    }


}
