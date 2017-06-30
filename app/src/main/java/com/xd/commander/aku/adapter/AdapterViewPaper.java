package com.xd.commander.aku.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AdapterViewPaper extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragments;
    private final ArrayList<String> mFragmentTitles = new ArrayList<>();

    public AdapterViewPaper(FragmentManager fm,ArrayList<Fragment> fragments,List<String> name) {
        super(fm);
        this.mFragments = fragments;
        mFragmentTitles.addAll(name);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
