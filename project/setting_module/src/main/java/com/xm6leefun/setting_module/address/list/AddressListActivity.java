package com.xm6leefun.setting_module.address.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.AddressBook;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.SelectAddressBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.R2;
import com.xm6leefun.setting_module.address.edit.AddressEditActivity;
import com.xm6leefun.setting_module.address.edit.mvp.RefreshAddressBusBean;
import com.xm6leefun.setting_module.address.list.adapter.AddressListAdapter;
import com.xm6leefun.setting_module.address.list.mvp.AddressListContract;
import com.xm6leefun.setting_module.address.list.mvp.AddressListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:地址本
 * @Author: hhh
 * @CreateDate: 2021/4/2 11:01
 */
@Route(path = ModuleRouterTable.ADDRESS_LIST_PAGE)
public class AddressListActivity extends BaseToolbarPresenterActivity<AddressListPresenter> implements AddressListContract.IView, OnRefreshListener, AddressListAdapter.CopyAddressListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.address_rec_layout)
    EmptyDataRecyclerView address_rec_layout;
    RecyclerView address_rec;
    private AddressListAdapter adapter;

    @Override
    protected AddressListPresenter createPresenter() {
        return new AddressListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list_layout;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        topBarTvTitle.setText(R.string.address_list_title);
        topBarIvRight.setVisibility(View.VISIBLE);
        topBarIvRight.setImageResource(R.mipmap.address_add_icon);
        topBarTvRight.setVisibility(View.GONE);
        addOnClickListeners(R.id.base_topBar_iv_right);
        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setOnRefreshListener(this);
        address_rec = address_rec_layout.getmRecyclerView();
        address_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        address_rec.addItemDecoration(new SpacesItemDecoration(20));
    }

    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            isSelect = args.getBoolean(ModuleServiceUtils.IS_SELECT_ADDRESS,false);
        }
        presenter.getAddressList();
    }

    @Override
    public void getAddressListSuccess(List<AddressBook> addressBooks) {
        ultra_pull.finishRefresh();
        if(addressBooks != null && addressBooks.size() > 0) {
            address_rec_layout.showEmptyView(addressBooks.size(),1,false);
            if (adapter == null) {
                adapter = new AddressListAdapter(R.layout.item_list_address, addressBooks, this);
                adapter.setOnItemClickListener(this);
                address_rec.setAdapter(adapter);
            } else {
                adapter.setNewData(addressBooks);
            }
        }else{
            address_rec_layout.showEmptyView(0,1,false);
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ultra_pull.finishRefresh();
        address_rec_layout.showEmptyView(0,1,false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getAddressList();
    }

    @Override
    public void copy(String address) {
        copyStrings(address);
    }

    private boolean isSelect = false;//选择地址
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AddressBook addressBook = this.adapter.getData().get(position);
        if(isSelect){//选择地址
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_ADDRESS
                    , new SelectAddressBean(addressBook.getAddress(),addressBook.getPerson()));
            setResult(ModuleServiceUtils.RESULT_CODE_SELECT_ADDRESS, resultIntent);
            ActivityUtil.finishActivity(this);
        }else {//修改地址信息
            ModuleServiceUtils.navigateEditAddressPage(addressBook);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.base_topBar_iv_right){
            ModuleServiceUtils.navigateEditAddressPage(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新地址本数据
     * @param refreshAddressBusBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddressData(RefreshAddressBusBean refreshAddressBusBean){
        presenter.getAddressList();
    }
}
