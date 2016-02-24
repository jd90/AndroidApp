package com.parse.starter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Borris on 08/02/2016.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
                default:
                    return new Fragment1();
            }
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public String getPageTitle(int position) {

        String title = "";
        switch (position) {

            case 0:
                title = "feed";
                break;
            case 1:
                title = "goals";
                break;
            case 2:
                title = "stats";
                break;
            default:
                title = "";
                break;
        }

        return title;
    }
}