package com.xm6leefun.poketok.mvp;

import android.app.Activity;

import com.hailong.biometricprompt.fingerprint.FingerprintCallback;
import com.hailong.biometricprompt.fingerprint.FingerprintVerifyManager;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class SplashPresenter extends BasePresenter<SplashContract.IView> {
    private  SplashContract.IModel iModel;
    public SplashPresenter(SplashContract.IView baseView) {
        super(baseView);
        iModel = new SplashModelImp();
    }

    /**
     * 获取当前数据库中的地址
     */
    public void checkWallet(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.checkWallet(), new BaseObserver<List<Wallet_Main>>() {
            @Override
            public void onSuccess(List<Wallet_Main> walletMains) {
                if(walletMains != null && walletMains.size() > 0)
                    baseView.checkWalletSuccess(true);
                else
                    baseView.checkWalletSuccess(false);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    public void checkTouchId(Activity activity) {
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.checkTouchId(), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean isOpen) {
                if(isOpen) {
                    FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(activity);
                    builder.callback(new FingerprintCallback() {
                        @Override
                        public void onHwUnavailable() {

                        }

                        @Override
                        public void onNoneEnrolled() {

                        }

                        @Override
                        public void onSucceeded() {
                            baseView.isOpenTouchId(isOpen);
                        }

                        @Override
                        public void onFailed() {

                        }

                        @Override
                        public void onUsepwd() {

                        }

                        @Override
                        public void onCancel() {
                            baseView.onTouchIdCancel();
                        }
                    });
                    builder.build();
                }else{
                    baseView.isOpenTouchId(isOpen);
                }
            }

            @Override
            public void onError(int code, String msg) {
                baseView.isOpenTouchId(false);
            }
        });
    }
}
