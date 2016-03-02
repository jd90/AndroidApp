package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;


public class Fragment1 extends ListFragment {

    // Required empty public constructor
    public Fragment1() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomArrayAdapter1 adapter = new CustomArrayAdapter1(getActivity(), ProfileMainActivity.goalStore);
        setListAdapter(adapter);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getListView().setDivider(null);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        v.setPressed(true);
    }

}