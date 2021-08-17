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
public class BlockBrowserAddressNftAdapter extends BaseQuickAdapter<BlockBrowserSearchBean.NftInfoBean, BaseViewHolder> {


    public BlockBrowserAddressNftAdapter(@Nullable List<BlockBrowserSearchBean.NftInfoBean> data) {
        super(R.layout.list_item_property, data);

    }


    @Override
    protected void convert(BaseViewHolder helper,BlockBrowserSearchBean.NftInfoBean item) {

        helper.setText(R.id.tv_product_name, "物品名称："  + item.getNftName() );

        helper.setText(R.id.tv_token, "Token ID: " + item.getId() + "");

        helper.setText(R.id.tv_ruler_hash,"合约地址: " + item.getContractAddress());

        ImageView ivPhoto = helper.getView(R.id.iv_photo);

        if (item.getNftImg() != null && item.getNftImg().size() > 0) {
            ImageLoader.loadRadiusImage(mContext, item.getNftImg().get(0), ivPhoto);
        }


    }
}