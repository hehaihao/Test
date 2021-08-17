package com.xm6leefun.points_module.zwd_login.mvp;


import com.xm6leefun.points_module.zwd_login.api.LoginApiService;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/11/25 14:40
 */
public class LoginModelImpl implements LoginContract.IModel{
    private LoginApiService loginApiService;

    public LoginModelImpl(LoginApiService loginApiService) {
        this.loginApiService = loginApiService;
    }

    @Override
    public Observable<?> sendCode(String mobile, String type) {
        return loginApiService.getNoteCode(mobile, type);
    }

    @Override
    public Observable<?> pswLogin(String mobile, String psw) {
        return loginApiService.pswLogin(true,mobile,psw);
    }

    @Override
    public Observable<?> login(String mobile, String code) {
        return loginApiService.login(mobile, code);
    }

    @Override
    public Observable<?> wxLogin(String openId, String nickName, String headPortrait, String unionid) {
        return loginApiService.wxLogin(openId, nickName, headPortrait,unionid);
    }
}
