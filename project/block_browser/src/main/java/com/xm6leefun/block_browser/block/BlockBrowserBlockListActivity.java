package com.xm6leefun.block_browser.block;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;
import com.xm6leefun.block_browser.address_detail.BlockBrowserAddressDetailActivity;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.adapter.BlockBrowserBlockAdapter;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;
import com.xm6leefun.block_browser.block.mvp.BlockBrowserBlockListContract;
import com.xm6leefun.block_browser.block.mvp.BlockBrowserBlockListPresenter;
import com.xm6leefun.block_browser.block_detail.BlockBrowserBlockDetailActivity;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.export_module.ModuleServiceUtils;


import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/14
 */
public class BlockBrowserBlockListActivity extends BaseToolbarPresenterActivity<BlockBrowserBlockListPresenter> implements BlockBrowserBlockListContract.IView, BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;

    @BindView(R2.id.tv_current_page)
    TextView tvCurrentPage;

    @BindView(R2.id.tv_total_page)
    TextView tvTotalPage;

    @BindView(R2.id.block_rec)
    EmptyDataRecyclerView block_rec_layout;

    private RecyclerView block_rec;

    private long current_page = 1 ;

    public static final long LIMIT = 10;

    private long total_page = 1;



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
    //区块详细（nft)
    public static final int BLOCK_NFT_DETAIL = 5;

    private BlockBrowserBlockAdapter adapter;


    @Override
    protected BlockBrowserBlockListPresenter createPresenter() {
        return new BlockBrowserBlockListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_block_browser_block_list;
    }

    @Override
    protected void initView() {

        topBarTvTitle.setText(getString(R.string.block_browser));

        tvCurrentPage.setText( current_page + "");

        tvTotalPage.setText(total_page + "");

        block_rec = block_rec_layout.getmRecyclerView();
        block_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));

        addOnClickListeners(R.id.iv_first,R.id.iv_pre,R.id.iv_next,R.id.iv_last,R.id.search_iv);
    }

    @Override
    protected void initData() { presenter.getList(LIMIT,current_page); }

    @Override
    public void getListSuccess(BlockBrowserBlockBean blockBean) {
        if (blockBean == null) return;

        if (blockBean.getPageInfo() != null && blockBean.getPageInfo().getPageSize() != 0) {

            long pageSize = blockBean.getPageInfo().getPageSize();

            long totalNum = blockBean.getPageInfo().getTotalNum();

            total_page = totalNum%pageSize == 0 ? totalNum/pageSize : totalNum/pageSize + 1;

            tvTotalPage.setText(total_page + "");
        }

        if (adapter == null){
            adapter = new BlockBrowserBlockAdapter(blockBean.getRecordList());
            adapter.setOnItemClickListener(this);
            block_rec.setAdapter(adapter);

        }else{
            adapter.setNewData(blockBean.getRecordList());
        }


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
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.iv_first){
             pageFirst();
        } else if(id == R.id.iv_pre){
             pagePre();
        } else if(id == R.id.iv_next){
             pageNext();
        } else if(id == R.id.iv_last){
            pageLast();
        } else if (id== R.id.search_iv){
            if (!TextUtils.isEmpty(selectContractAddressTv.getText())){
                presenter.search(selectContractAddressTv.getText().toString());
            }
        }
    }



    private void pageFirst() {
        if (current_page == 1) {
            ToastUtil.showCenterToast(getString(R.string.already_first_page));
            return;
        }
        current_page = 1;
        tvCurrentPage.setText( current_page + "");
        presenter.getList(LIMIT,current_page);
    }


    private void pagePre() {
        if (current_page == 1) {
            ToastUtil.showCenterToast(getString(R.string.already_first_page));
            return;
        }
        current_page--;
        tvCurrentPage.setText( current_page + "");
        presenter.getList(LIMIT,current_page);
    }


    private void pageNext() {
        if (current_page == total_page) {
            ToastUtil.showCenterToast(getString(R.string.already_last_page));
            return;
        }
        current_page++;
        tvCurrentPage.setText( current_page + "");
        presenter.getList(LIMIT,current_page);
    }

    private void pageLast() {
        if (current_page == total_page) {
            ToastUtil.showCenterToast(getString(R.string.already_last_page));
            return;
        }
        current_page = total_page;
        tvCurrentPage.setText( current_page + "");
        presenter.getList(LIMIT,current_page);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BlockBrowserBlockListBean bean = (BlockBrowserBlockListBean) adapter.getData().get(position);
        if (bean != null){
            ModuleServiceUtils.navigateBlockDetailPage(bean.getBlockHeight());
        }

    }
}
