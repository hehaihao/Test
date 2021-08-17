package com.xm6leefun.wallet_module.wallet_manage.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.wallet_module.R;
import java.util.List;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/30 11:00
 */
public class WalletListAdapter extends BaseQuickAdapter<Wallet_Main, BaseViewHolder> {

    private final int[] bgs = new int[]{
            R.drawable.blue8_conner_f46f08_bg,
            R.drawable.blue8_conner_d71010_bg,
            R.drawable.blue8_conner_365cfe_bg,
            R.drawable.blue8_conner_6736fe_bg};
    public WalletListAdapter(int layoutResId, @Nullable List<Wallet_Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wallet_Main item) {
        View id_wallet_tag = helper.getView(R.id.id_wallet_tag);
        TextView address_tv = helper.getView(R.id.address_tv);
        TextView name_tv = helper.getView(R.id.name_tv);
        address_tv.setText(item.getAddress());
        name_tv.setText(item.getWalletName());
        id_wallet_tag.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
        int position = helper.getPosition();
        RelativeLayout root_view = helper.getView(R.id.root_view);
        int result = position%bgs.length;
        if(item.isIdWallet()){//身份钱包固定只有一个，固定背景
            root_view.setBackgroundResource(R.drawable.blue8_conner_6736fe_bg);
        }else {//其他钱包按照既定背景循环
            if (result == 0) {
                root_view.setBackgroundResource(bgs[0]);
            } else if (result == 1) {
                root_view.setBackgroundResource(bgs[1]);
            } else if (result == 2) {
                root_view.setBackgroundResource(bgs[2]);
            } else {
                root_view.setBackgroundResource(bgs[3]);
            }
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
