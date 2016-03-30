package com.parse.starter;

/**
 * Created by Borris on 29/03/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class CustomArrayFriends extends ArrayAdapter{

    Context context;
    List pastGoals;
    int clickedPosition;

public CustomArrayFriends(Context context, List g, int x) {
        super(context, R.layout.goal_list_item, g);

        this.context = context;
        this.pastGoals = g;
        clickedPosition=x;

        //take the list and turn the first position object into an arraylist for this to use... then it wont repeat sixteen times?

        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ViewHolder holder;

        if (convertView == null) {
        convertView = inflater.inflate(R.layout.goal_list_item, parent, false);
        holder = new ViewHolder();
        holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.goal_Layout_Relative);
        holder.goalTitleView = (TextView) convertView.findViewById(R.id.goalTitle);

                holder.goalTitleView.setText(pastGoals.get(position).toString());
        convertView.setTag(holder);
        }
        else {
        holder = (ViewHolder) convertView.getTag();
        }


/**
        try {


        JSONObject jsonObject = jsonArray.getJSONObject(i);

        Log.i("888888", "jsonlength2" + jsonArray.length());
        holder.goalTitleView.setText(jsonObject.optString("name"));

        Log.i("888888", "jsonlength3" + jsonArray.length());
        if (jsonObject.optString("total").equals("")) {
        Log.i("88888", "reporting empty profile");
        } else {
        Log.i("888888", "jsonlength33" + jsonArray.length());
        holder.numberOutOfView.setText(jsonObject.optString("total"));
        Log.i("888888", "jsonlength4" + jsonArray.length());
        holder.percentView.setText(String.valueOf(Double.parseDouble(jsonObject.optString("percent"))));
        Log.i("888888", "jsonlength5" + jsonArray.length());
        holder.percentageBar.setProgress((int) Double.parseDouble(jsonObject.optString("percent")));

        if(Integer.parseInt(jsonObject.optString("b0"))!=0) {
        holder.b0.setImageResource(R.drawable.m1);}
        else{holder.b0.setImageResource(R.drawable.m2);}
        if(Integer.parseInt(jsonObject.optString("b1"))!=0) {
        holder.b1.setImageResource(R.drawable.t1);}
        else{holder.b1.setImageResource(R.drawable.t2);}
        if(Integer.parseInt(jsonObject.optString("b2"))!=0) {
        holder.b2.setImageResource(R.drawable.w1);}
        else{holder.b2.setImageResource(R.drawable.w2);}
        if(Integer.parseInt(jsonObject.optString("b3"))!=0) {
        holder.b3.setImageResource(R.drawable.t1);}
        else{holder.b3.setImageResource(R.drawable.t2);}
        if(Integer.parseInt(jsonObject.optString("b4"))!=0) {
        holder.b4.setImageResource(R.drawable.f1);}
        else{holder.b4.setImageResource(R.drawable.f2);}
        if(Integer.parseInt(jsonObject.optString("b5"))!=0) {
        holder.b5.setImageResource(R.drawable.s1);}
        else{holder.b5.setImageResource(R.drawable.s2);}
        if(Integer.parseInt(jsonObject.optString("b6"))!=0) {
        holder.b6.setImageResource(R.drawable.s1);}
        else{holder.b6.setImageResource(R.drawable.s2);}

        }
        }
        }catch(Exception e){
        Log.i("888888", "jsonlength error" + e.toString());}



        holder.percentageBar.getProgressDrawable().setColorFilter(Color.parseColor("#47aff3"), PorterDuff.Mode.MULTIPLY);
    **/
        return convertView;
        }

//@Override
public void onClick(View v) {


        }


static class ViewHolder {
    // this enables reuse. recyler view it is called? means that it only has to do a findviewbyid call once then reuse it, saving valuable processing time.
    //this means that things like scrolling etc are smoother
    private RelativeLayout relativeLayout;
    private TextView goalTitleView;
    private TextView numberOutOfView;
    private TextView percentView;
    private ImageView b0,b1,b2,b3,b4,b5,b6;
    private ProgressBar percentageBar;
}




}