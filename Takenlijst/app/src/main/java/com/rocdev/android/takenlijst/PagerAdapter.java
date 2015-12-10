package com.rocdev.android.takenlijst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

/**
 * Created by piet on 28-09-15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int aantalTabs;
    HashMap<Integer, LijstFragment> fragments = new HashMap<>();


    public PagerAdapter(FragmentManager fm, int aantalTabs) {
        super(fm);
        this.aantalTabs = aantalTabs;
    }


    @Override
    public Fragment getItem(int position) {
        LijstFragment lijstFragment = new LijstFragment();
        lijstFragment.setTabPos(position);
        fragments.put(Integer.valueOf(position), lijstFragment);
        return lijstFragment;
    }

    public LijstFragment getFragment(int key) {
        return fragments.get(key);
    }


    @Override
    public int getCount() {
        return aantalTabs;
    }


}
