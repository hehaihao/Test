package com.xm6leefun.points_module.zwd_login;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;

import androidx.annotation.NonNull;
import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 项目：BlockUser
 * 文件描述：验证码登录页面fragment
 * 作者：ljj
 * 创建时间：2020/12/16
 */
public class CodeLoginFragment extends BaseComFragment {

    @BindView(R2.id.ed_phone)
    EditText ed_phone;
    @BindView(R2.id.ed_code)
    EditText ed_code;
    @BindView(R2.id.tv_send_code)
    TextView tv_send_code;

    private OnContentListener onContentListener;

    public static CodeLoginFragment getInstance(){
        CodeLoginFragment fragment = new CodeLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnContentListener){
            onContentListener = (OnContentListener) context;
        }else{
            throw new RuntimeException("LoginActivity must implements OnContentListener");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_code_login;
    }

    @Override
    protected void initView() {
        setPhoneEditTextListener();
        tv_send_code.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.tv_send_code) {// 发送验证码
            initSendSms();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(timer != null){
            timer.cancel();
        }
    }

    /**
     * 设置验证码按钮监听
     */
    private void setPhoneEditTextListener() {
        RxTextView.textChanges(ed_phone)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    String phone = charSequence.toString();
                    RxView.enabled(tv_send_code).call(!StrUtils.isEmpty(phone) && phone.length() == 11);
                });
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            tv_send_code.setText(getString(R.string.get_code_count,l / 1000));
            tv_send_code.setEnabled(false);
        }

        @Override
        public void onFinish() {
            tv_send_code.setEnabled(true);
            tv_send_code.setText(getResources().getString(R.string.get_code));
        }
    };

    private void initSendSms() {
        String phone = ed_phone.getText().toString().trim();
        if (StrUtils.isEmpty(phone) || phone.length() != 11) {
            tv_send_code.setEnabled(false);
            return;
        }
        if (StrUtils.isEmpty(phone)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.phone_is_null));
            return;
        }
        //发送验证码
        onContentListener.sendSms(phone, () -> timer.start());
    }

    protected String getPhone(){
        if(ed_phone != null){
            String phone = ed_phone.getText().toString();
            return phone;
        }
        return null;
    }
    protected String getCode(){
        if(ed_code != null){
            String code = ed_code.getText().toString();
            return code;
        }
        return null;
    }

    interface OnContentListener {
        void sendSms(String phone, SendSmsCallBack callBack);
    }
    public interface SendSmsCallBack{
        void onSuccess();
    }
}
