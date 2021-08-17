package com.xm6leefun.physical_module.physical_detail.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.physical_module.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description:物权转移确认
 * @Author: hhh
 * @CreateDate: 2021/3/23 8:30
 */
public class PhysicalTranDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = PhysicalTranDialog.class.getSimpleName();
    private static final String BABLANCE = "balance";
    private static final String OWNNER_ADDRESS = "ownnerAddress";
    private ClickListener clickListener;
    private Context mContext;
    private EditText address_et,psw_et;
    private ImageView select_address_iv;
    private TextView gas_tv,sure_tv;
    private String ownnerAddress = "";


    public static PhysicalTranDialog getInstance(long balancePoint,String ownnerAddress, ClickListener clickListener){
        PhysicalTranDialog selectNetDialog = new PhysicalTranDialog();
        Bundle args = new Bundle();
        args.putLong(BABLANCE,balancePoint);
        args.putString(OWNNER_ADDRESS,ownnerAddress);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.physical_tran_dialog_layout,null);
        address_et = view.findViewById(R.id.address_et);
        select_address_iv = view.findViewById(R.id.select_address_iv);
        gas_tv = view.findViewById(R.id.gas_tv);
        address_et.setOnLongClickListener(v -> {
            address_et.setText(getTextFromClip(mContext));
            return true;
        });
        select_address_iv.setOnClickListener(v -> {
            if(clickListener != null){
                clickListener.selectAddress();
            }
        });
        psw_et = view.findViewById(R.id.psw_et);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        sure_tv = view.findViewById(R.id.sure_tv);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);

        if(args != null){
            long balancePoints = args.getLong(BABLANCE,0);
            ownnerAddress = args.getString(OWNNER_ADDRESS,"");
        }
        Dialog dialog = new Dialog(mContext,R.style.bottom_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private SelectCallbackListener scanCallbackListener = new SelectCallbackListener() {
        @Override
        public void selectResult(String address) {
            address_et.setText(address);
        }

        @Override
        public void checkPswResult(boolean isPass) {
            sure_tv.setEnabled(true);
            sure_tv.setText(R.string.sure);
        }
    };

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
            if (clickListener != null && DoubleClickUtils.isNoDoubleClick()) {
                String addressStr = address_et.getText().toString().trim();
                String pswStr = psw_et.getText().toString().trim();
                if (!StrUtils.checkCurrAddress(addressStr)) {
                    ToastUtil.showCenterToast(getString(R.string.wrong_psw_tip));
                    return;
                }
                if (ownnerAddress.equals(addressStr)) {
                    ToastUtil.showCenterToast(getString(R.string.same_address_tip));
                    return;
                }
                if (StrUtils.isEmpty(pswStr)) {
                    ToastUtil.showCenterToast(getString(R.string.please_input_psw_tip));
                    return;
                }
                sure_tv.setEnabled(false);
                sure_tv.setText(R.string.checking_psw_str);
                clickListener.onSure(addressStr, pswStr,scanCallbackListener);
            }
        }
    }

    public static class Builder{
        private long balancePoints;
        private String ownnerAddress;
        private ClickListener clickListener;

        public Builder setBalancePoints(long balancePoints) {
            this.balancePoints = balancePoints;
            return this;
        }

        public Builder setOwnnerAddress(String ownnerAddress) {
            this.ownnerAddress = ownnerAddress;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public PhysicalTranDialog build(){
            return PhysicalTranDialog.getInstance(balancePoints,ownnerAddress,clickListener);
        }
    }

    private String getTextFromClip(Context context){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if(!clipboardManager.hasPrimaryClip())
            return"";
        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 text
        String text = clipData.getItemAt(0).getText().toString();
        return text;
    }

    public interface ClickListener{
        void onSure(String toAddress, String pswStr,SelectCallbackListener selectCallbackListener);
        void selectAddress();
    }

    public interface SelectCallbackListener{
        void selectResult(String address);
        void checkPswResult(boolean isPass);
    }
}
