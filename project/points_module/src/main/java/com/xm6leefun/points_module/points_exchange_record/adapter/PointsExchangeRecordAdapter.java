package com.xm6leefun.points_module.points_exchange_record.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.points_exchange_record.mvp.RecordResultBean;

import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/9 10:11
 */

public class PointsExchangeRecordAdapter extends BaseQuickAdapter<RecordResultBean.ListBean,BaseViewHolder> {

    public PointsExchangeRecordAdapter(int layoutResId, @Nullable List<RecordResultBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordResultBean.ListBean item) {
        long state = item.getState();
        String stateStr = "";
        TextView status_tv = helper.getView(R.id.status_tv);
        status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.black_60a));
        if(state == 1){//审核中
            status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_365cfe));
            stateStr = mContext.getString(R.string.points_exchange_ing_str);
        }else if(state == 2){//审核完成
            status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_0cae32));
            stateStr = mContext.getString(R.string.points_exchange_success_str);
        }else if(state == 3){//审核失败
            status_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_d30202));
            stateStr = mContext.getString(R.string.points_exchange_fail_str);
        }
        helper.setText(R.id.name_tv,mContext.getString(R.string.points_toptitle_str));

        helper.setText(R.id.money_tv,"-"+item.getNum());

        status_tv.setText(stateStr);

        helper.setText(R.id.time_tv,item.getCreated());


    }
}
