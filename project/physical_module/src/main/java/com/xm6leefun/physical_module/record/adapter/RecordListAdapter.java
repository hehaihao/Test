package com.xm6leefun.physical_module.record.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.record.mvp.PhysicalRecordResultBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class RecordListAdapter extends BaseQuickAdapter<PhysicalRecordResultBean.ListBean, BaseViewHolder> {

    public RecordListAdapter(int layoutResId, @Nullable List<PhysicalRecordResultBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalRecordResultBean.ListBean item) {
        ImageView img = helper.getView(R.id.img);
        TextView name_tv = helper.getView(R.id.name_tv);
        TextView type_tv = helper.getView(R.id.type_tv);
        TextView date_tv = helper.getView(R.id.date_tv);
        TextView from_tv = helper.getView(R.id.from_tv);
        TextView to_tv = helper.getView(R.id.to_tv);

        ImageLoader.loadRadiusImage(mContext,item.getLogo(),img);
        name_tv.setText(mContext.getString(R.string.physical_name_data_str,item.getName()));

        String typeStr = item.getTypeName();
        type_tv.setText(mContext.getString(R.string.physical_tran_type_str,typeStr));
        date_tv.setText(item.getCreateTime());
        from_tv.setText(mContext.getString(R.string.physical_tran_from_str,item.getOriginatorAddress()));
        to_tv.setText(mContext.getString(R.string.physical_tran_to_str,item.getReAddress()));

    }
}
