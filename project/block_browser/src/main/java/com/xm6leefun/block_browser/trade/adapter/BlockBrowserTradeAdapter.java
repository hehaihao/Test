package com.xm6leefun.block_browser.trade.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;

import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeListBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserTradeAdapter extends BaseQuickAdapter<BlockBrowserTradeListBean, BaseViewHolder> {


    public BlockBrowserTradeAdapter(@Nullable List<BlockBrowserTradeListBean> data) {
        super(R.layout.list_item_trade, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserTradeListBean item) {

        helper.setText(R.id.tv_trade_hash,item.getAction());

        helper.setText(R.id.tv_trade_type,item.getActionTypeName());

        helper.setText(R.id.tv_create_time,item.getCreatedTime());

}

}
