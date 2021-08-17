package com.xm6leefun.wallet_module.reset_psw.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class ResetPswPresenter extends BasePresenter<ResetPswContract.IView> {
    private ResetPswContract.IModel model;
    public ResetPswPresenter(ResetPswContract.IView baseView) {
        super(baseView);
        model = new ResetPswModelImpl();
    }

    /**
     * 通过助记词重置
     */
    public void resetPwdByWord(Context context, String address, String wordsStr, String wordsPath,String pwd, String promptInfo) {
        addDisposableObserveOnMain(model.resetPwdByWord(context, address, wordsStr, wordsPath, pwd, promptInfo), new BaseObserver<Boolean>(baseView,true) {
            @Override
            public void onSuccess(Boolean b) {
                baseView.resetPwdSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 通过私钥重置
     */
    public void resetPwdByPrivateKey(Context context,String address, String privateKey, String pwd, String promptInfo) {
        addDisposableObserveOnMain(model.resetPwdByPrivateKey(context, address, privateKey, pwd, promptInfo), new BaseObserver<Boolean>(baseView,true) {
            @Override
            public void onSuccess(Boolean b) {
                baseView.resetPwdSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
