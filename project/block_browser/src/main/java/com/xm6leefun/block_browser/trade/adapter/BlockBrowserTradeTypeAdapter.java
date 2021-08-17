package com.xm6leefun.block_browser.trade.adapter;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeTypeBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserTradeTypeAdapter  extends BaseQuickAdapter<BlockBrowserTradeTypeBean, BaseViewHolder> {

    public BlockBrowserTradeTypeAdapter(@Nullable List<BlockBrowserTradeTypeBean> data,BlockBrowserTradeTypeListener listener) {
        super(R.layout.list_item_trade_type, data);
        this.listener = listener;
    }


    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserTradeTypeBean item) {

        TextView tv_types = helper.getView(R.id.tv_types);

        tv_types.setText(item.getType_name());


        tv_types.setTextColor(item.isSelected()? Color.WHITE : Color.BLACK);

        tv_types.setSelected(item.isSelected());

        tv_types.setOnClickListener(v -> {
            if (listener!= null && !item.isSelected()){
                listener.tradeTypeSelected(item);
            }
        });


    }



    private BlockBrowserTradeTypeListener listener;


    public interface BlockBrowserTradeTypeListener{
        void tradeTypeSelected(BlockBrowserTradeTypeBean item);

    }



}
