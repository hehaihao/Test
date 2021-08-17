package com.xm6leefun.points_module.points_exchange_record;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.SelectAddressBean;
import com.xm6leefun.export_module.busbean.LoginOutBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeResultBean;
import com.xm6leefun.points_module.points_exchange_record.adapter.PointsExchangeRecordAdapter;
import com.xm6leefun.points_module.points_exchange_record.dialog.ExchangePointDialog;
import com.xm6leefun.points_module.points_exchange_record.mvp.PointsExchangeRecordContract;
import com.xm6leefun.points_module.points_exchange_record.mvp.PointsExchangeRecordPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.points_exchange_record.mvp.RecordResultBean;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description:积分兑换记录
 * @Author: cyh
 * @CreateDate: 2021/4/9 10:10
 */

@Route(path = ModuleRouterTable.POINTS_EXCHANGE_RECORD)
public class PointsExchangeRecordActivity extends BaseToolbarPresenterActivity<PointsExchangeRecordPresenter> implements PointsExchangeRecordContract.IView, OnRefreshListener, OnLoadMoreListener {
    @BindView(R2.id.layout_appbar)
    AppBarLayout layout_appbar;
    @BindView(R2.id.icon)
    ImageView icon;
    @BindView(R2.id.points_title_tv)
    TextView pointsTitleTv;
    @BindView(R2.id.points_tv)
    TextView pointsTv;
    @BindView(R2.id.date_tv)
    TextView date_tv;
    @BindView(R2.id.date_tip_tv)
    TextView date_tip_tv;
    @BindView(R2.id.status_tv)
    TextView status_tv;
    @BindView(R2.id.recycler_layout)
    EmptyDataRecyclerView recycler_layout;
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultraPull;

    private RecyclerView recycler;

    private PointsExchangeRecordAdapter adapter;

    @Override
    protected PointsExchangeRecordPresenter createPresenter() {
        return new PointsExchangeRecordPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange_record_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.points_toptitle_str);
        topBarTvRight.setVisibility(View.GONE);
        recycler = recycler_layout.getmRecyclerView();
        recycler.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        recycler.setLayoutManager(new WrapContentLinearLayoutManager(this));
        recycler.addItemDecoration(new SpacesItemDecoration(30));
        ultraPull.setRefreshHeader(new ClassicsHeader(this));
        ultraPull.setFooterHeight(0);
        ultraPull.setOnRefreshListener(this);
        ultraPull.setOnLoadMoreListener(this);
        addOnClickListeners(R.id.to_exchange,R.id.date_tv,R.id.status_tv);
        banAppBarScroll(true);
    }
    private String mobile = "";
    private String startDate = "";
    private String endDate = "";

    private long state = 0;
    private int page = 1;


    private PointsExchangeResultBean.ListBean mBean;
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mBean = extras.getParcelable(ModuleServiceUtils.DATA);
            ImageLoader.loadCircleImage(this,mBean.getIntegralLogo(),icon);
            pointsTitleTv.setText(mBean.getIntegralName());
            pointsTv.setText(mBean.getNum()+"");
        }
        mobile = SharePreferenceUtil.getString(AppAllKey.MOBILE);
        presenter.exchangeList(false,mobile,mBean.getExpendId(),state,startDate,endDate,page);
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

    @Override
    public void getListSuccess(RecordResultBean recordResultBean, boolean isLoadMore) {
        List<RecordResultBean.ListBean> listBeans = recordResultBean.getList();
        if(listBeans != null && listBeans.size() > 0){
            recycler_layout.showEmptyView(listBeans.size(),page++,false);
            if(adapter == null){
                adapter = new PointsExchangeRecordAdapter(R.layout.item_list_exchange_record_layout,listBeans);
                recycler.setAdapter(adapter);
            }else{
                if(isLoadMore) {
                    adapter.addData(listBeans);
                }else {
                    recycler.smoothScrollToPosition(0);//刷新回顶部
                    adapter.replaceData(listBeans);
                }
            }
        }else{
            recycler_layout.showEmptyView(0,page,false);
        }
        ultraPull.setEnableLoadMore(adapter != null && adapter.getData().size() < recordResultBean.getPageInfo().getTotalNum());
        finishLoad();
    }

    @Override
    public void getListFail(String msg) {
        recycler_layout.showEmptyView(0,page,false);
        finishLoad();
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void integralExchangeSuccess() {
        ToastUtil.showCenterToast(getString(R.string.points_exchange_success_tip_str));
        onRefresh(ultraPull);
    }

    @Override
    public void integralExchangeFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    private void finishLoad(){
        ultraPull.finishRefresh();
        ultraPull.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        presenter.exchangeList(false,mobile,mBean.getExpendId(),state,startDate,endDate,page);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.exchangeList(true,mobile,mBean.getExpendId(),state,startDate,endDate,page);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.to_exchange){
            showExchangeDialog();
        }else if(id == R.id.date_tv){//选择日期
            showSelectDate();
        }else if(id == R.id.status_tv){//选择状态
            showSelectStatus();
        }
    }

    /**
     * 积分兑换
     */
    private void showExchangeDialog() {
        ExchangePointDialog exchangePointDialog = getBuilder("",mBean.getNum()).build();
        exchangePointDialog.show(mFragmentManager,ExchangePointDialog.TAG);
    }

    private ExchangePointDialog.AddressCallBack addressCallBack;
    private ExchangePointDialog.Builder builder;
    private ExchangePointDialog.Builder getBuilder(String address,long points) {
        if(builder == null)
            builder = new ExchangePointDialog.Builder()
                    .setAddress(address)
                    .setBalancePoints(points)
                    .setClickListener(new ExchangePointDialog.ClickListener() {
                        @Override
                        public void onSure(String toAddress, long points) {
                            //积分兑换
                            presenter.integralExchange(mobile,mBean.getExpendId(),toAddress,points);
                        }

                        @Override
                        public void selectAddress(ExchangePointDialog.AddressCallBack addressCallBack) {
                            PointsExchangeRecordActivity.this.addressCallBack = addressCallBack;
                            ARouter.getInstance()
                                    .build(ModuleRouterTable.ADDRESS_LIST_PAGE)
                                    .withBoolean(ModuleServiceUtils.IS_SELECT_ADDRESS,true)
                                    .navigation(PointsExchangeRecordActivity.this,ModuleServiceUtils.REQ_SELECT_ADDRESS);
                        }
                    });
        builder.setAddress(address).setBalancePoints(points);//重置内容
        return builder;
    }

    private TimePickerView datePicke;
    private OptionsPickerView statusPicke;
    /**
     * 选择日期
     */
    private void showSelectDate(){
        if(datePicke == null) {
            datePicke = new TimePickerBuilder(this, (date, v) -> {
                date_tv.setText(getTime(date));
                date_tip_tv.setVisibility(View.VISIBLE);
                date_tip_tv.setText(getTimeCn(date));
                startDate = getTime(date)+"-01";
                endDate = getTime(date)+"-31";
                onRefresh(ultraPull);//刷新数据
            })
                    .setType(new boolean[]{true, true, false, false, false, false})
                    .setTextXOffset(90,-90,0,0,0,0)
                    .setItemVisibleCount(5)
                    .build();
        }
        datePicke.show();
    }
    private List<String> statusList;

    /**
     * 选择状态
     */
    private void showSelectStatus(){
        if(statusPicke == null) {
            String[] statusArr = getResources().getStringArray(R.array.status_arr);
            statusList = Arrays.asList(statusArr);
            statusPicke = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                String status = statusList.size() > 0 ? statusList.get(options1) : "";
                status_tv.setText(status);
                state = options1;//选中状态
                onRefresh(ultraPull);//刷新数据
            }).build();
            statusPicke.setPicker(statusList);
        }
        statusPicke.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
    private String getTimeCn(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }

    private String selectAddress = "";
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_SELECT_ADDRESS://处理选择地址结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_SELECT_ADDRESS){
                    if(data != null){
                        SelectAddressBean addressBean = data.getParcelableExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_ADDRESS);
                        selectAddress = addressBean.getAddress();
                        if(addressCallBack!= null)addressCallBack.seleted(selectAddress);
                    }
                }
                break;
        }
    }

    @Override
    public void reLogin() {
        super.reLogin();
        ActivityUtil.finishActivity(this);
        EventBus.getDefault().post(new LoginOutBusBean());//通知首页退出登录，切换界面
    }
}
