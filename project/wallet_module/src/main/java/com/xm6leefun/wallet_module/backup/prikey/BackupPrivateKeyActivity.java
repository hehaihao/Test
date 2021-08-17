package com.xm6leefun.wallet_module.backup.prikey;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.xm6leefun.common.app.activity.BasePresenterActivity;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup.keystore.BackupKeystoreActivity;
import com.xm6leefun.wallet_module.backup.prikey.adapter.BackupViewPagerAdapter;
import com.xm6leefun.wallet_module.backup.prikey.mvp.BackupPrikeyContract;
import com.xm6leefun.wallet_module.backup.prikey.mvp.BackupPrikeyPresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Description:备份私钥
 * @Author: hhh
 * @CreateDate: 2021/3/30 16:05
 */
public class BackupPrivateKeyActivity extends BaseToolbarPresenterActivity<BackupPrikeyPresenter> implements BackupPrikeyContract.IView, TabLayout.OnTabSelectedListener {
    public static final String CUS_DATA = "cus_data";
    @BindView(R2.id.tab_backup_private_key)
    TabLayout mTab;
    @BindView(R2.id.vp_backup_private_key)
    ViewPager viewPager;

    private BackupViewPagerAdapter backupViewPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private CusCreateWalletBean cusCreateWallet;

    @Override
    protected BackupPrikeyPresenter createPresenter() {
        return new BackupPrikeyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_backup_keystore_prikey_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.backup_hint_content_pk);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                cusCreateWallet = bundle.getParcelable(CUS_DATA);
                if (cusCreateWallet != null) {
                    initTab(cusCreateWallet);
                }
            }
        }
    }

    private void initTab(CusCreateWalletBean cusCreateWallet) {
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        String[] titles = getResources().getStringArray(R.array.private_key_tabs);
        fragments.add(BackupPrivateKeyFragment.getInstance(cusCreateWallet.getKeysInfo().getPrivateKey()));
        fragments.add(BackupPrivateKeyQrCodeFragment.getInstance(cusCreateWallet.getKeysInfo().getPrivateKey()));
        BackupViewPagerAdapter backupViewPagerAdapter = new BackupViewPagerAdapter(
                mFragmentManager,
                titles,
                fragments);
        viewPager.setAdapter(backupViewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        mTab.addOnTabSelectedListener(this);
        mTab.setupWithViewPager(viewPager);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTextStyle(Typeface.DEFAULT_BOLD,24,tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setTextStyle(Typeface typeface,int size,TabLayout.Tab tab){
        View view = tab.getCustomView();
        if(view == null){
            tab.setCustomView(R.layout.tab_item_layout);
        }
        ViewHolder holder = new ViewHolder(tab.getCustomView());
        holder.textView.setTextColor(mTab.getTabTextColors());
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        holder.textView.setTypeface(typeface);
        holder.textView.setText(tab.getText());
    }

    class ViewHolder {
        TextView textView;
        ViewHolder(View tabView) {
            textView = tabView.findViewById(R.id.tab_item_textview);
        }
    }
}
