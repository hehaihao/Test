package com.xm6leefun.wallet_module.points_property.property_manager.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;


import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class PropertyManagerAdapter extends BaseItemDraggableAdapter<HomeDataResultBean.FtBean, BaseViewHolder> {

    public boolean isEditModel = false;


    public PropertyManagerAdapter(@Nullable List<HomeDataResultBean.FtBean> data,CheckItemListener checkListener) {
        super(R.layout.list_item_property_manager, data);
        this.checkListener = checkListener;
    }




    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.FtBean item) {

        helper.setText(R.id.tv_property,item.getToken_name());

        helper.setText(R.id.tv_money,String.format(mContext.getResources().getString(R.string.balance_str),item.getNum()));

        ImageView iv_icon = helper.getView(R.id.iv_icon);

        ImageLoader.loadCircleImage(mContext,item.getToken_logo(),iv_icon);

        ImageView iv_edit = helper.getView(R.id.iv_edit);

        CheckBox cb_add = helper.getView(R.id.cb_add);

        TextView tvHash = helper.getView(R.id.tv_hash);

        if (!TextUtils.isEmpty(item.getContract_address())){
            tvHash.setVisibility(View.VISIBLE);
            tvHash.setText(item.getContract_address());
        }else{
            tvHash.setVisibility(View.GONE);
        }

        cb_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkListener != null){
                    if (isChecked) {
                        checkListener.checked(item);
                    } else {
                        checkListener.notChecked(item);
                    }
                }
            }
        });
        if (isEditModel){
            iv_edit.setVisibility(View.GONE);
            cb_add.setVisibility(helper.getAdapterPosition() != 0?View.VISIBLE:View.GONE);
//            cb_add.setVisibility(View.VISIBLE);
        }else{
            iv_edit.setVisibility(helper.getAdapterPosition() != 0?View.VISIBLE:View.GONE);
            cb_add.setVisibility(View.GONE);
        }
    }


    private CheckItemListener checkListener;
    public interface CheckItemListener{
        void checked(HomeDataResultBean.FtBean item);
        void notChecked(HomeDataResultBean.FtBean item);
    }

}