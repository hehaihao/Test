package com.xm6leefun.scan_module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.scan_module.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @Description:地址弹窗
 * @Author: hhh
 * @CreateDate: 2021/4/1 11:20
 */
public class ScanAddressDialog extends BaseDialogFragment implements View.OnClickListener, DialogInterface.OnKeyListener {
    public static final String TAG = ScanAddressDialog.class.getSimpleName();
    public static final String ADDRESS = "address";
    private ClickListener clickListener;
    private Context mContext;
    private String address = "";

    public static ScanAddressDialog getInstance(String address, ClickListener clickListener){
        ScanAddressDialog selectNetDialog = new ScanAddressDialog();
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        selectNetDialog.setArguments(args);
        selectNetDialog.setClickListener(clickListener);
        return selectNetDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.scan_address_dialog_layout,null);
        TextView save_tv = view.findViewById(R.id.save_tv);
        TextView transfer_tv = view.findViewById(R.id.transfer_tv);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        save_tv.setOnClickListener(this);
        transfer_tv.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        if(args != null){
            address = args.getString(ADDRESS,"");
        }

        Dialog dialog = new Dialog(mContext,R.style.bottom_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setOnKeyListener(this);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM; //将对话框放到布局下面，也就是屏幕下方
            dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        }
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel_tv) {
            dismiss();
        } else if (id == R.id.save_tv) {
            if (clickListener != null) clickListener.saveAddress(address);
            dismiss();
        } else if (id == R.id.transfer_tv) {
            if (clickListener != null) clickListener.transfer(address);
            dismiss();
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) {
            if(clickListener != null) clickListener.onDissmiss();
        }
        return false;
    }

    public static class Builder{
        private String address;
        private ClickListener clickListener;

        public Builder setAddress(String address){
            this.address = address;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public ScanAddressDialog build(){
            return ScanAddressDialog.getInstance(address,clickListener);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(clickListener != null) clickListener.onDissmiss();
    }

    public interface ClickListener{
        void saveAddress(String address);
        void transfer(String address);
        void onDissmiss();
    }
}
