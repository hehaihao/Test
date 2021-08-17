package com.xm6leefun.block_browser.block.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;


import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserBlockAdapter extends BaseQuickAdapter<BlockBrowserBlockListBean, BaseViewHolder> {


    public BlockBrowserBlockAdapter(@Nullable List<BlockBrowserBlockListBean> data) {
        super(R.layout.list_item_block, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserBlockListBean item) {
        helper.setText(R.id.tv_block_height,item.getBlockHeight() + "");
        helper.setText(R.id.tv_block_hash,item.getHeadHash());
        helper.setText(R.id.tv_create_time,item.getCreatedTime());
    }
}
