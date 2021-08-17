package com.xm6leefun.wallet_module.backup.prikey.adapter;


import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @Description:备份keystore界面adapter
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public class BackupViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> fragments;

    public BackupViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = mTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return mTitles.length;
    }
    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
