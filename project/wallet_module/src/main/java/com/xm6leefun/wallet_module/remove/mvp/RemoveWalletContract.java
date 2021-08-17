package com.xm6leefun.wallet_module.remove.mvp;

import android.content.Context;

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
public interface RemoveWalletContract {
    interface IModel {
        Observable<?> removeWallet(Context context,String address);
        Observable<?> checkPsw(String address,String psw,String wrongTipStr);
        Observable<?> setFirstWalletChecked();
    }

    interface IView extends BaseView {
        void checkPswResult(boolean isPass, String psw,KeysInfo keysInfo);
        void removeWalletSuccess();
        void noWalletData();
        void onLoadFail(String msg);
        void setFirstWalletCheckedSuccess();
    }
}
