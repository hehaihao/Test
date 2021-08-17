package com.xm6leefun.wallet_module.home_wallet.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.utils.ShowUtil;
import com.xm6leefun.wallet_module.R;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/30 9:49
 */
public class HomeTopListAdapter extends BaseQuickAdapter<Wallet_Main, BaseViewHolder> {
    private Context context;
    private ClickListener clickListener;
    private int itemMargin;
    private int[] size;
    private float width,height;
    public HomeTopListAdapter(Context context, int layoutResId, @Nullable List<Wallet_Main> data,ClickListener clickListener) {
        super(layoutResId, data);
        this.context = context;
        this.clickListener = clickListener;
        size = ShowUtil.getScreenSize(context);
        width = (size[0]-ShowUtil.dip2px(context,24)) * 39.f/40;
        height = width * 108.f/335;
        itemMargin = ShowUtil.dip2px(context,5);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wallet_Main item) {
        TextView wallet_name_tv = helper.getView(R.id.wallet_name_tv);
        LinearLayout address_lin = helper.getView(R.id.address_lin);
        TextView address_tv = helper.getView(R.id.address_tv);
        wallet_name_tv.setText(item.getWalletName());
        int position = helper.getPosition();
        address_tv.setText(item.getAddress());
        LinearLayout top_layout = helper.getView(R.id.top_layout);
        address_lin.setOnClickListener(view -> clickListener.copy(item.getAddress()));
        wallet_name_tv.setOnClickListener(view -> clickListener.switchWallet());
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) top_layout.getLayoutParams();
        params.width = (int) width;
        params.height = (int) height;
        if(position == 0){
            top_layout.setBackgroundResource(R.drawable.blue8_top_conner_365cfe_bg);
            params.leftMargin = 3*itemMargin;
            params.rightMargin = itemMargin;
        }else if(position == getItemCount() -1){
            top_layout.setBackgroundResource(R.drawable.blue8_top_conner_6736fe_bg);
            params.rightMargin = 3*itemMargin;
            params.leftMargin = itemMargin;
        }else{
            top_layout.setBackgroundResource(R.drawable.blue8_top_conner_365cfe_bg);
            params.leftMargin = itemMargin;
            params.rightMargin = itemMargin;
        }
        top_layout.setLayoutParams(params);
    }

    public interface ClickListener{
        void switchWallet();
        void copy(String address);
    }
}
