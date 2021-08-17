package com.xm6leefun.setting_module.nodesetting.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.nodesetting.mvp.NodeBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class NodeSettingAdapter extends BaseQuickAdapter<NodeBean,BaseViewHolder> {

    private String mCurrNode;
    public NodeSettingAdapter(int layoutResId, @Nullable List<NodeBean> data,String mCurrNode) {
        super(layoutResId, data);
        this.mCurrNode = mCurrNode;
    }

    public void setmCurrNode(String mCurrNode) {
        this.mCurrNode = mCurrNode;
    }

    @Override
    protected void convert(BaseViewHolder helper, NodeBean item) {
        helper.setText(R.id.tv_title,item.getNodeName());

        ImageView iv_select = helper.getView(R.id.iv_select);

        if(item.getNodeUrl().equals(mCurrNode)){
            iv_select.setImageResource(R.mipmap.select_blue_icon);
        }else{
            iv_select.setImageResource(R.drawable.shape_circle_grey);
        }
    }
}
