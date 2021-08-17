package com.xm6leefun.wallet_module.wallet_setting.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.KeyBoardUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.wallet_module.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:钱包设置弹窗  dialogType 0表示钱包名，1表示导出助记词，2表示导出keystore，3表示导出私钥，4表示移除钱包
 * @Author: hhh
 * @CreateDate: 2020/9/21 16:40
 */
public class InputDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = InputDialog.class.getSimpleName();
    public static final int Wallet_Name = 0;
    public static final int Wallet_words = 1;
    public static final int Wallet_words_backup = 2;
    public static final int Wallet_keystore = 3;
    public static final int Wallet_privatekey = 4;
    public static final int Wallet_remove = 5;
    public static final int Wallet_prompt_info = 6;
    public static final int Wallet_quick_pay = 7;
    private static final String CONTENT = "content";
    private static final String DIALOG_TYPR = "dialog_type";
    private ClickListener clickListener;
    private Context mContext;
    private ImageView remove_tips_iv;
    private TextView sure_tv;
    private EditText content_et;
    private int dialogType;
    private boolean isShow = false;

    public static InputDialog getInstance(String content, int dialogType, ClickListener clickListener){
        InputDialog selectNetDialog = new InputDialog();
        Bundle args = new Bundle();
        args.putString(CONTENT,content);
        args.putInt(DIALOG_TYPR,dialogType);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.input_dialog_layout,null);
        TextView title_tv = view.findViewById(R.id.title_tv);
        TextView remove_tips_tv = view.findViewById(R.id.remove_tips_tv);
        remove_tips_iv = view.findViewById(R.id.remove_tips_iv);
        content_et = view.findViewById(R.id.content_et);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        sure_tv = view.findViewById(R.id.sure_tv);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);
        remove_tips_iv.setOnClickListener(this);

        String content = args.getString(CONTENT,"");
        dialogType = args.getInt(DIALOG_TYPR,0);
        switch (dialogType){
            case Wallet_Name://修改钱包名
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.GONE);
                title_tv.setText(R.string.wallet_setting_name_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT);
                content_et.setHint(R.string.create_wallet_name_input);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_words://导出助记词
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_setting_export_words_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_words_backup://备份助记词
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_setting_backup_words_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_keystore://导出keystore
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_setting_export_keystore_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_privatekey://导出私钥
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_setting_export_private_key_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_quick_pay:  // 免密支付
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_setting_no_psw_pay_str);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_remove://移除钱包
                remove_tips_tv.setVisibility(View.VISIBLE);
                remove_tips_iv.setVisibility(View.VISIBLE);
                title_tv.setText(R.string.wallet_remove_title);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                content_et.setHint(R.string.wallet_input_psw_str);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Wallet_prompt_info:  // 密码提示词
                remove_tips_tv.setVisibility(View.GONE);
                remove_tips_iv.setVisibility(View.GONE);
                title_tv.setText(R.string.wallet_setting_forget_psw_dialog_title);
                content_et.setInputType(InputType.TYPE_CLASS_TEXT);
                content_et.setEnabled(false);
                content_et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                break;
        }
        content_et.setText(content);
        content_et.setSelection(content_et.getText().toString().trim().length());

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
                    ToastUtil.showCenterToast(getString(R.string.wallet_input_psw_str));
                    return;
                }
                switch (dialogType) {
                    case Wallet_prompt_info:  // 密码提示词
                    case Wallet_Name://修改钱包名
                    case Wallet_words://导出助记词
                    case Wallet_words_backup://备份助记词
                    case Wallet_keystore://导出keystore
                    case Wallet_privatekey://导出私钥
                    case Wallet_remove://移除钱包
                    case Wallet_quick_pay:  // 免密支付
                        sure_tv.setEnabled(false);
                        sure_tv.setText(R.string.checking_psw_str);
                        //关闭软键盘
                        KeyBoardUtils.hideSoftKeyboard(mContext,content_et);
                        clickListener.onSure(dialogType,intputContent, isPass -> {
                            sure_tv.setEnabled(true);
                            sure_tv.setText(R.string.sure);
                        });
                        break;
                }
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
        private String content;
        private int dialogType;
        private ClickListener clickListener;

        public Builder setContent(String content){
            this.content = content;
            return this;
        }
        public Builder setDialogType(int dialogType){
            this.dialogType = dialogType;
            return this;
        }
        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }

        public InputDialog build(){
            return InputDialog.getInstance(content,dialogType,clickListener);
        }
    }

    public interface ClickListener{
        void onSure(int inputTye,String content,CheckPswListener listener);
    }
    public interface CheckPswListener{
        void checkPswResult(boolean isPass);
    }


}
