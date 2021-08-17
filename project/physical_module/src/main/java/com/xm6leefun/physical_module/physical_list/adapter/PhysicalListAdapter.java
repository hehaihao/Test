package com.xm6leefun.physical_module.physical_list.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListBean;

import java.util.List;

import androidx.annotation.Nullable;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class PhysicalListAdapter extends BaseQuickAdapter<PhysicalListBean, BaseViewHolder> {
    private Context context;
    public PhysicalListAdapter(Context context, int layoutResId, @Nullable List<PhysicalListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalListBean item) {
        ImageView img = helper.getView(R.id.img);
        TextView token_id_tv = helper.getView(R.id.token_id_tv);
        TextView name_tv = helper.getView(R.id.name_tv);
        ImageLoader.loadRadiusImage(context,item.getNft_img(),img);
        name_tv.setText(context.getString(R.string.physical_name_data_str,item.getNft_name()));
        token_id_tv.setText(context.getString(R.string.token_id_data_str,item.getId()+""));

    }
}
