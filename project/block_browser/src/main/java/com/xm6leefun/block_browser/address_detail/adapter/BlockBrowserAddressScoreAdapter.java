package com.xm6leefun.block_browser.address_detail.adapter;


import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.utils.ImageLoader;

import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserAddressScoreAdapter extends BaseQuickAdapter<BlockBrowserSearchBean.BlockActionInfoBean.BlockAddressListBean, BaseViewHolder> {


    public BlockBrowserAddressScoreAdapter(@Nullable List<BlockBrowserSearchBean.BlockActionInfoBean.BlockAddressListBean> data) {
        super(R.layout.list_item_score, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserSearchBean.BlockActionInfoBean.BlockAddressListBean item) {

        ImageView imageView = helper.getView(R.id.icon_score);

        ImageLoader.loadCircleImage(mContext,
                "https://avatar.csdnimg.cn/8/5/E/0_qq_35892584.jpg",
                imageView);

        helper.setText(R.id.tv_score_name,   item.getBlockCoin());

        helper.setText(R.id.tv_count, String.format(mContext.getResources().getString(R.string.count_str),item.getActionTotal()));




    }

}