package com.xm6leefun.wallet_module.backup_tips.mvp;

import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021年3月26日14:34:47
 */
public interface BackupTipsContract {
    interface IModel {
        Observable<?> saveWallet(CusCreateWalletBean cusCreateWalletBean);
    }

    interface IView extends BaseView {
        void saveWalletSuccess(CusCreateWalletBean cusCreateWalletBean);
        void saveWalletFail(String errorMessage);
    }
}
