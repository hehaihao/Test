package com.xm6leefun.wallet_module.remove.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.wallet.KeysInfo;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class RemoveWalletPresenter extends BasePresenter<RemoveWalletContract.IView> {
    private RemoveWalletContract.IModel model;
    public RemoveWalletPresenter(RemoveWalletContract.IView baseView) {
        super(baseView);
        model = new RemoveWalletModelImpl();
    }

    /**
     * 校验密码
     * @param address
     * @param psw
     */
    public void checkPsw(String address,String psw,String wrongTipStr){
        addDisposableObserveOnMain(model.checkPsw(address,psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,psw,keysInfo);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,psw,null);
            }
        });
    }

    /**
     * 移除钱包
     * @param address
     */
    public void removeWallet(Context context,String address){
        addDisposableObserveOnMain(model.removeWallet(context,address), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean isHaveData) {
                baseView.removeWalletSuccess();
                if(isHaveData){
                    setFirstWalletChecked();
                }else{
                    baseView.noWalletData();
                }
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 设置第一个钱包为选中状态
     */
    private void setFirstWalletChecked(){
        addDisposableObserveOnMain(model.setFirstWalletChecked(), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean isHaveData) {
                baseView.setFirstWalletCheckedSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
