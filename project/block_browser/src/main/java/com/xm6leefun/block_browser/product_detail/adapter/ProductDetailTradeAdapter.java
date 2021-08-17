package com.xm6leefun.block_browser.product_detail.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;

import com.xm6leefun.block_browser.product_detail.bean.ProductTradeBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/19
 */
public class ProductDetailTradeAdapter  extends BaseQuickAdapter<ProductTradeBean, BaseViewHolder> {


    public ProductDetailTradeAdapter(@Nullable List<ProductTradeBean> data) {
        super(R.layout.list_item_product_trade_record, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, ProductTradeBean item) {
        helper.setText(R.id.tv_event,item.getEvent() + "");


        helper.setText(R.id.tv_origin_address,item.getOrigin_address() + "");

        helper.setText(R.id.tv_to_address,item.getTo_address() + "");
        helper.setText(R.id.tv_create_time,item.getTime());

    }

}
