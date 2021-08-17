package com.xm6leefun.wallet_module.importby.prikey.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface ImportByPrikeyContract {
    interface IModel {
        Observable<?> importByPrikey(Context context,String walletName, String promptInfo, String pwd, String privateKeyStr);
    }

    interface IView extends BaseView {
        void importByPrikeySuccess(CusCreateWalletBean createWallet);
        void onLoadFail(String msg);
    }
}
