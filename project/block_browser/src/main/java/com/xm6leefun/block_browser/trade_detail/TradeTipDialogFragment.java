package com.xm6leefun.block_browser.trade_detail;


import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xm6leefun.block_browser.R;
import com.xm6leefun.common.base.BaseDialogFragment;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/5/24
 */
public class TradeTipDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    public static String TAG = TradeTipDialogFragment.class.getSimpleName();
    private TextView tv_sure;

    public static TradeTipDialogFragment newInstance() {
        TradeTipDialogFragment dialog = new TradeTipDialogFragment();
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.white_10dp_conner);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.dialog_trade_tip, container, false);
        tv_sure =  inflate.findViewById(R.id.sure_tv);
        tv_sure.setOnClickListener(this);
        setCancelable(false);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sure_tv) {
            getActivity().finish();
            dismiss();
        }
    }
}
