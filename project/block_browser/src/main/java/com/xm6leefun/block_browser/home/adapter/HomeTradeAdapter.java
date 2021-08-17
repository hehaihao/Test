package com.xm6leefun.block_browser.home.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.block_browser.R;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeListBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class HomeTradeAdapter extends BaseQuickAdapter<BlockBrowserTradeListBean, BaseViewHolder> {

    public HomeTradeAdapter(@Nullable List<BlockBrowserTradeListBean> data,BlockBrowserTradeAddressListener listener) {
        super(R.layout.list_item_home_trade,data);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockBrowserTradeListBean item) {

        helper.setText(R.id.tv_send_hash,item.getSendAddress());

        helper.setText(R.id.tv_receive_hash,item.getReceiveAddress());

        helper.setText(R.id.tv_trade_number,item.getActionTotal() + "");

        helper.setText(R.id.tv_block_hash,item.getAction());

        helper.setText(R.id.tv_time,item.getCreatedTime());

        helper.setText(R.id.tv_token_name,item.getActionCoin());

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            if (listener!=null){
                listener.selectHash(item.getAction());
            }
        });

    }




    private BlockBrowserTradeAddressListener listener;


    public interface BlockBrowserTradeAddressListener{
        void selectAddress(String address);
        void selectHash(String hash);
    }

}
