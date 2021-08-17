package com.xm6leefun.wallet_module.backup_tips.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.wallet_module.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:备份提示
 * @Author: hhh
 * @CreateDate: 2021年3月26日15:26:31
 */
public class BackupTipDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = BackupTipDialog.class.getSimpleName();
    private ClickListener clickListener;
    private Context mContext;

    public static BackupTipDialog getInstance(ClickListener clickListener){
        BackupTipDialog selectNetDialog = new BackupTipDialog();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.backup_tip_dialog_layout,null);
        TextView sure_tv = view.findViewById(R.id.sure_tv);
        sure_tv.setOnClickListener(this);

        Dialog dialog = new Dialog(mContext,R.style.bottom_nopadding_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
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
        if (v.getId() == R.id.sure_tv) {
            if (clickListener != null) {
                clickListener.sure();
                dismiss();
            }
        }
    }

    public static class Builder{
        private ClickListener clickListener;

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public BackupTipDialog build(){
            return BackupTipDialog.getInstance(clickListener);
        }
    }

    public interface ClickListener{
        void sure();
    }
}
