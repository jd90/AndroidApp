package com.parse.starter;

/**
 * Created by Borris on 04/02/2016.
 */
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class Frag2Goals extends ListFragment {

    static CustAdapterGoals adapter;

    public Frag2Goals() {
    // Required empty public constructor
    }

    GoalStore1 goalStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CustAdapterGoals(getActivity(), ActGoals.goalStore);
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