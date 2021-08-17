package com.xm6leefun.wallet_module.create_wallet.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface CreateWalletContract {
    interface IModel {
        Observable<?> createWallet(boolean isFirst,String walletName,String promptInfo,String pwd);
    }

    interface IView extends BaseView {
        void createWalletSuccess(CusCreateWalletBean createWallet);
        void onLoadFail(String msg);
    }
}
