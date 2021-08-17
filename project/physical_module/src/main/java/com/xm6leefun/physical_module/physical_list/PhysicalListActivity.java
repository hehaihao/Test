package com.xm6leefun.physical_module.physical_list;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;
import com.xm6leefun.physical_module.physical_list.adapter.PhysicalListAdapter;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListBean;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListContract;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/23 9:59
 */
@Route(path = ModuleRouterTable.PHYSICAL_LIST_PAGE)
public class PhysicalListActivity extends BaseToolbarPresenterActivity<PhysicalListPresenter> implements PhysicalListContract.IView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.contract_address_tv)
    TextView contract_address_tv;
    @BindView(R2.id.search_et)
    EditText search_et;
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.physical_rec_layout)
    EmptyDataRecyclerView physical_rec_layout;
    private RecyclerView physical_rec;
    private PhysicalListAdapter adapter;

    @Override
    protected PhysicalListPresenter createPresenter() {
        return new PhysicalListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_physical_list_layout;
    }

    @Override
    protected void initView() {
        addOnClickListeners(R.id.search_img);
        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setFooterHeight(0);
        ultra_pull.setOnRefreshListener(this);
        physical_rec = physical_rec_layout.getmRecyclerView();
        physical_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));
    }

    private HomeDataResultBean.NftBean mNftBean;
    private String mCurrAddress = "";
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mNftBean = extras.getParcelable(ModuleServiceUtils.DATA);
            mCurrAddress = extras.getString(ModuleServiceUtils.ADDRESS,"");
            presenter.getPhysicalList(false,mCurrAddress,mNftBean.getContract_address(),"");
            dealData(mNftBean);
        }
    }

    private void dealData(HomeDataResultBean.NftBean mNftBean) {
        contract_address_tv.setText(mNftBean.getContract_address());
        topBarTvTitle.setText(mNftBean.getChain_name());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.search_img){
            presenter.getPhysicalList(false,mCurrAddress,mNftBean.getContract_address(),search_et.getText().toString().trim());
        }
    }

    @Override
    public void getPhysicalListSuccess(List<PhysicalListBean> physicalListBeans,boolean isLoadMore) {
        if(physicalListBeans != null && physicalListBeans.size() > 0) {
            physical_rec_layout.showEmptyView(0,physicalListBeans.size(),false);
            if (adapter == null) {
                adapter = new PhysicalListAdapter(this, R.layout.item_list_physical, physicalListBeans);
                adapter.setOnItemClickListener(this);
                physical_rec.setAdapter(adapter);
            } else {
                if (isLoadMore) adapter.addData(physicalListBeans);
                else adapter.setNewData(physicalListBeans);
            }
        }else{
            physical_rec_layout.showEmptyView(0,1,false);
        }
        finishLoad();
    }

    @Override
    public void onLoadFail(String msg) {
        finishLoad();
        physical_rec_layout.showEmptyView(0,1,false);
    }

    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PhysicalListBean physicalListBean = (PhysicalListBean) adapter.getData().get(position);
        ModuleServiceUtils.navigatePhysicalDetailPage(physicalListBean.getId());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.getPhysicalList(true,mCurrAddress,mNftBean.getContract_address(),search_et.getText().toString().trim());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getPhysicalList(false,mCurrAddress,mNftBean.getContract_address(),search_et.getText().toString().trim());
    }
}
