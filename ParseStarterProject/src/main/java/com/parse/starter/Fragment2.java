package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Fragment2 extends ListFragment {

    // Required empty public constructor
    public Fragment2() {
    }



    GoalStore1 goalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomArrayAdapter2 adapter = new CustomArrayAdapter2(getActivity(), MainActivity.goalStore);
        setListAdapter(adapter);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setDivider(null);

       // final ListView  lv=getListView();

        ///this is used to long click - could be used to make a toast of your reasons for undertaking a goal

     //   lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

      //      public boolean onItemLongClick(AdapterView<?> arg0, View v,
                            //               int index, long arg3) {

        //        Toast.makeText(getActivity(), lv.getItemAtPosition(index).toString(), Toast.LENGTH_LONG).show();
           //     return false;
       //     }
      //  });
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }






}