package com.xm6leefun.wallet_module.points_property.my_property.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.utils.TextUtil;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;


import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class MyPropertyAdapter extends BaseQuickAdapter<HomeDataResultBean.FtBean, BaseViewHolder> {


    public MyPropertyAdapter(@Nullable List<HomeDataResultBean.FtBean> data) {
        super(R.layout.list_item_my_property, data);

    }


    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.FtBean item) {

        helper.setText(R.id.tv_property,item.getToken_name());

        helper.setText(R.id.tv_money,String.format(mContext.getResources().getString(R.string.balance_str),item.getNum()));

        TextView tvHash = helper.getView(R.id.tv_hash);

        if (!TextUtils.isEmpty(item.getContract_address())){
            tvHash.setVisibility(View.VISIBLE);
            tvHash.setText(item.getContract_address());
        }else{
            tvHash.setVisibility(View.GONE);
        }

        ImageView iv_icon = helper.getView(R.id.iv_icon);

        ImageLoader.loadCircleImage(mContext,item.getToken_logo(),iv_icon);



    }
}