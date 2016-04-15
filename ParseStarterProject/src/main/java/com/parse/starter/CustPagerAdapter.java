package com.parse.starter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Borris on 08/02/2016.
 */

public class CustPagerAdapter extends FragmentStatePagerAdapter {

    public CustPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return new Frag1Feed();
                case 1:
                    return new Frag2Goals();
                case 2:
                    return new Frag3Stats();
                default:
                    return new Frag1Feed();
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