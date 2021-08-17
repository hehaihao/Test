package com.xm6leefun.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:密码输入
 * @Author: hhh
 * @CreateDate: 2020/9/21 16:40
 */
public class InputPswDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = InputPswDialog.class.getSimpleName();
    private ClickListener clickListener;
    private Context mContext;
    private ImageView remove_tips_iv;
    private EditText content_et;
    private TextView sure_tv;
    private boolean isShow = false;

    public static InputPswDialog getInstance(ClickListener clickListener){
        InputPswDialog inputPswDialog = new InputPswDialog();
        inputPswDialog.setClickListener(clickListener);
        return inputPswDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.input_psw_dialog_layout,null);
        remove_tips_iv = view.findViewById(R.id.remove_tips_iv);
        content_et = view.findViewById(R.id.content_et);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        sure_tv = view.findViewById(R.id.sure_tv);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);
        remove_tips_iv.setOnClickListener(this);
        Dialog dialog = new Dialog(mContext,R.style.bottom_dialog_style);
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
            params.gravity = Gravity.CENTER; //将对话框放到布局中间，也就是屏幕中间
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
        } else if (id == R.id.sure_tv) {
            if (clickListener != null) {
                String intputContent = content_et.getText().toString().trim();
                if(StrUtils.isEmpty(intputContent)){
                    ToastUtil.showCenterToast(getString(R.string.dialog_wallet_input_psw_str));
                    return;
                }
                sure_tv.setEnabled(false);
                sure_tv.setText(R.string.checking_psw_str);
                clickListener.onSure(intputContent, isPass -> {
                    sure_tv.setEnabled(true);
                    sure_tv.setText(R.string.sure);
                });
            }
        } else if (id == R.id.remove_tips_iv) {
            isShow = !isShow;
            if (isShow) {  // 显示
                content_et.setInputType(InputType.TYPE_CLASS_TEXT);
                remove_tips_iv.setImageResource(R.mipmap.eye_icon);
            } else {
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                remove_tips_iv.setImageResource(R.mipmap.eye_close_icon);
            }
            content_et.setSelection(content_et.getText().toString().trim().length());
        }
    }

    public static class Builder{
        private ClickListener clickListener;

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }

        public InputPswDialog build(){
            return InputPswDialog.getInstance(clickListener);
        }
    }

    public interface ClickListener{
        void onSure(String content,CallbackListener callbackListener);
    }

    public interface CallbackListener{
        void pswCheckResult(boolean isPass);
    }
}
