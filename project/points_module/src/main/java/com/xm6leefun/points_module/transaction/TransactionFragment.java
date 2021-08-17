package com.xm6leefun.points_module.transaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.fragment.BasePresenterFragment;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.transaction.adapter.TransactionAdapter;
import com.xm6leefun.points_module.transaction.mvp.TransactionContract;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;
import com.xm6leefun.points_module.transaction.mvp.TransactionListPresenter;
import com.xm6leefun.points_module.transaction.mvp.TransationResultBean;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 10:39
 */
public class TransactionFragment extends BasePresenterFragment<TransactionListPresenter> implements TransactionContract.IListView, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    private boolean isInitView;
    private boolean isVisible;

    public static final String TYPE = "type";
    public static final String NFT_ID = "nftId";
    public static final String ADDRESS = "address";
    public static final String CONTRACT_ADDRESS = "contractAddress";
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.transaction_rec_layout)
    EmptyDataRecyclerView transaction_rec_layout;
    private RecyclerView transaction_rec;
    private TransactionAdapter adapter;
    private int type;
    private String nftId = "";
    private String address = "";
    private String contractAddress = "";

    public static TransactionFragment getInstance(int type,String nftId,String address,String contractAddress){
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE,type);
        args.putString(NFT_ID,nftId);
        args.putString(ADDRESS,address);
        args.putString(CONTRACT_ADDRESS,contractAddress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible) {
            lazyLoadView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = getUserVisibleHint();
        if (isVisible) {
            lazyLoadView();
        }
    }

    @Override
    protected TransactionListPresenter createPresenter() {
        return new TransactionListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_layout;
    }

    @Override
    protected void initView() {
        isInitView = true;
        transaction_rec = transaction_rec_layout.getmRecyclerView();
        transaction_rec.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        ultra_pull.setRefreshHeader(new ClassicsHeader(getContext()));
        ultra_pull.setOnRefreshListener(this);
        ultra_pull.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args!=null){
            type = args.getInt(TYPE,0);
            nftId = args.getString(NFT_ID,"");
            address = args.getString(ADDRESS,"");
            contractAddress = args.getString(CONTRACT_ADDRESS,"");
        }
    }

    private int page = 1;
    private final int limit = 20;
    /**
     * 懒加载
     */
    private void lazyLoadView() {
        if (isInitView && isVisible) {
            isInitView = false;
            //do something
            presenter.getTransactionList(false,address,contractAddress,nftId,page,limit,type);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.getTransactionList(true,address,contractAddress,nftId,page,limit,type);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        presenter.getTransactionList(false,address,contractAddress,nftId,page,limit,type);
    }

    @Override
    public void getTransactionListSuccess(TransationResultBean transationResultBean, boolean isLoadMore) {
        List<TransactionListBean> listBeans = transationResultBean.getList();
        if(listBeans != null && listBeans.size() > 0) {
            transaction_rec_layout.showEmptyView(listBeans.size(),page++,false);
            if(adapter == null){
                adapter = new TransactionAdapter(R.layout.item_list_transaction,listBeans);
                adapter.setOnItemClickListener(this);
                transaction_rec.setAdapter(adapter);
            }else{
                if(isLoadMore){
                    adapter.addData(listBeans);
                }else{
                    adapter.setNewData(listBeans);
                }
            }
        }else{
            transaction_rec_layout.showEmptyView(0,page,false);
        }
        ultra_pull.setEnableLoadMore(adapter != null && adapter.getData().size() < transationResultBean.getPageInfo().getTotalNum());
        finishLoad();
    }

    @Override
    public void onLoadFail(String msg) {
        ultra_pull.setEnableLoadMore(page!=1);
        transaction_rec_layout.showEmptyView(0,page,false);
        finishLoad();
        ToastUtil.showCenterToast(msg);
    }

    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TransactionListBean transactionListBean = (TransactionListBean) adapter.getData().get(position);
        ModuleServiceUtils.navigateTransactionDetailPage(transactionListBean);
    }
}
