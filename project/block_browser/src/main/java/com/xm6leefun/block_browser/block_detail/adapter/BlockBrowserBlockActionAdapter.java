package com.xm6leefun.block_browser.block_detail.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/5/6
 */
public class BlockBrowserBlockActionAdapter extends BaseQuickAdapter<BlockBrowserSearchBean.BlockActionInfoBean.BlockActionListBean, BaseViewHolder> {


    public BlockBrowserBlockActionAdapter(@Nullable List<BlockBrowserSearchBean.BlockActionInfoBean.BlockActionListBean> data) {
        super(R.layout.list_item_block_action, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserSearchBean.BlockActionInfoBean.BlockActionListBean item) {

        helper.setText(R.id.tv_block_hash,item.getAction());
        helper.setText(R.id.tv_create_time,item.getCreatedTime());
        helper.setText(R.id.tv_number,item.getActionTotal());


    }
}