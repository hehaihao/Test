package com.xm6leefun.points_module.zwd_login.mvp;


import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.export_module.busbean.LoginResultBean;
import com.xm6leefun.points_module.zwd_login.CodeLoginFragment;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2020/11/24 16:16
 */
public interface LoginContract {
    interface IModel {
        Observable<?> sendCode(String mobile, String type);
        Observable<?> pswLogin(String mobile, String code);
        Observable<?> login(String mobile, String code);
        Observable<?> wxLogin(String openId, String nickName, String headPortrait, String unionid);
    }

    interface IView extends BaseView {
        void sendCodeSuccess(CodeLoginFragment.SendSmsCallBack sendSmsCallBack);
        void sendCodeFail(String errorMessage);
        void loginSuccess(LoginResultBean loginResultBean);
        void loginFail(String errorMessage);
    }
}
