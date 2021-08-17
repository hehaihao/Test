package com.xm6leefun.points_module.points_exchange.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface PointsExchangeContract {
    interface IModel {
        Observable<?> getCurrWallet();
        Observable<?> getIntegralList(String mobile);
        Observable<?> getAllWalletList();
        Observable<?> setAddress(String address, String lastAddress);
    }

    interface IView extends BaseView {
        void getCurrWalletSuccess(Wallet_Main walletMain);
        void getListSuccess(PointsExchangeResultBean bean);
        void getListFail(String msg);
        void getAllWalletListSuccess(List<Wallet_Main> walletMains);
        void setAddressSuccess(Wallet_Main walletMain);
        void onLoadFail(String msg);
    }
}
