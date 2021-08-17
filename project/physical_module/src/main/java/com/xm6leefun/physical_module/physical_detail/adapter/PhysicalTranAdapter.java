package com.xm6leefun.physical_module.physical_detail.adapter;

import android.content.Context;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;
import com.xm6leefun.physical_module.physical_detail.mvp.PhysicalTranListBean;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class PhysicalTranAdapter extends BaseQuickAdapter<PhysicalDetailBean.TransferBean, BaseViewHolder> {
    private Context context;
    public PhysicalTranAdapter(Context context, int layoutResId, @Nullable List<PhysicalDetailBean.TransferBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalDetailBean.TransferBean item) {
        TextView event_tv = helper.getView(R.id.event_tv);
        TextView from_address_tv = helper.getView(R.id.from_address_tv);
        TextView to_address_tv = helper.getView(R.id.to_address_tv);
        TextView date_tv = helper.getView(R.id.date_tv);


        event_tv.setText(item.getTypeName());

        from_address_tv.setText(item.getOriginator_address());

        to_address_tv.setText(item.getRe_address());

        date_tv.setText(item.getCreate_time());


    }
}
