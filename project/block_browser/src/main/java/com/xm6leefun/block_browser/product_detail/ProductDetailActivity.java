package com.xm6leefun.block_browser.product_detail;



import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.R2;
import com.xm6leefun.block_browser.product_detail.adapter.ProductDetailTradeAdapter;
import com.xm6leefun.block_browser.product_detail.bean.ProductDetailBean;
import com.xm6leefun.block_browser.product_detail.mvp.ProductDetailContract;
import com.xm6leefun.block_browser.product_detail.mvp.ProductDetailPresenter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ImageLoader;


import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */

public class ProductDetailActivity extends BaseToolbarPresenterActivity<ProductDetailPresenter> implements ProductDetailContract.IView {

    @BindView(R2.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R2.id.tv_all_address)
    TextView tvAllAddress;
    @BindView(R2.id.tv_ruler_hash)
    TextView tvRulerHash;
    @BindView(R2.id.tv_touch)
    TextView tvTouch;
    @BindView(R2.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R2.id.rv_trade)
    RecyclerView rvTrade;
    @BindView(R2.id.tv_product_name)
    TextView tvProductName;


    private ProductDetailTradeAdapter adapter;



    @Override
    protected ProductDetailPresenter createPresenter() {
        return presenter = new ProductDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(getString(R.string.block_browser));
        rvTrade.setLayoutManager(new WrapContentLinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        presenter.getDetail();
    }

    @Override
    public void getDetail(ProductDetailBean detailBean) {
        if (detailBean==null) return;

        tvProductName.setText(detailBean.getProduct_name());

        tvAllAddress.setText(detailBean.getAll_address());

        tvRulerHash.setText(detailBean.getRuler_address());

        tvTouch.setText(detailBean.getToken_id());

        tvIntroduce.setText(detailBean.getProduct_introduce());


        ImageLoader.loadRadiusImage(this,detailBean.getPhoto_url(),18,ivPhoto);

        if (adapter == null){
            adapter = new ProductDetailTradeAdapter(detailBean.getTradeBeanList());
            rvTrade.setAdapter(adapter);
        }else{
            adapter.setNewData(detailBean.getTradeBeanList());
        }




    }

    @Override
    public void onLoadFail(String msg) {

    }

}
