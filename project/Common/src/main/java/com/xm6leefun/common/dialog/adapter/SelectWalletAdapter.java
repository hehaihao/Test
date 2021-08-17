package com.xm6leefun.common.dialog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.R;
import com.xm6leefun.common.db.bean.Wallet_Main;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/24 14:31
 */
public class SelectWalletAdapter extends BaseQuickAdapter<Wallet_Main, BaseViewHolder> {
    private String mCurrSelectAddress;
    private Context context;
    private CopyListener copyListener;
    public SelectWalletAdapter(int layoutResId, @Nullable List<Wallet_Main> data, String mCurrSelectAddress, Context context,CopyListener copyListener) {
        super(layoutResId, data);
        this.mCurrSelectAddress = mCurrSelectAddress;
        this.context = context;
        this.copyListener = copyListener;
    }

    public void setCurrAddress(String mCurrSelectAddress){
        this.mCurrSelectAddress = mCurrSelectAddress;
    }

    @Override
    protected void convert(BaseViewHolder helper, Wallet_Main item) {
        TextView address_tv = helper.getView(R.id.address_tv);
        ImageView copy_iv = helper.getView(R.id.copy_iv);
        ImageView selected_iv = helper.getView(R.id.selected_iv);
        address_tv.setText(item.getAddress());
        copy_iv.setOnClickListener(v -> copyListener.copy(item.getAddress()));
        if(item.getAddress().equals(mCurrSelectAddress)){
            selected_iv.setImageResource(R.mipmap.select_blue_icon);
        }else{
            selected_iv.setImageResource(R.drawable.shape_circle_grey);
        }
    }

    public interface CopyListener{
        void copy(String address);
    }
}
