package com.xm6leefun.points_module.points_exchange_record.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.points_module.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @Description:积分兑换确认
 * @Author: hhh
 * @CreateDate: 2021/3/23 8:30
 */
public class ExchangePointDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = ExchangePointDialog.class.getSimpleName();
    private static final String ADDRESS = "address";
    private static final String BABLANCE = "balance";
    private ClickListener clickListener;
    private Context mContext;
    private EditText address_et,points_et;
    private TextView balance_point_tv,arrival_points_tv;


    public static ExchangePointDialog getInstance(String address, long balancePoint, ClickListener clickListener){
        ExchangePointDialog selectNetDialog = new ExchangePointDialog();
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        args.putLong(BABLANCE,balancePoint);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.exchange_point_dialog_layout,null);
        address_et = view.findViewById(R.id.address_et);
        ImageView select_address_iv = view.findViewById(R.id.select_address_iv);
        points_et = view.findViewById(R.id.points_et);
        balance_point_tv = view.findViewById(R.id.balance_point_tv);
        arrival_points_tv = view.findViewById(R.id.arrival_points_tv);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        TextView sure_tv = view.findViewById(R.id.sure_tv);
        select_address_iv.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);
        points_et.addTextChangedListener(payNumTextWatcher);

        if(args != null){
            String address = args.getString(ADDRESS,"");
            long balancePoints = args.getLong(BABLANCE,0);
            if(!StrUtils.isEmpty(address)) {
                address_et.setText(address);
                address_et.setSelection(address_et.getText().toString().trim().length());
            }
            balance_point_tv.setText(balancePoints+"");
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
        if (id == R.id.select_address_iv) {
            if (clickListener != null) {
                clickListener.selectAddress(address -> {
                    address_et.setText(address);
                    address_et.setSelection(address_et.getText().toString().trim().length());
                });
            }
        }else if (id == R.id.cancel_tv) {
            dismiss();
        } else if (id == R.id.sure_tv) {
            if (clickListener != null) {
                String addressStr = address_et.getText().toString().trim();
                String pointStr = points_et.getText().toString().trim();
                if (!StrUtils.checkCurrAddress(addressStr)) {
                    ToastUtil.showCenterToast(getString(R.string.points_wrong_psw_tip));
                    return;
                }
                if (StrUtils.isEmpty(pointStr)) {
                    ToastUtil.showCenterToast(getString(R.string.points_please_input_exchange_points_tip));
                    return;
                }
                long points = Long.valueOf(pointStr);
                if (points <= 0) {
                    ToastUtil.showCenterToast(getString(R.string.points_please_input_exchange_points_tip));
                    return;
                }
                clickListener.onSure(addressStr, points);
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
            String str = s.toString();
            arrival_points_tv.setText(str);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public static class Builder{
        private String address;
        private long balancePoints;
        private ClickListener clickListener;

        public Builder setAddress(String address){
            this.address = address;
            return this;
        }

        public Builder setBalancePoints(long balancePoints) {
            this.balancePoints = balancePoints;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public ExchangePointDialog build(){
            return ExchangePointDialog.getInstance(address,balancePoints,clickListener);
        }
    }

    public interface ClickListener{
        void onSure(String toAddress, long points);
        void selectAddress(AddressCallBack addressCallBack);
    }

    public interface AddressCallBack{
        void seleted(String address);
    }
}
