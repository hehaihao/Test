package com.xm6leefun.wallet_module.points_property.add_property;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;

import com.xm6leefun.wallet_module.points_property.add_property.adapter.HotPropertyAdapter;
import com.xm6leefun.wallet_module.points_property.add_property.bean.RefreshPropertyBunBean;
import com.xm6leefun.wallet_module.points_property.add_property.mvp.AddPropertyContract;
import com.xm6leefun.wallet_module.points_property.add_property.mvp.AddPropertyPresenter;
import com.xm6leefun.wallet_module.points_property.my_property.MyPropertyActivity;
import com.xm6leefun.wallet_module.points_property.property_manager.PropertyManagerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import butterknife.BindView;


/**
 * @Description: 添加积分资产
 * @Author: cyh
 * @CreateDate: 2021/4/13
 */

@Route(path = ModuleRouterTable.POINTS_PROPERTY_PAGE)
public class AddPropertyActivity extends BaseToolbarPresenterActivity<AddPropertyPresenter> implements AddPropertyContract.IView, HotPropertyAdapter.HotPropertyAddListener {

    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;
    @BindView(R2.id.rec_layout)
    EmptyDataRecyclerView recLayout;
    RecyclerView rec_hot_property;
    private HotPropertyAdapter adapter;
    private String mAddress;

    @Override
    protected AddPropertyPresenter createPresenter() {
        return new AddPropertyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_property;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        topBarTvTitle.setText(getString(R.string.add_property_title));
        rec_hot_property = recLayout.getmRecyclerView();
        rec_hot_property.setLayoutManager(new WrapContentLinearLayoutManager(this));
        addOnClickListeners(R.id.property_manager_rl, R.id.my_property_rl,R.id.search_iv);
    }

    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mAddress = args.getString(ModuleServiceUtils.ADDRESS, "");
        }
        presenter.getList(selectContractAddressTv.getText().toString(),mAddress);
    }

    @Override
    protected void onDestroy() {
        //为避免每次添加都刷新首页积分数据，只有在添加资产之后再刷新
        if(isAdded)
            EventBus.getDefault().post(new RefreshPropertyBunBean());
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getListSuccess(List<HomeDataResultBean.FtBean> listBean) {
         recLayout.dismissEmptyView();
        if (adapter == null) {
            adapter = new HotPropertyAdapter(listBean,this);
            rec_hot_property.setAdapter(adapter);
        } else {
            adapter.setNewData(listBean);
        }
    }

    @Override
    public void getListFail(String msg) {
        recLayout.showEmptyView(0, 1, false);
    }


    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    /**
     * 获取数据库数据成功后更新列表的选中图标
     * @param ids
     */
    @Override
    public void updateList(List<Long> ids) {
        adapter.setIds(ids);
        adapter.notifyDataSetChanged();
    }

    private boolean isAdded = false;
    @Override
    public void addPropertyFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void addPropertySuccess() {
        isAdded = true;
    }


    @Override
    public void addProperty(HomeDataResultBean.FtBean item) {
        if (item != null) {
            presenter.addProperty(item, mAddress);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.my_property_rl) {
            ActivityUtil.startActivity(MyPropertyActivity.class, false);
        } else if (id == R.id.property_manager_rl) {
            Bundle args = new Bundle();
            args.putString(ModuleServiceUtils.ADDRESS,mAddress);
            ActivityUtil.startActivity(PropertyManagerActivity.class, false,args);
        } else if (id == R.id.search_iv){
            presenter.getList(selectContractAddressTv.getText().toString(),mAddress);
        }
    }

    /**
     * 刷新界面
     * @param refreshPropertyBunBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshDatas(RefreshPropertyBunBean refreshPropertyBunBean) {
        presenter.getList(selectContractAddressTv.getText().toString(),mAddress);
    }

}
