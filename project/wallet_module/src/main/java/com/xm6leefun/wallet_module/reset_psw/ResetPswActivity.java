package com.xm6leefun.wallet_module.reset_psw;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.xm6leefun.common.app.activity.BaseComActivity;
import com.xm6leefun.common.app.activity.BaseToolbarComActivity;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup.prikey.BackupPrivateKeyActivity;
import com.xm6leefun.wallet_module.reset_psw.adapter.ResetPswViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/31 13:50
 */
public class ResetPswActivity extends BaseToolbarComActivity implements TabLayout.OnTabSelectedListener {
    public static final String ADDRESS = "address";
    public static final String TYPE = "type";
    @BindView(R2.id.tab_reset_pwd)
    TabLayout mTab;
    @BindView(R2.id.vp_reset_pwd)
    ViewPager viewPager;
    private ResetPswViewPagerAdapter resetPwdViewPagerAdapter;

    private String address;
    private List<ResetPswFragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_psw_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(getResources().getString(R.string.reset_pwd_title));
        topBarTvRight.setVisibility(View.GONE);
        topBarIvRight.setVisibility(View.VISIBLE);
        topBarIvRight.setImageResource(R.mipmap.home_scaner_icon);
        addOnClickListeners(R.id.base_topBar_iv_right);
    }

    private void initTab() {
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());

        String[] titles = getResources().getStringArray(R.array.reset_psw_tabs);
        fragments = new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            fragments.add(ResetPswFragment.getInstance(i, address));
        }
        resetPwdViewPagerAdapter = new ResetPswViewPagerAdapter(
                mFragmentManager,
                titles,
                fragments);
        viewPager.setAdapter(resetPwdViewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        mTab.addOnTabSelectedListener(this);
        mTab.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                address = bundle.getString(ADDRESS);
                initTab();
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.base_topBar_iv_right){//扫一扫
            ARouter.getInstance().build(ModuleRouterTable.SCAN_PAGE).navigation(this, ModuleServiceUtils.REQ_QR_CODE);
        }
    }

    private int mSelectPosition;//当前选中
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mSelectPosition = tab.getPosition();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_QR_CODE://处理扫码结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_QR_SCAN){
                    if(data != null){
                        String result = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN);
                        fragments.get(mSelectPosition).setScanResult(result);
                    }
                }
                break;
        }
    }
}
