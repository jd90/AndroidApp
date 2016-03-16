package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class Fragment2 extends ListFragment {

    static CustomArrayAdapter2 adapter;

    public Fragment2() {
    // Required empty public constructor
    }

    GoalStore1 goalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CustomArrayAdapter2(getActivity(), ProfileMainActivity.goalStore);
        setListAdapter(adapter);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    public static void reload(){
        adapter.notifyDataSetChanged();
    }





}