package com.xm6leefun.physical_module.record;

import android.os.Bundle;
import android.view.View;
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
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;
import com.xm6leefun.physical_module.record.adapter.RecordListAdapter;
import com.xm6leefun.physical_module.record.mvp.PhysicalRecordResultBean;
import com.xm6leefun.physical_module.record.mvp.RecordListContract;
import com.xm6leefun.physical_module.record.mvp.RecordListPresenter;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/23 9:59
 */
@Route(path = ModuleRouterTable.PHYSICAL_RECORD_LIST_PAGE)
public class RecordListActivity extends BaseToolbarPresenterActivity<RecordListPresenter> implements RecordListContract.IView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.record_rec_layout)
    EmptyDataRecyclerView record_rec_layout;
    private RecyclerView record_rec;
    private RecordListAdapter adapter;
    private String mCurrAddress = "";

    @Override
    protected RecordListPresenter createPresenter() {
        return new RecordListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_list_layout;
    }

    @Override
    protected void initView() {
        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setFooterHeight(0);
        ultra_pull.setOnRefreshListener(this);
        ultra_pull.setOnLoadMoreListener(this);
        record_rec = record_rec_layout.getmRecyclerView();
        record_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));
    }

    private int page = 1;
    private final int limit = 20;
    @Override
    protected void initData() {
        topBarTvTitle.setText(R.string.physical_tran_record_title);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mCurrAddress = extras.getString(ModuleServiceUtils.ADDRESS,"");
            presenter.getRecordList(false,page,limit,mCurrAddress);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();

    }

    @Override
    public void getRecordListSuccess(PhysicalRecordResultBean recordResultBean, boolean isLoadMore) {
        List<PhysicalRecordResultBean.ListBean> recordListBeans = recordResultBean.getList();
        if(recordListBeans != null && recordListBeans.size() > 0) {
            record_rec_layout.showEmptyView(recordListBeans.size(),page++,false);
            if (adapter == null) {
                adapter = new RecordListAdapter(R.layout.item_list_physical_record, recordListBeans);
                adapter.setOnItemClickListener(this);
                record_rec.setAdapter(adapter);
            } else {
                if (isLoadMore) adapter.addData(recordListBeans);
                else adapter.replaceData(recordListBeans);
            }
        }else{
            record_rec_layout.showEmptyView(0,page,false);
        }
        ultra_pull.setEnableLoadMore(adapter != null && adapter.getData().size() < recordResultBean.getPageInfo().getTotalNum());
        finishLoad();
    }

    @Override
    public void onLoadFail(String msg) {
        record_rec_layout.showEmptyView(0,page,false);
        finishLoad();
    }

    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PhysicalRecordResultBean.ListBean listBean = (PhysicalRecordResultBean.ListBean) adapter.getData().get(position);

        if (listBean != null) {
            ModuleServiceUtils.navigateTradeDetailPage(listBean.getHash());
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.getRecordList(true,page,limit,mCurrAddress);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        presenter.getRecordList(false,page,limit,mCurrAddress);
    }
}
