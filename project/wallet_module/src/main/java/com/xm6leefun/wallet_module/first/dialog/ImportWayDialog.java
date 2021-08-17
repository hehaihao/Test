package com.xm6leefun.wallet_module.first.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.wallet_module.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:导入方式
 * @Author: hhh
 * @CreateDate: 2021年3月27日15:10
 */
public class ImportWayDialog extends BaseDialogFragment implements View.OnClickListener{
    public static final String TAG = ImportWayDialog.class.getSimpleName();
    public static final int WORDS = 0;
    public static final int PRI_KEY = 1;
    public static final int KEYSTORE = 2;
    private ClickListener clickListener;
    private Context mContext;

    public static ImportWayDialog getInstance(ClickListener clickListener){
        ImportWayDialog selectNetDialog = new ImportWayDialog();
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.import_way_dialog_layout,null);
        RelativeLayout private_key_layout = view.findViewById(R.id.private_key_layout);
        RelativeLayout words_layout = view.findViewById(R.id.words_layout);
        RelativeLayout keystore_layout = view.findViewById(R.id.keystore_layout);
        ImageView close_btn = view.findViewById(R.id.close_btn);
        private_key_layout.setOnClickListener(this);
        words_layout.setOnClickListener(this);
        keystore_layout.setOnClickListener(this);
        close_btn.setOnClickListener(this);

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
        int id = v.getId();
        if (id == R.id.close_btn) {
            dismiss();
        } else if (id == R.id.private_key_layout) {
            if (clickListener != null) clickListener.select(PRI_KEY);
            dismiss();
        } else if (id == R.id.keystore_layout) {
            if (clickListener != null) clickListener.select(KEYSTORE);
            dismiss();
        } else if (id == R.id.words_layout) {
            if (clickListener != null) clickListener.select(WORDS);
            dismiss();
        }
    }

    public static class Builder{
        private ClickListener clickListener;

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public ImportWayDialog build(){
            return ImportWayDialog.getInstance(clickListener);
        }
    }

    public interface ClickListener{
        void select(int importType);
    }
}
