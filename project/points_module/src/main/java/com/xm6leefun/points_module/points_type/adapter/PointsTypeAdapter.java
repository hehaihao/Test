package com.xm6leefun.points_module.points_type.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.points_module.R;


import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class PointsTypeAdapter extends BaseQuickAdapter<HomeDataResultBean.FtBean, BaseViewHolder> {
    public PointsTypeAdapter(@Nullable List<HomeDataResultBean.FtBean> data) {
        super(R.layout.list_item_points_type, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.FtBean item) {
        ImageView icon = helper.getView(R.id.iv_icon);
        Glide.with(mContext).load(item.getToken_logo()).into(icon);
        helper.setText(R.id.tv_name, item.getToken_name());

    }
}