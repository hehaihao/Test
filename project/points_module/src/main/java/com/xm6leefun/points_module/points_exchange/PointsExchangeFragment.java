package com.xm6leefun.points_module.points_exchange;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.fragment.BasePresenterFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.dialog.SelectWalletDialog;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.busbean.LoginOutBusBean;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.points_exchange.adapter.PointsListAdapter;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeContract;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangePresenter;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeResultBean;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/8 15:29
 */
@Route(path = ModuleRouterTable.HOME_POINTS_EXCHANGE_FRAGMENT)
public class PointsExchangeFragment extends BasePresenterFragment<PointsExchangePresenter> implements PointsExchangeContract.IView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.base_topBar_iv_back)
    ImageView base_topBar_iv_back;
    @BindView(R2.id.base_topBar_tv_title)
    TextView base_topBar_tv_title;
    @BindView(R2.id.base_topBar_iv_right)
    ImageView base_topBar_iv_right;
    @BindView(R2.id.base_topBar_tv_right)
    TextView base_topBar_tv_right;
    @BindView(R2.id.root_view)
    LinearLayout root_view;
    @BindView(R2.id.curr_address_tv)
    TextView curr_address_tv;
    @BindView(R2.id.userName_tv)
    TextView userName_tv;
    @BindView(R2.id.head_img)
    ImageView head_img;
    @BindView(R2.id.switch_wallet)
    ImageView switch_wallet;
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.exchange_rec)
    EmptyDataRecyclerView exchange_rec_layout;

    private RecyclerView exchange_rec;

    private PointsListAdapter adapter;

    @Override
    protected PointsExchangePresenter createPresenter() {
        return new PointsExchangePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_points_exchange;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setDarkMode(getActivity());//暗色模式，字体为亮色
        root_view.setPadding(0,getStatusBarHeight(),0,0);
        EventBus.getDefault().register(this);
        base_topBar_iv_back.setVisibility(View.INVISIBLE);
        base_topBar_tv_title.setText(R.string.points_toptitle_str);
        base_topBar_iv_right.setVisibility(View.GONE);
        base_topBar_tv_right.setVisibility(View.VISIBLE);
        base_topBar_tv_right.setText(R.string.switch_zwd_acount);
        base_topBar_tv_right.setOnClickListener(this);
        switch_wallet.setOnClickListener(this);
        ultra_pull.setRefreshHeader(new ClassicsHeader(getContext()).setAccentColor(getResources().getColor(R.color.white)));
        ultra_pull.setFooterHeight(0);
        ultra_pull.setOnRefreshListener(this);
        exchange_rec = exchange_rec_layout.getmRecyclerView();
        exchange_rec.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.switch_wallet){//切换钱包
            presenter.getAllWalletList();//获取所有钱包列表进行切换
        }else if(id == R.id.base_topBar_tv_right){//切换真唯度账户
            ModuleServiceUtils.navigateLoginPage(true);
        }
    }

    private String userMobile = "";
    private Wallet_Main mWalletMain;
    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        this.mWalletMain = walletMain;
        curr_address_tv.setText(walletMain.getAddress());
        //获取用户登录数据
        userName_tv.setText(SharePreferenceUtil.getString(AppAllKey.NICK_NAME));
        userMobile = SharePreferenceUtil.getString(AppAllKey.MOBILE);
        ImageLoader.loadCircleImage(getActivity(),SharePreferenceUtil.getString(AppAllKey.HEAD_PORTRAIT),head_img);
        presenter.getList(userMobile);
    }

    @Override
    public void getListSuccess(PointsExchangeResultBean bean) {
        List<PointsExchangeResultBean.ListBean> pointsExchangListBeans = bean.getList();
        if(pointsExchangListBeans != null && pointsExchangListBeans.size() > 0) {
            exchange_rec_layout.showEmptyView(0,pointsExchangListBeans.size(),false);
            if (adapter == null) {
                adapter = new PointsListAdapter(getActivity(), R.layout.item_points_exchange_layout, pointsExchangListBeans);
                adapter.setOnItemClickListener(this);
                exchange_rec.setAdapter(adapter);
            } else {
                adapter.setNewData(pointsExchangListBeans);
            }
        }else{
            exchange_rec_layout.showEmptyView(0,1,false);
        }
        finishLoad();
    }

    @Override
    public void getListFail(String msg) {
        finishLoad();
        exchange_rec_layout.showEmptyView(0,1,false);
    }

    private SelectWalletDialog.Builder selectWalletDialogBuilder;
    /**
     * 获取所有有效钱包地址，用于切换
     * @param walletMains
     */
    @Override
    public void getAllWalletListSuccess(List<Wallet_Main> walletMains) {
        if(mWalletMain != null) {
            if(selectWalletDialogBuilder == null) {
                selectWalletDialogBuilder = new SelectWalletDialog.Builder()
                        .setWalletList(walletMains)
                        .setSelectAddress(mWalletMain.getAddress())
                        .setClickListener(walletMain -> presenter.setAddress(walletMain.getAddress(), mWalletMain.getAddress()));
            }else{
                selectWalletDialogBuilder.setWalletList(walletMains).setSelectAddress(mWalletMain.getAddress());
            }
            SelectWalletDialog selectWalletDialog = selectWalletDialogBuilder.build();
            selectWalletDialog.show(mChildFragmentManager, SelectWalletDialog.TAG);
        }
    }

    /**
     * 切换地址成功
     * @param walletMain
     */
    @Override
    public void setAddressSuccess(Wallet_Main walletMain) {
        EventBus.getDefault().post(new RefreshWalletDataBusBean());
    }

    @Override
    public void onLoadFail(String msg) {
        finishLoad();
    }

    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }

    /**
     * 粘性事件直接刷新首页钱包数据
     * @param walletMain
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void refreshWalletDataSticky(Wallet_Main walletMain){
        getCurrWalletSuccess(walletMain);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getCurrWallet();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PointsExchangeResultBean.ListBean bean = (PointsExchangeResultBean.ListBean) adapter.getData().get(position);
        ModuleServiceUtils.navigateSelectPointsTypePage(bean);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            StatusBarUtil.setDarkMode(getActivity());//暗色模式，字体为亮色
        }
    }

    @Override
    public void reLogin() {
        super.reLogin();
        EventBus.getDefault().post(new LoginOutBusBean());//通知首页退出登录，切换界面
    }
}
