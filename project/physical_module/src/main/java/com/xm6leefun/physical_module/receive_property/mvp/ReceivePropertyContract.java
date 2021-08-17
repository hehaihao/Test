package com.xm6leefun.physical_module.receive_property.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;

import io.reactivex.Observable;


public interface ReceivePropertyContract {


    interface IModel {
        Observable<?> getCurrWallet();
    }


    interface IView extends BaseView {
        void getCurrWalletSuccess(Wallet_Main walletMain);

        void onLoadFail(String msg);
    }
}
