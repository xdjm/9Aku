package com.xd.commander.aku.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.xd.commander.aku.fragment.FragmentAll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AdapterViewPaper extends FragmentStatePagerAdapter {
    private String[] mTab = new String[]{"免费", "付费", "演示"};
    public AdapterViewPaper(FragmentManager fm) {
        super(fm);
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
        return FragmentAll.newInstance(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    @Override
    public int getCount() {
        return mTab.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTab[position];
    }
}
