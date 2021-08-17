package com.xm6leefun.wallet_module.words_make.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface WordsMakeContract {
    interface IModel {
        Observable<?> getCurrWallet(String address);
    }

    interface IView extends BaseView {
        void getCurrWalletSuccess(Wallet_Main walletMain);
        void onLoadFail(String msg);
    }
}
