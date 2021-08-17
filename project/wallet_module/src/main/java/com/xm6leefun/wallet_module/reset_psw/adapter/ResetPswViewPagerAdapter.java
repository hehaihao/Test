package com.xm6leefun.wallet_module.reset_psw.adapter;

import com.xm6leefun.wallet_module.reset_psw.ResetPswFragment;

import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/31 13:50
 */
public class ResetPswViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<ResetPswFragment> fragments;

    public ResetPswViewPagerAdapter(FragmentManager fm, String[] mTitles, List<ResetPswFragment> fragments) {
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
