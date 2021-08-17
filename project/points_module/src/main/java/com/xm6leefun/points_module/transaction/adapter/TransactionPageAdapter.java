package com.xm6leefun.points_module.transaction.adapter;

import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:15
 */
public class TransactionPageAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<Fragment> fragments;
    public TransactionPageAdapter(FragmentManager fm, String[] mTitles, List<Fragment> fragments) {
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
