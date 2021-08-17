package com.xm6leefun.wallet_module.wallet_setting.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.wallet.KeysInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class WalletSettingPresenter extends BasePresenter<WalletSettingContract.IView> {
    private WalletSettingContract.IModel model;
    public WalletSettingPresenter(WalletSettingContract.IView baseView) {
        super(baseView);
        model = new WalletSettingModelImpl();
    }

    /**
     * 获取所有钱包列表
     */
    public void getWalletList(){
        //从数据库读取数据
        addDisposableObserveOnMain(model.getWalletList(), new BaseObserver<List<Wallet_Main>>(baseView,true) {
            @Override
            public void onSuccess(List<Wallet_Main> walletMains) {
                baseView.getWalletListSuccess(walletMains);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 根据地址获取钱包信息
     */
    public void getWalletByAddress(String address) {
        addDisposableObserveOnMain(model.getWalletByAddress(address), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main walletMain) {
                baseView.getWalletByAddressSuccess(walletMain);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 校验密码
     * @param address
     * @param psw
     */
    public void checkPsw(String address,String psw,int intentType,String wrongTipStr){
        addDisposableObserveOnMain(model.checkPsw(address,psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,psw,keysInfo,intentType);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,psw,null,intentType);
            }
        });
    }

    /**
     * 移除钱包
     * @param address
     */
    public void removeWallet(String address){
        addDisposableObserveOnMain(model.removeWallet(address), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean isHaveData) {
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
    public void setFirstWalletChecked(){
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

    /**
     * 修改钱包名
     * @param mCurrAddress
     * @param name
     */
    public void modifyWalletName(String mCurrAddress, String name) {
        addDisposableObserveOnMain(model.modifyWalletName(mCurrAddress,name), new BaseObserver<String>() {
            @Override
            public void onSuccess(String walletName) {
                baseView.modifyWalletNameSuccess(walletName);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 设置免密支付
     * @param mCurrAddress
     * @param toStatue
     */
    public void setSelectedWalletQuickPay(String mCurrAddress, boolean toStatue) {
        addDisposableObserveOnMain(model.setSelectedWalletQuickPay(mCurrAddress,toStatue), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean status) {
                baseView.setSelectedWalletQuickPaySuccess(status);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 查询密码提示词
     * @param mCurrAddress
     */
    public void getPromptInfo(String mCurrAddress) {
        addDisposableObserveOnMain(model.getPromptInfo(mCurrAddress), new BaseObserver<String>() {
            @Override
            public void onSuccess(String promptInfoStr) {
                baseView.getPromptInfoSuccess(promptInfoStr);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
