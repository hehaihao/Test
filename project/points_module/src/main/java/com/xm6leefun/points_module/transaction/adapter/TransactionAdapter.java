package com.xm6leefun.points_module.transaction.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class TransactionAdapter extends BaseQuickAdapter<TransactionListBean, BaseViewHolder> {

    public TransactionAdapter(int layoutResId, @Nullable List<TransactionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TransactionListBean item) {
        TextView address_tv = helper.getView(R.id.address_tv);
        TextView time_tv = helper.getView(R.id.time_tv);
        TextView money_tv = helper.getView(R.id.money_tv);
        TextView fail_tv = helper.getView(R.id.fail_tv);
        String targetAddress = item.getRe_address();
        int type = item.getType();
        if(type == 1||type == 5){//转出
            money_tv.setText("- "+item.getTransfer_num());
            money_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_d30202));
        }else if(type ==2||type ==6){//转入
            money_tv.setText("+ "+item.getTransfer_num());
            money_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_0cae32));
        }else{
            money_tv.setText(""+item.getTransfer_num());
            money_tv.setTextColor(ContextCompat.getColor(mContext,R.color.color_0cae32));
        }
        fail_tv.setText(item.getTypeName());
        fail_tv.setVisibility((type == 3) ? View.VISIBLE : View.GONE);//3表示失败
        address_tv.setText(targetAddress);
        time_tv.setText(item.getCreate_time());
    }
}
