package com.xm6leefun.wallet_module.words_confirm.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description: 助记词adapter
 * @Author: hhh
 * @CreateDate: 2021/3/26 16:39
 */
public class MnemonicChosenAdapter extends BaseQuickAdapter<CusMnemonic, BaseViewHolder> {

//    private boolean isShowDelete;

    private Context context;
    public MnemonicChosenAdapter(Context context, int layoutResId, @Nullable List<CusMnemonic> data/*, boolean isShowDelete*/) {
        super(layoutResId, data);
//        this.isShowDelete = isShowDelete;
        this.context = context;
        this.mData = new ArrayList<>();
        if (data != null) {
            this.mData.addAll(data);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LinearLayout mItemLinMain = holder.getView(R.id.item_lin_mnemonic_main);
        TextView mTvMnemonic = holder.getView(R.id.item_tv_mnemonic);

        CusMnemonic cusMnemonic = mData.get(position);
        if (cusMnemonic.isChecked()){
            if (cusMnemonic.isCorrect()) {
                mTvMnemonic.setTextColor(context.getResources().getColor(R.color.black_30a));
            } else {
                mTvMnemonic.setTextColor(context.getResources().getColor(R.color.black_80a));
            }
        } else {
            mTvMnemonic.setTextColor(context.getResources().getColor(R.color.black_80a));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CusMnemonic item) {
        helper.setText(R.id.item_tv_mnemonic, item.getWord());
        helper.addOnClickListener(R.id.item_tv_mnemonic);
    }
}
