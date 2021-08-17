package com.xm6leefun.block_browser.address_detail.property;

import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;

import com.xm6leefun.block_browser.address_detail.adapter.BlockBrowserAddressNftAdapter;

import com.xm6leefun.block_browser.address_detail.property.mvp.BlockBrowserPropertyContract;
import com.xm6leefun.block_browser.address_detail.property.mvp.BlockBrowserPropertyPresenter;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



@Route(path = ModuleRouterTable.ADDRESS_DETAIL_NFT_PAGE)
public class BlockBrowserPropertyListActivity extends BaseToolbarPresenterActivity<BlockBrowserPropertyPresenter> implements BlockBrowserPropertyContract.IView, OnRefreshListener, OnLoadMoreListener {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;
    @BindView(R2.id.tv_address)
    TextView tvAddress;



    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout ultra_pull;

    @BindView(R2.id.recycler)
    EmptyDataRecyclerView rec_layout;

    RecyclerView recycler;

    private BlockBrowserAddressNftAdapter nftAdapter;


    private String  mAddress;


    private int page = 1;
    private final int limit = 10;



    //交易详细(积分
    public static final int TRADE_SCORE_DETAIL = 1 ;
    //交易详细(nft)
    public static final int TRADE_NFT_DETAIL = 2;

    //地址详细(积分）
    public static final int ADDRESS_SCORE_DETAIL = 3 ;
    //地址详细(nft)
    public static final int ADDRESS_NFT_DETAIL = 4;

    //区块详细（积分）
    public static final int BLOCK_SCORE_DETAIL = 6 ;
    // 区块详细（nft)
    public static final int BLOCK_NFT_DETAIL = 5;

    @Override
    protected BlockBrowserPropertyPresenter createPresenter() {
        return new BlockBrowserPropertyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_more;
    }

    @Override
    protected void initView() {

        topBarTvTitle.setText(getString(R.string.block_browser));


        addOnClickListeners(R.id.search_iv);

        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setFooterHeight(0);
        ultra_pull.setOnRefreshListener(this);
        ultra_pull.setOnLoadMoreListener(this);
        recycler = rec_layout.getmRecyclerView();
        recycler.setLayoutManager(new WrapContentLinearLayoutManager(this));
        recycler.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        recycler.setLayoutManager(new WrapContentLinearLayoutManager(this));


    }

    @Override
    protected void initData(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mAddress = extras.getString(ModuleServiceUtils.ADDRESS,"");

            tvAddress.setText(String.format(getString(R.string.address_title),mAddress));

            presenter.getList(false,mAddress,limit,page);
        }
    }


    @Override
    public void getListSuccess(BlockBrowserSearchBean bean, boolean isLoadMore) {

        if (bean == null) {
            rec_layout.showEmptyView(0,page,false);
            ultra_pull.setEnableLoadMore(false);
            finishLoad();
            return;
        }



        if (bean.getBlockActionInfo() != null  &&
                bean.getBlockActionInfo().getBlockAddressList()!=null &&
                bean.getBlockActionInfo().getBlockAddressList().size() > 0){


            List<BlockBrowserSearchBean.NftInfoBean> nftInfoBeanList = new ArrayList<>();

            for (BlockBrowserSearchBean.BlockActionInfoBean.BlockAddressListBean listBean : bean.getBlockActionInfo().getBlockAddressList()){
                if (bean.getNftInfo()!=null){
                    nftInfoBeanList.add(listBean.getNftInfo());
                }
            }

            if (nftAdapter == null){
                nftAdapter = new BlockBrowserAddressNftAdapter(nftInfoBeanList);
                recycler.setAdapter(nftAdapter);
            }else{
                if (isLoadMore) nftAdapter.addData(nftInfoBeanList);
                else nftAdapter.replaceData(nftInfoBeanList);
            }

        }else{
            rec_layout.showEmptyView(0,page,false);
        }
        ultra_pull.setEnableLoadMore(nftAdapter != null && nftAdapter.getData().size() < bean.getBlockActionInfo().getBlockActionPage().getTotalNum());
        finishLoad();

    }


    @Override
    public void getSearchResult(BlockBrowserSearchBean searchBean) {
        if (searchBean == null )  return;

        switch (searchBean.getDetailType()){
            //交易详情
            case TRADE_NFT_DETAIL:
            case TRADE_SCORE_DETAIL:
                if (searchBean.getActionHashInfo()!=null){
                    ModuleServiceUtils.navigateTradeDetailPage(searchBean.getActionHashInfo().getAction());
                }
                break;
            //地址详细
            case ADDRESS_SCORE_DETAIL:
            case ADDRESS_NFT_DETAIL:
                if (searchBean.getBlockAddressInfo()!=null){
                    ModuleServiceUtils.navigateAddressDetailPage(searchBean.getBlockAddressInfo().getAddress());
                }
                break;
            //区块详细
            case BLOCK_NFT_DETAIL:
            case BLOCK_SCORE_DETAIL:
                if (searchBean.getBlockHeightInfo()!=null){
                    ModuleServiceUtils.navigateBlockDetailPage(searchBean.getBlockHeightInfo().getBlockHeight());
                }
                break;
        }



    }

    @Override
    public void getSearchResultEmpty() {
        ToastUtil.showCenterToast(getString(R.string.search_result_empty_str));
    }

    @Override
    public void onLoadFail(String msg) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.getList(true,mAddress,limit,page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        presenter.getList(false,mAddress,limit,page);

    }
    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }
}
