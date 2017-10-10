package com.fendoudebb.playandroid.module.feature.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fendoudebb.playandroid.config.C;
import com.fendoudebb.playandroid.module.feature.ui.gank.GankDetailFragment;
import com.fendoudebb.playandroid.module.feature.ui.gank.GankMeiZhiFragment;

/**
 * author : zbj on 2017/10/7 20:01.
 */

public class GankViewPagerAdapter extends FragmentStatePagerAdapter {

    public GankViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return GankMeiZhiFragment.newInstance(C.gank.gank_category[1]);
        }
        return GankDetailFragment.newInstance(C.gank.gank_category[position]);
    }

    @Override
    public int getCount() {
        return C.gank.gank_tab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return C.gank.gank_tab[position];
    }
}
