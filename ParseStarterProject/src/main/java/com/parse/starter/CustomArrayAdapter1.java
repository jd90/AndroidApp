package com.parse.starter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.List;

/**
 * Created by Borris on 05/02/2016.
 */
public class CustomArrayAdapter1 extends ArrayAdapter<Goal> implements View.OnClickListener {

    private final Context context;
    private final List<FeedItem> feedList;

    public CustomArrayAdapter1(Context context, List<FeedItem> g) {
        super(context, R.layout.friends_feed, (List) g);

        this.context = context;
        this.feedList = g;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.feed_fragment, parent, false);

        TextView tUser = (TextView) row_view.findViewById(R.id.user);
        TextView tProfile = (TextView) row_view.findViewById(R.id.profile);
        TextView tPercent = (TextView) row_view.findViewById(R.id.percent);

        tUser.setText(feedList.get(position).username);
        tProfile.setText(feedList.get(position).profileName);
        tPercent.setText(String.valueOf(feedList.get(position).percent)+"%");


        return row_view;
    }

    @Override
    public void onClick(View v) {

    }
}
