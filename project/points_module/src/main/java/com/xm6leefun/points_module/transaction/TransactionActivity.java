package com.xm6leefun.points_module.transaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ShowUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.points_intro.PointsIntroActivity;
import com.xm6leefun.points_module.transaction.adapter.TransactionPageAdapter;
import com.xm6leefun.points_module.transaction.mvp.TransactionContract;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;
import com.xm6leefun.points_module.transaction.mvp.TransactionPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 9:40
 */
@Route(path = ModuleRouterTable.HOME_TRANSACTION_PAGE)
public class TransactionActivity extends BaseToolbarPresenterActivity<TransactionPresenter> implements TransactionContract.IView,TabLayout.OnTabSelectedListener {
    @BindView(R2.id.layout_appbar)
    AppBarLayout layout_appbar;
    @BindView(R2.id.top_layout)
    RelativeLayout top_layout;
    @BindView(R2.id.asset_type_tv)
    TextView asset_type_tv;
    @BindView(R2.id.asset_total_tv)
    TextView asset_total_tv;
    @BindView(R2.id.tab_transaction)
    TabLayout tab_transaction;
    @BindView(R2.id.vp_transaction)
    ViewPager vp_transaction;
    private TransactionPageAdapter transactionPageAdapter;

    @Override
    protected TransactionPresenter createPresenter() {
        return new TransactionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_layout;
    }

    @Override
    protected void initView() {
        initTopBanner();
        addOnClickListeners(R.id.receipt,R.id.send,R.id.base_topBar_tv_right);
        topBarIvRight.setVisibility(View.GONE);
        topBarTvRight.setVisibility(View.VISIBLE);
        topBarTvRight.setText(R.string.points_intro_title_str);
    }

    /**
     * 设置顶部比例
     */
    private void initTopBanner() {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) top_layout.getLayoutParams();
        int[] size = ShowUtil.getScreenSize(this);
        int width = size[0];
        int height = (int) (width* 155.f/345);
        layoutParams.height = height;
        layoutParams.width = width;
        top_layout.setLayoutParams(layoutParams);
    }

    private HomeDataResultBean.FtBean ftBean;
    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            ftBean = args.getParcelable(ModuleServiceUtils.DATA);
            dealData(ftBean);
        }
        presenter.getCurrWallet();
    }

    private void dealData(HomeDataResultBean.FtBean ftBean) {
        if(ftBean==null)return;
        topBarTvTitle.setText(ftBean.getToken_name());
        asset_type_tv.setText(getString(R.string.assets_name_str,ftBean.getToken_name()));
        asset_total_tv.setText(ftBean.getNum()+"");
    }

    private void initTab(String address) {
        tab_transaction.addOnTabSelectedListener(this);
        banAppBarScroll(true);
        tab_transaction.addTab(tab_transaction.newTab());
        tab_transaction.addTab(tab_transaction.newTab());
        tab_transaction.addTab(tab_transaction.newTab());
        tab_transaction.addTab(tab_transaction.newTab());

        String[] titles = getResources().getStringArray(R.array.transaction_tabs);
        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            fragments.add(TransactionFragment.getInstance(i,ftBean.getId()+"",address,ftBean.getContract_address()));
        }
        transactionPageAdapter = new TransactionPageAdapter(
                mFragmentManager,
                titles,
                fragments);
        vp_transaction.setAdapter(transactionPageAdapter);
        vp_transaction.setOffscreenPageLimit(fragments.size()-1);
        tab_transaction.addOnTabSelectedListener(this);
        tab_transaction.setupWithViewPager(vp_transaction);

    }

    /**
     * 控制appbar的滑动
     * @param isScroll true 允许滑动，false禁止滑动
     * */
    private void banAppBarScroll(boolean isScroll) {
        View childAt = layout_appbar.getChildAt(0);
        AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) childAt.getLayoutParams();
        if(isScroll){
            mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            childAt.setLayoutParams(mAppBarParams);
        }else{
            mAppBarParams.setScrollFlags(0);
        }
    }

    private Wallet_Main mWalletMain;
    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        this.mWalletMain = walletMain;
        initTab(walletMain.getAddress());
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if(view == null){
            tab.setCustomView(R.layout.tab_item_layout);
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
        textView.setTextColor(tab_transaction.getTabTextColors());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if(view == null){
            tab.setCustomView(R.layout.tab_item_layout);
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
        textView.setTextColor(tab_transaction.getTabTextColors());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(tab.getText());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.receipt) {
            ModuleServiceUtils.navigateReceivePointsPage(ftBean.getToken_name());
        } else if (id == R.id.send) {
            if(mWalletMain!=null && ftBean != null)
                ModuleServiceUtils.navigateSendPointsPage(ftBean,mWalletMain.getAddress());
        }else if (id == R.id.base_topBar_tv_right) {
            Bundle args = new Bundle();
            args.putString(PointsIntroActivity.NAME,ftBean.getToken_name());
            args.putString(PointsIntroActivity.ID,ftBean.getId()+"");
            ActivityUtil.startActivity(PointsIntroActivity.class,false,args);
        }
    }
}
