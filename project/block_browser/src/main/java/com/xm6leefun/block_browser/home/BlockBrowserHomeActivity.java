package com.xm6leefun.block_browser.home;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.BlockBrowserBlockListActivity;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;
import com.xm6leefun.block_browser.home.adapter.HomeBlockAdapter;
import com.xm6leefun.block_browser.home.adapter.HomeTradeAdapter;
import com.xm6leefun.block_browser.home.bean.BlockBrowserHomeBean;
import com.xm6leefun.block_browser.home.mvp.BlockBrowserHomeContract;
import com.xm6leefun.block_browser.home.mvp.BlockBrowserHomePresenter;
import com.xm6leefun.block_browser.trade.BlockBrowserTradeListActivity;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeListBean;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;

import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;


import java.util.List;

import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/14
 */
@Route(path = ModuleRouterTable.BLOCK_BROWSER_PAGE)
public class BlockBrowserHomeActivity extends BaseToolbarPresenterActivity<BlockBrowserHomePresenter> implements BlockBrowserHomeContract.IView,HomeTradeAdapter.BlockBrowserTradeAddressListener,HomeBlockAdapter.BlockBrowserBlockListener{

    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;

    @BindView(R2.id.tv_property)
    TextView tvProperty;

    @BindView(R2.id.tv_types)
    TextView tvTypes;

    @BindView(R2.id.tv_height)
    TextView tvHeight;

    @BindView(R2.id.tv_trade)
    TextView tvTrade;


    @BindView(R2.id.block_rec)
    RecyclerView blockRec;

    @BindView(R2.id.trade_rec)
    RecyclerView tradeRec;


    private HomeBlockAdapter mHomeBlockAdapter;

    private HomeTradeAdapter mHomeTradeAdapter;




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
    protected BlockBrowserHomePresenter createPresenter() {
        return new BlockBrowserHomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_block_browser_home;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(getString(R.string.block_browser));
        blockRec.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        blockRec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        tradeRec.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        tradeRec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        addOnClickListeners(R.id.tv_block_more,R.id.tv_trade_more,R.id.search_iv);
    }

    @Override
    protected void initData() {
        presenter.getBlockList();
        presenter.getTradeList();
        presenter.getStatics();
    }


    @Override
    public void getStaticsSuccess(BlockBrowserHomeBean homeStaticsBean) {
        if (homeStaticsBean== null) return;

        tvProperty.setText(homeStaticsBean.getNftTotal() + "");

        tvHeight.setText(homeStaticsBean.getBlockTotal() + "");

        tvTypes.setText(homeStaticsBean.getFtTotal() + "");

        tvTrade.setText(homeStaticsBean.getActionTotal() + "");
    }

    @Override
    public void getBlockListSuccess(List<BlockBrowserBlockListBean>  blockListBeans) {
        if (mHomeBlockAdapter == null){
            mHomeBlockAdapter = new HomeBlockAdapter(blockListBeans,this);
            blockRec.setAdapter(mHomeBlockAdapter);
        }else{
            mHomeBlockAdapter.setNewData(blockListBeans);
        }


    }

    @Override
    public void getTradeListSuccess(List<BlockBrowserTradeListBean>  tradeListBeans) {
        if (mHomeTradeAdapter == null){
            mHomeTradeAdapter = new HomeTradeAdapter(tradeListBeans,this);
            tradeRec.setAdapter(mHomeTradeAdapter);
        }else{
            mHomeTradeAdapter.setNewData(tradeListBeans);
        }
    }

    /**
     * 搜索列表
     * @param searchBean
     */
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
        if (id == R.id.tv_block_more) {
            ActivityUtil.startActivity(BlockBrowserBlockListActivity.class,false);
        }else if (id == R.id.tv_trade_more){
            ActivityUtil.startActivity(BlockBrowserTradeListActivity.class,false);
        }else if (id == R.id.search_iv){
            if (!TextUtils.isEmpty(selectContractAddressTv.getText())){
                presenter.search(selectContractAddressTv.getText().toString());
            }

        }
    }

    @Override
    public void selectAddress(String address) {
        ModuleServiceUtils.navigateAddressDetailPage(address);
    }

    @Override
    public void selectHeight(int height) {
        ModuleServiceUtils.navigateBlockDetailPage(height);
    }

    @Override
    public void selectHash(String hash) {
        ModuleServiceUtils.navigateTradeDetailPage(hash);
    }
}
