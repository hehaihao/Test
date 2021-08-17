package com.xm6leefun.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.TextUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:指定金额
 * @Author: hhh
 * @CreateDate: 2020/9/21 16:40
 */
public class InputMoneyDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = InputMoneyDialog.class.getSimpleName();
    private static final String MONEY = "money";
    private static final String MAIN_NET = "mainNet";
    private ClickListener clickListener;
    private Context mContext;
    private EditText content_et;

    private int mainNet = 0;

    public static InputMoneyDialog getInstance(String money, ClickListener clickListener){
        InputMoneyDialog selectNetDialog = new InputMoneyDialog();
        Bundle args = new Bundle();
        args.putString(MONEY,money);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.input_money_dialog_layout,null);
        content_et = view.findViewById(R.id.content_et);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        TextView sure_tv = view.findViewById(R.id.sure_tv);
        content_et.addTextChangedListener(payNumTextWatcher);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);

        if(args != null){
            double money = Double.parseDouble(args.getString(MONEY,"0"));
            mainNet = args.getInt(MAIN_NET, 0);
            if(money != 0) {
                content_et.setText(money + "");
                content_et.setSelection(content_et.getText().toString().trim().length());
            }
        }
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
                String moneyStr = content_et.getText().toString().trim();
                if (".".equals(moneyStr) || moneyStr.startsWith(".") || moneyStr.endsWith(".")) {
                    ToastUtil.showCenterToast(getString(R.string.receipt_qrcode_set_num_error));
                    return;
                }
                if (!TextUtils.isEmpty(moneyStr)) {
                    double money = Double.parseDouble(moneyStr);
                    if (money > 10000000000.0) {
                        ToastUtil.showCenterToast(getString(R.string.wallet_oc_transfer_enter_max_num));
                        return;
                    } else if (money <= 0) {
                        ToastUtil.showCenterToast(getString(R.string.wallet_oc_transfer_enter_min_num));
                        return;
                    } else {
                        BigDecimal bigDecimal = new BigDecimal(moneyStr);
                        moneyStr = bigDecimal.stripTrailingZeros().toPlainString();  // 去除小数点后末尾的0
                    }
                }
                clickListener.inputMoney(moneyStr);
            }
            dismiss();
        }
    }

    private TextWatcher payNumTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            TextUtil.textWatchDecimalLimit(content_et, s, mainNet == 0 ? 8 : 9);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static class Builder{
        private String money;
        private ClickListener clickListener;

        public Builder setMoney(String money){
            this.money = money;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public InputMoneyDialog build(){
            return InputMoneyDialog.getInstance(money,clickListener);
        }
    }

    public interface ClickListener{
        void inputMoney(String money);
    }
}
