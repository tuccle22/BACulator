package com.example.macmini.baculator.PersonFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class PersonAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public PersonAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GenderFragment.newInstance(0, "sex");
            case 1:
                return WeightFragment.newInstance(1, "lbs");
            case 2:
                return TimeFragment.newInstance(2, "time");
            case 3:
                return DoneFragment.newInstance(3, "done");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getArguments().getString("someTitle");
    }

}
