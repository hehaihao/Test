package com.xm6leefun.block_browser.block_detail;


import android.content.ClipboardManager;
import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block_detail.adapter.BlockBrowserBlockActionAdapter;
import com.xm6leefun.block_browser.block_detail.mvp.BlockBrowserBlockDetailContract;
import com.xm6leefun.block_browser.block_detail.mvp.BlockBrowserBlockDetailPresenter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;

import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */

@Route(path = ModuleRouterTable.BLOCK_DETAIL_PAGE)
public class BlockBrowserBlockDetailActivity extends BaseToolbarPresenterActivity<BlockBrowserBlockDetailPresenter> implements BlockBrowserBlockDetailContract.IView {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;
    @BindView(R2.id.tv_height)
    TextView tvHeight;
    @BindView(R2.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R2.id.tv_block_hash)
    TextView tvBlockHash;
    @BindView(R2.id.tv_trade_number)
    TextView tvTradeNumber;
    @BindView(R2.id.tv_block_size)
    TextView tvBlockSize;
    @BindView(R2.id.tv_types)
    TextView tvTypes;
    @BindView(R2.id.tv_trade_hash)
    TextView tvTradeHash;
    @BindView(R2.id.tv_time)
    TextView tvTime;
    @BindView(R2.id.tv_product_name)
    TextView tvProductName;
    @BindView(R2.id.tv_token)
    TextView tvToken;
    @BindView(R2.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R2.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R2.id.tv_ruler_hash)
    TextView tvRulerHash;
    @BindView(R2.id.tv_send_hash)
    TextView tvSendHash;
    @BindView(R2.id.tv_receive_hash)
    TextView tvReceiveHash;
    @BindView(R2.id.search_iv)
    ImageView searchIv;
    @BindView(R2.id.btn_pre)
    Button btnPre;
    @BindView(R2.id.btn_next)
    Button btnNext;

    @BindView(R2.id.ll_trade)
    LinearLayout llTrade;

    @BindView(R2.id.block_rec_layout)
    EmptyDataRecyclerView blockRecLayout;

    RecyclerView blockRec;


    @BindView(R2.id.ll_trade_list)
    LinearLayout llTradeList;


    public int mHeight;


    private BlockBrowserBlockActionAdapter adapter;

    //NFT区块详情
    public static final int NFT_DETAIL = 5;

    //积分区块详情
    public static final int SCORE_DETAIL = 6;



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
    protected BlockBrowserBlockDetailPresenter createPresenter() {
        return new BlockBrowserBlockDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_block_browser_block_detail;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(getString(R.string.block_browser));

        blockRec = blockRecLayout.getmRecyclerView();

        blockRec.setLayoutManager(new WrapContentLinearLayoutManager(this));

        addOnClickListeners(R.id.btn_pre, R.id.btn_next, R.id.search_iv,R.id.tv_block_hash);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            mHeight = extras.getInt(ModuleServiceUtils.HEIGHT);
            presenter.getDetail(mHeight);
        }

    }


    @Override
    public void getDetail(BlockBrowserSearchBean detailBean) {

        if (detailBean == null) return;

        if (detailBean.getBlockHeightInfo() == null) return;

        mHeight = detailBean.getBlockHeightInfo().getBlockHeight();

        //积分资产 显示列表
         if (detailBean.getDetailType() == SCORE_DETAIL){
             llTrade.setVisibility(View.GONE);
             llTradeList.setVisibility(View.VISIBLE);
         }else{
             llTrade.setVisibility(View.VISIBLE);
             llTradeList.setVisibility(View.GONE);
         }

         tvHeight.setText(detailBean.getBlockHeightInfo().getBlockHeight() +"");

         tvCreateTime.setText(detailBean.getBlockHeightInfo().getCreatedTime());

         tvBlockHash.setText(detailBean.getBlockHeightInfo().getHeadHash());

         tvTradeNumber.setText(detailBean.getBlockHeightInfo().getTradingNum() + "");

         tvBlockSize.setText(detailBean.getBlockHeightInfo().getTradingSize() + "");

         tvTypes.setText(detailBean.getBlockHeightInfo().getTradingCoin());


        if (detailBean.getBlockActionInfo().getBlockActionList() == null ||
                detailBean.getBlockActionInfo().getBlockActionList().size() == 0){
            llTradeList.setVisibility(View.GONE);
            return;
        }


        if (adapter == null){
            adapter = new BlockBrowserBlockActionAdapter(detailBean.getBlockActionInfo().getBlockActionList());
            blockRec.setAdapter(adapter);
        }else {
            adapter.setNewData(detailBean.getBlockActionInfo().getBlockActionList());
        }


        if (detailBean.getNftInfo() == null){
            llTrade.setVisibility(View.GONE);
        }



        if (detailBean.getNftInfo() != null){

            tvTradeHash.setText(detailBean.getNftInfo().getTxid());

            tvTime.setText(detailBean.getNftInfo().getCreateTime());

             tvProductName.setText(detailBean.getNftInfo().getNftName());
             if (detailBean.getNftInfo().getNftImg() != null && detailBean.getNftInfo().getNftImg().size() > 0){
                 ImageLoader.loadRadiusImage(this,detailBean.getNftInfo().getNftImg().get(0),ivPhoto);
             }

             tvToken.setText(detailBean.getNftInfo().getId() + "");

             tvIntroduce.setText(detailBean.getNftInfo().getNftIntroduce());

             tvRulerHash.setText(detailBean.getNftInfo().getContractAddress());

             tvSendHash.setText(detailBean.getNftInfo().getOriginatorAddress());

             tvReceiveHash.setText(detailBean.getNftInfo().getReAddress());
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
                getDetail(searchBean);
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
        if (id == R.id.search_iv) {
            if (!TextUtils.isEmpty(selectContractAddressTv.getText())){
                presenter.search(selectContractAddressTv.getText().toString());
            }

        } else if (id == R.id.btn_pre){
            presenter.getDetail(mHeight - 1);
        } else if (id == R.id.btn_next){
            presenter.getDetail(mHeight + 1);
        } else if (id == R.id.tv_block_hash){
            if (!TextUtils.isEmpty(tvBlockHash.getText())){
                copyStrings(tvBlockHash.getText().toString());
            }
        }
    }


    /**
     * 复制字符串
     */
    protected void copyStrings(String string) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setText(string);
            ToastUtil.showCenterToast(getResources().getString(com.xm6leefun.common.R.string.copy_success));
        }
    }
}
