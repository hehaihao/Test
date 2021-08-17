package com.xm6leefun.block_browser.trade_detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;
import com.xm6leefun.block_browser.address_detail.BlockBrowserAddressDetailActivity;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.product_detail.ProductDetailActivity;
import com.xm6leefun.block_browser.trade_detail.bean.BlockBrowserTradeDetailBean;
import com.xm6leefun.block_browser.trade_detail.mvp.BlockBrowserTradeDetailContract;
import com.xm6leefun.block_browser.trade_detail.mvp.BlockBrowserTradeDetailPresenter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.dialog.TipsDialog;
import com.xm6leefun.common.dialog.UpdateDialogFragment;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;

import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */


@Route(path = ModuleRouterTable.TRADE_DETAIL_PAGE)
public class BlockBrowserTradeDetailActivity extends BaseToolbarPresenterActivity<BlockBrowserTradeDetailPresenter> implements BlockBrowserTradeDetailContract.IView {

    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;
    @BindView(R2.id.tv_trade_hash)
    TextView tvTradeHash;
    @BindView(R2.id.tv_time)
    TextView tvTime;
    @BindView(R2.id.tv_types)
    TextView tvTypes;
    @BindView(R2.id.tv_height)
    TextView tvHeight;
    @BindView(R2.id.tv_coin)
    TextView tvCoin;
    @BindView(R2.id.tv_total)
    TextView tvTotal;
    @BindView(R2.id.tv_service_charge)
    TextView tvServiceCharge;
    @BindView(R2.id.tv_send_hash)
    TextView tvSendHash;
    @BindView(R2.id.tv_receive_hash)
    TextView tvReceiveHash;
    @BindView(R2.id.tv_product_name)
    TextView tvProductName;
    @BindView(R2.id.tv_token)
    TextView tvToken;
    @BindView(R2.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R2.id.tv_introduce)
    TextView tvIntroduce;

    @BindView(R2.id.ll_product)
    LinearLayout llProduct;



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


    private String mHash;

    private long mGoodId;


    @Override
    protected BlockBrowserTradeDetailPresenter createPresenter() {
        return new BlockBrowserTradeDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_block_browser_trade_detail;
    }

    @Override
    protected void initView() {

        topBarTvTitle.setText(getString(R.string.block_browser));

        addOnClickListeners(R.id.ll_product,R.id.search_iv,R.id.rl_receiver_address,R.id.rl_send_address);
    }

    @Override
    protected void initData(){

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            mHash = extras.getString(ModuleServiceUtils.HASH);
            presenter.getDetail(mHash);
        }
    }


    @Override
    public void getDetail(BlockBrowserSearchBean detailBean) {
        if (detailBean == null){
            showTipDialog();
            return;
        }

        BlockBrowserSearchBean.ActionHashInfoBean hashInfoBean = detailBean.getActionHashInfo();

        if (hashInfoBean == null){
            showTipDialog();
            return;
        }

        tvTradeHash.setText(hashInfoBean.getAction());

        tvTime.setText(hashInfoBean.getCreatedTime());

        tvTypes.setText(hashInfoBean.getActionName());

        tvHeight.setText(hashInfoBean.getBlockHeight() + "");

        tvCoin.setText(hashInfoBean.getCoin());

        tvTotal.setText(hashInfoBean.getActionTotal());

        tvServiceCharge.setText(hashInfoBean.getCost());

        tvSendHash.setText(hashInfoBean.getSendAddress());

        //取第一个值
        if (hashInfoBean.getReceiveAddressList() != null &&
            hashInfoBean.getReceiveAddressList().size() >0 ){
            tvReceiveHash.setText(hashInfoBean.getReceiveAddressList().get(0).getReceiveAddress());
        }

        if (detailBean.getNftInfo() != null && detailBean.getNftInfo().getId() != 0){
            llProduct.setVisibility(View.VISIBLE);
            mGoodId = detailBean.getNftInfo().getId();
            tvProductName.setText(detailBean.getNftInfo().getNftName());
            if (detailBean.getNftInfo().getNftImg() != null && detailBean.getNftInfo().getNftImg().size() > 0) {
                ImageLoader.loadRadiusImage(this, detailBean.getNftInfo().getNftImg().get(0), ivPhoto);
            }
            tvToken.setText(detailBean.getNftInfo().getId() + "");
            tvIntroduce.setText(detailBean.getNftInfo().getNftIntroduce());
        }else {
            llProduct.setVisibility(View.GONE);
        }

    }

    @Override
    public void getSearchResult(BlockBrowserSearchBean searchBean) {
        if (searchBean == null )  return;

        switch (searchBean.getDetailType()){
            //交易详情
            case TRADE_NFT_DETAIL:
            case TRADE_SCORE_DETAIL:
                getDetail(searchBean);
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
        showTipDialog();

    }

    private void showTipDialog() {
        TradeTipDialogFragment
                .newInstance()
                .show(mFragmentManager, TradeTipDialogFragment.TAG);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.ll_product){
            ModuleServiceUtils.navigatePhysicalDetailPage(mGoodId,true);
        } else if (id == R.id.search_iv){
            if (!TextUtils.isEmpty(selectContractAddressTv.getText())){
                presenter.search(selectContractAddressTv.getText().toString());
            }
        }else if(id == R.id.rl_send_address){
            if (!TextUtils.isEmpty(tvSendHash.getText().toString())){
                ModuleServiceUtils.navigateAddressDetailPage(tvSendHash.getText().toString());
            }

        }else if(id == R.id.rl_receiver_address){
            if (!TextUtils.isEmpty(tvReceiveHash.getText().toString())){
                ModuleServiceUtils.navigateAddressDetailPage(tvReceiveHash.getText().toString());
            }
        }
    }
}
