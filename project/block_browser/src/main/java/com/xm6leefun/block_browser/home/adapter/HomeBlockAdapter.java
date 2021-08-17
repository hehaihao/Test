package com.xm6leefun.block_browser.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;
import com.xm6leefun.block_browser.home.bean.BlockBrowserHomeBean;
import com.xm6leefun.common.utils.TimeUtil;


import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class HomeBlockAdapter extends BaseQuickAdapter<BlockBrowserBlockListBean, BaseViewHolder> {

    public HomeBlockAdapter(@Nullable List<BlockBrowserBlockListBean> data,BlockBrowserBlockListener listener) {
        super(R.layout.list_item_home_block, data);
        this.listener = listener;

    }

    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserBlockListBean item) {

        helper.setText(R.id.tv_block_height, item.getBlockHeight() + "");

        helper.setText(R.id.tv_block_hash, item.getHeadHash());

        helper.setText(R.id.tv_trade_number, item.getTradingNum() + "");

        helper.setText(R.id.tv_time, item.getCreatedTime());

        helper.setText(R.id.tv_block_time, TimeUtil.formatDisplayTime(item.getCreatedTime()));



        helper.getView(R.id.tv_block_height).setOnClickListener(v -> {
            if (listener!=null){
                listener.selectHeight(item.getBlockHeight());
            }
        });

        helper.getView(R.id.tv_block_hash).setOnClickListener(v -> {
            if (listener!=null){
                listener.selectHash(item.getHeadHash());
            }
        });


    }


    private BlockBrowserBlockListener listener;


    public interface BlockBrowserBlockListener{
        void selectHeight(int height);
        void selectHash(String hash);
    }


}
