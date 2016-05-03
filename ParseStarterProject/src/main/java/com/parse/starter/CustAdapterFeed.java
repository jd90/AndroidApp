package com.parse.starter;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.parse.Parse;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;
        import com.parse.ParseUser;
        import com.parse.SaveCallback;

        import java.util.List;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustAdapterFeed extends ArrayAdapter<ClassGoal> implements View.OnLongClickListener, View.OnClickListener {

    private final Context context;
    private final List<ClassFeedItem> feedList;
    boolean unseen = false;

    public CustAdapterFeed(Context context, List<ClassFeedItem> g) {
        super(context, R.layout.friends_feed, (List) g);

        this.context = context;
        this.feedList = g;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.feed_fragment, parent, false);


        LinearLayout dateCont = (LinearLayout) row_view.findViewById(R.id.dateContainer);
        TextView tUser = (TextView) row_view.findViewById(R.id.user);
        TextView tProfile = (TextView) row_view.findViewById(R.id.profile);
        TextView tPercent = (TextView) row_view.findViewById(R.id.percent);
        TextView tDate = (TextView) row_view.findViewById(R.id.date);
        TextView dateText = (TextView) row_view.findViewById(R.id.dateText);
        tUser.setText(feedList.get(position).username);
        tProfile.setText(feedList.get(position).profileName);
        ImageView heart = (ImageView) row_view.findViewById(R.id.heart);
        ImageView comment = (ImageView) row_view.findViewById(R.id.comment);
        TextView likers = (TextView) row_view.findViewById(R.id.likers);
        if(feedList.get(position).percent == 200){
            dateCont.setVisibility(View.VISIBLE);
            tPercent.setVisibility(View.GONE);
            tUser.setVisibility(View.GONE);
            tDate.setText(feedList.get(position).username);
            dateText.setVisibility(View.GONE);
            heart.setVisibility(View.GONE);
            comment.setVisibility(View.GONE);
        }else {

                dateCont.setVisibility(View.VISIBLE);//problem here - parse user is a nullpointer? should have been stopped before this point tho...
            if(feedList.get(position).likes.contains(ParseUser.getCurrentUser().getUsername())){
                heart.setImageResource(R.drawable.heart_red);

            }else{
                heart.setImageResource(R.drawable.heart);}
            if(feedList.get(position).likes.size()>0){
                likers.setVisibility(View.VISIBLE);
                String likersText= "Liked by:";
                if(feedList.get(position).likes.contains(ParseUser.getCurrentUser().getUsername())){
                    likersText+=" you";
                }
                if(feedList.get(position).likes.size()>1){likersText+=",";}
                for(int i=0; i<feedList.get(position).likes.size();i++){
                    String name =feedList.get(position).likes.get(i).toString();
                    if(!name.equals(ParseUser.getCurrentUser().getUsername())){
                    if(i==0){likersText+= " "+name;}
                    else{likersText+=", "+name;}
                    }
                }
                likers.setText(likersText);
            }
            tDate.setText(feedList.get(position).username.toUpperCase());
            tUser.setText(feedList.get(position).username + " completed ");
            tPercent.setText(String.valueOf(feedList.get(position).percent) + "%");
            tProfile.setText(" of " + feedList.get(position).profileName + " goals");
            dateText.setText("for week ending: " + feedList.get(position).date);
            heart.setTag(position);
            heart.setOnClickListener(this);
            if(!feedList.get(position).itemSeen.contains(ParseUser.getCurrentUser().getUsername())){

                tDate.setBackgroundColor(R.drawable.profbutton_default_state);

                //unseen=true;
                //ActGoals.t0.setIcon(R.drawable.abc_btn_rating_star_on_mtrl_alpha);

            }

        }
        row_view.setTag(position);
        row_view.setOnLongClickListener(this);
        return row_view;
    }


    @Override
    public boolean onLongClick(View v) {

            return false;
    }

    @Override
    public void onClick(View v) {


        ConnectivityManager connect = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connect.getActiveNetworkInfo() != null && ParseUser.getCurrentUser() != null) {
            Log.i("75757", v.getTag().toString());
            int pos = Integer.parseInt(v.getTag().toString());
            if (!feedList.get(pos).id.equals("")) {
                ParseQuery query2 = new ParseQuery("Feed");
                try {
                    ParseObject o = query2.get(feedList.get(pos).id);
                    Log.i("75757 1", v.getTag().toString());
                    List likes = o.getList("likes");
                    boolean exists = false;
                    int position = 200;
                    for (int i = 0; i < likes.size(); i++) {
                        if (likes.get(i).equals(ParseUser.getCurrentUser().getUsername())) {
                            exists = true;
                            position = i;
                        }
                    }
                    if (exists) {
                        likes.remove(position);
                        feedList.get(pos).likes.remove(position);
                        o.put("likes", likes);
                        o.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    notifyDataSetChanged();
                                } else {
                                    Toast t = Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT);
                                    e.printStackTrace();
                                    t.show();
                                }
                            }
                        });

                        //ImageView heart = (ImageView) v;
                        //heart.setImageResource(R.drawable.heart);
                    } else {

                        likes.add(ParseUser.getCurrentUser().getUsername());
                        feedList.get(pos).likes.add(ParseUser.getCurrentUser().getUsername());
                        Log.i("75757 3", v.getTag().toString());
                        ImageView vv = (ImageView)v;
                        vv.setImageResource(R.drawable.heart_red);
                        vv.animate().scaleX(10).scaleY(10).setDuration(500);
                        o.put("likes", likes);
                        o.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {

                                    notifyDataSetChanged();
                                } else {
                                    Toast t = Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT);
                                    e.printStackTrace();
                                    t.show();
                                }
                            }
                        });
                        //ImageView heart = (ImageView) v;
                        //heart.setImageResource(R.drawable.heart_red);


                    }
                } catch (Exception e) {
                    Toast t = Toast.makeText(getContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        }else{Toast t = Toast.makeText(getContext(), "Error: Please check network connection!", Toast.LENGTH_SHORT);
            t.show();}
    }
}
