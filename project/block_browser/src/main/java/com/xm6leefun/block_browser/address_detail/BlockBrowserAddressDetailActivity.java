package com.xm6leefun.block_browser.address_detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;
import com.xm6leefun.block_browser.address_detail.adapter.BlockBrowserAddressNftAdapter;
import com.xm6leefun.block_browser.address_detail.adapter.BlockBrowserAddressScoreAdapter;

import com.xm6leefun.block_browser.address_detail.mvp.BlockBrowserAddressDetailContract;
import com.xm6leefun.block_browser.address_detail.mvp.BlockBrowserAddressDetailPresenter;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;


import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/19
 */
@Route(path = ModuleRouterTable.ADDRESS_DETAIL_PAGE)
public class BlockBrowserAddressDetailActivity extends BaseToolbarPresenterActivity<BlockBrowserAddressDetailPresenter> implements BlockBrowserAddressDetailContract.IView {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;

    @BindView(R2.id.score_rec)
    RecyclerView scoreRec;

    @BindView(R2.id.property_rec)
    RecyclerView propertyRec;

    @BindView(R2.id.tv_address)
    TextView tvAddress;

    private BlockBrowserAddressNftAdapter nftAdapter;

    private BlockBrowserAddressScoreAdapter scoreAdapter;


    private String mAddress;



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
    protected BlockBrowserAddressDetailPresenter createPresenter() {
        return new BlockBrowserAddressDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_block_browser_address_detail;
    }

    @Override
    protected void initView() {

        topBarTvTitle.setText(getString(R.string.block_browser));

        scoreRec.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        scoreRec.setLayoutManager(new WrapContentLinearLayoutManager(this));

        propertyRec.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        propertyRec.setLayoutManager(new WrapContentLinearLayoutManager(this));

        addOnClickListeners(R.id.tv_score_more,R.id.tv_property_more,R.id.search_iv);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            mAddress = extras.getString(ModuleServiceUtils.ADDRESS);

            tvAddress.setText(String.format(getString(R.string.address_title),mAddress));

            presenter.getNftList(mAddress);

            presenter.getScoreList(mAddress);


        }

    }



    @Override
    public void getScoreListSuccess(BlockBrowserSearchBean detailBean) {
        if (detailBean == null) return;

        if (detailBean.getBlockActionInfo() != null  &&
            detailBean.getBlockActionInfo().getBlockAddressList()!=null &&
            detailBean.getBlockActionInfo().getBlockAddressList().size() > 0){

            if (scoreAdapter == null){
                scoreAdapter = new BlockBrowserAddressScoreAdapter(detailBean.getBlockActionInfo().getBlockAddressList());
                scoreRec.setAdapter(scoreAdapter);
            }else{
                scoreAdapter.setNewData(detailBean.getBlockActionInfo().getBlockAddressList());
            }


        }

    }

    @Override
    public void getNftListSuccess(BlockBrowserSearchBean detailBean) {

        if (detailBean == null) return;

        if (detailBean.getBlockActionInfo() != null  &&
                detailBean.getBlockActionInfo().getBlockAddressList()!=null &&
                detailBean.getBlockActionInfo().getBlockAddressList().size() > 0){

            List<BlockBrowserSearchBean.NftInfoBean> nftInfoBeanList = new ArrayList<>();

            for (BlockBrowserSearchBean.BlockActionInfoBean.BlockAddressListBean bean : detailBean.getBlockActionInfo().getBlockAddressList()){
                if (bean.getNftInfo()!=null){
                    nftInfoBeanList.add(bean.getNftInfo());
                }
            }
            if (nftAdapter == null){
                nftAdapter = new BlockBrowserAddressNftAdapter(nftInfoBeanList);
                propertyRec.setAdapter(nftAdapter);
            }else{
                nftAdapter.setNewData(nftInfoBeanList);
            }

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
                    mAddress = searchBean.getBlockAddressInfo().getAddress();

                    tvAddress.setText(String.format(getString(R.string.address_title),mAddress));

                    presenter.getNftList(mAddress);

                    presenter.getScoreList(mAddress);


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
        if (id == R.id.tv_score_more) {
            ModuleServiceUtils.navigateAddressDetailScoreMorePage(mAddress);
        }else if (id == R.id.tv_property_more){
            ModuleServiceUtils.navigateAddressDetailNFTMorePage(mAddress);
        }else if (id == R.id.search_iv){
            if (!TextUtils.isEmpty(selectContractAddressTv.getText())){
                presenter.search(selectContractAddressTv.getText().toString());
            }
        }
    }


}
