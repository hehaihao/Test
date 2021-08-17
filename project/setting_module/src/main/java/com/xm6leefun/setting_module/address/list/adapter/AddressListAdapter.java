package com.xm6leefun.setting_module.address.list.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.db.bean.AddressBook;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.setting_module.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/2 11:10
 */
public class AddressListAdapter extends BaseQuickAdapter<AddressBook, BaseViewHolder> {

    public AddressListAdapter(int layoutResId, @Nullable List<AddressBook> data,CopyAddressListener copyAddressListener) {
        super(layoutResId, data);
        this.copyAddressListener = copyAddressListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBook item) {
        TextView desc_tv = helper.getView(R.id.desc_tv);
        ImageView icon2 = helper.getView(R.id.icon_2);
        TextView name_tv = helper.getView(R.id.name_tv);
        TextView address_tv = helper.getView(R.id.address_tv);
        desc_tv.setVisibility(TextUtils.isEmpty(item.getRemark()) ? View.GONE : View.VISIBLE);
        desc_tv.setText(item.getRemark());
        address_tv.setText(item.getAddress());
        name_tv.setText(item.getPerson());
        icon2.setOnClickListener(v -> {
            if(copyAddressListener!=null)
                copyAddressListener.copy(item.getAddress());
        });
    }

    private CopyAddressListener copyAddressListener;
    public interface CopyAddressListener{
        void copy(String address);
    }
}
