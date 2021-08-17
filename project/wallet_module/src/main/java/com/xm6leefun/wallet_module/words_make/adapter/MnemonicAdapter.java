package com.xm6leefun.wallet_module.words_make.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.TextViewUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description: 抄写助记词适配器
 * @Author: hhh
 * @CreateDate: 2021/3/26 16:39
 */
public class MnemonicAdapter extends BaseQuickAdapter<CusMnemonic, BaseViewHolder> {

    private Context context;
    private boolean isShowTheme;
    private boolean isFirstTime = true;
    public MnemonicAdapter(Context context, int layoutResId, @Nullable List<CusMnemonic> data, boolean isShowTheme) {
        super(layoutResId, data);
//        this.isShowDelete = isShowDelete;
        this.context = context;
        this.isShowTheme = isShowTheme;
        this.mData = new ArrayList<>();
        if (data != null) {
            this.mData.addAll(data);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView mTvSerial = holder.getView(R.id.item_tv_serial);
        mTvSerial.setText((position + 1) + "");
        TextView mTvMnemonic = holder.getView(R.id.item_tv_mnemonic);

        CusMnemonic cusMnemonic = mData.get(position);
        mTvMnemonic.setText(cusMnemonic.getWord());

        if (cusMnemonic.isCorrect()) {
            if (cusMnemonic.isChecked()) {
                mTvMnemonic.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                mTvMnemonic.setTextColor(context.getResources().getColor(R.color.black));
            }
        } else {
            mTvMnemonic.setTextColor(context.getResources().getColor(R.color.red));
        }

    }

    @Override
    protected void convert(BaseViewHolder helper, CusMnemonic item) {
        helper.setText(R.id.item_tv_mnemonic, item.getWord());
        helper.addOnClickListener(R.id.item_tv_mnemonic);
        helper.addOnClickListener(R.id.item_lin_mnemonic_main);
    }
}
