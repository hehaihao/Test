package com.xm6leefun.wallet_module.wallet_setting.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.wallet.KeysInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface WalletSettingContract {
    interface IModel {
        Observable<?> getWalletList();
        Observable<?> getWalletByAddress(String address);
        Observable<?> removeWallet(String address);
        Observable<?> checkPsw(String address,String psw,String wrongTipStr);
        Observable<?> setFirstWalletChecked();
        Observable<?> modifyWalletName(String mCurrAddress, String name);
        Observable<?> setSelectedWalletQuickPay(String mCurrAddress, boolean toStatue);
        Observable<?> getPromptInfo(String mCurrAddress);
    }

    interface IView extends BaseView {
        void checkPswResult(boolean isPass, String psw,KeysInfo keysInfo,int intentType);
        void getWalletByAddressSuccess(Wallet_Main walletMain);
        void noWalletData();
        void onLoadFail(String msg);
        void setFirstWalletCheckedSuccess();
        void modifyWalletNameSuccess(String walletName);
        void setSelectedWalletQuickPaySuccess(boolean status);

        void getPromptInfoSuccess(String promptInfoStr);

        void getWalletListSuccess(List<Wallet_Main> walletMains);
    }
}
