package com.xm6leefun.points_module.zwd_login.mvp;


import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.export_module.busbean.LoginResultBean;
import com.xm6leefun.points_module.zwd_login.CodeLoginFragment;
import com.xm6leefun.points_module.zwd_login.api.LoginApiService;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/11/25 14:40
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> {
    private LoginContract.IModel model;
    public LoginPresenter(LoginContract.IView baseView) {
        super(baseView);
        model = new LoginModelImpl(retrofitManager.createZwdRs(LoginApiService.class));
    }

    public void sendCode(String mobile, String type, CodeLoginFragment.SendSmsCallBack sendSmsCallBack) {
        addDisposableObserveOnMain(model.sendCode(mobile, type), new BaseObserver<BaseDataBean<String>>(baseView,true) {
            @Override
            public void onSuccess(BaseDataBean<String> o) {
                baseView.sendCodeSuccess(sendSmsCallBack);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.sendCodeFail(msg);
            }
        });
    }

    public void login(String mobile, String code) {
        addDisposableObserveOnMain(model.login(mobile, code), new BaseObserver<BaseDataBean<LoginResultBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseDataBean<LoginResultBean> o) {
                //登录类型
                SharePreferenceUtil.setInt(AppAllKey.LOGIN_TYPE,AppAllKey.CODE_LOGIN_TYPE);
                baseView.loginSuccess(o.getRecord());
            }

            @Override
            public void onError(int code, String msg) {
                baseView.loginFail(msg);
            }
        });
    }

    public void pswLogin(String mobile, String code) {
        addDisposableObserveOnMain(model.pswLogin(mobile, code), new BaseObserver<BaseDataBean<LoginResultBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseDataBean<LoginResultBean> o) {
                //登录类型
                SharePreferenceUtil.setInt(AppAllKey.LOGIN_TYPE,AppAllKey.PWD_LOGIN_TYPE);
                baseView.loginSuccess(o.getRecord());
            }

            @Override
            public void onError(int code, String msg) {
                baseView.loginFail(msg);
            }
        });
    }

    public void wxLogin(String openId, String nickName, String headPortrait, String unionid) {
        addDisposableObserveOnMain(model.wxLogin(openId, nickName, headPortrait,unionid), new BaseObserver<BaseDataBean<LoginResultBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseDataBean<LoginResultBean> o) {
                //登录类型
                SharePreferenceUtil.setInt(AppAllKey.LOGIN_TYPE,AppAllKey.WX_LOGIN_TYPE);
                SharePreferenceUtil.setString(AppAllKey.UNION_ID,unionid);
                baseView.loginSuccess(o.getRecord());
            }

            @Override
            public void onError(int code, String msg) {
                baseView.loginFail(msg);
            }
        });
    }
}
