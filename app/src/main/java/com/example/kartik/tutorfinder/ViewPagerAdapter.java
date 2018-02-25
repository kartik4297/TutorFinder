package com.example.kartik.tutorfinder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kartik on 27-01-2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

ArrayList<Fragment> fragments = new ArrayList<>();
ArrayList<String> tabTitles = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment fragment, String tabTitle)
    {
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);


    }
    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);

    }
}
