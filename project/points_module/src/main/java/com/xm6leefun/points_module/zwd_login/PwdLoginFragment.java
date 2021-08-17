package com.xm6leefun.points_module.zwd_login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import butterknife.BindView;

/**
 * 项目：BlockUser
 * 文件描述：密码登录页面fragment
 * 作者：ljj
 * 创建时间：2020/12/16
 */
public class PwdLoginFragment extends BaseComFragment implements View.OnClickListener {

    @BindView(R2.id.ed_phone)
    EditText ed_phone;
    @BindView(R2.id.ed_pwd)
    EditText ed_pwd;

    private View view;

    public static PwdLoginFragment getInstance(){
        PwdLoginFragment fragment = new PwdLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pwd_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args != null){
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    protected String getPhone(){
        if(ed_phone != null){
            String phone = ed_phone.getText().toString();
            return phone;
        }
        return null;
    }
    protected String getPwd(){
        if(ed_pwd != null){
            String pwd = ed_pwd.getText().toString();
            return pwd;
        }
        return null;
    }
}
