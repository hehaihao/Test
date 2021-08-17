package com.xm6leefun.points_module.points_exchange.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeResultBean;

import java.util.List;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class PointsListAdapter extends BaseQuickAdapter<PointsExchangeResultBean.ListBean, BaseViewHolder> {
    private Context context;
    public PointsListAdapter(Context context, int layoutResId, @Nullable List<PointsExchangeResultBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PointsExchangeResultBean.ListBean item) {
        ImageView icon = helper.getView(R.id.icon);
        TextView points_title = helper.getView(R.id.points_title);
        TextView points_tv = helper.getView(R.id.points_tv);

        ImageLoader.loadCircleImage(context,item.getIntegralLogo(),icon);
        points_title.setText(item.getIntegralName());
        points_tv.setText(item.getNum()+"");

    }
}
