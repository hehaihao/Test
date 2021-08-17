package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;

import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface TransactionContract {
    interface IModel {
        Observable<?> getCurrWallet();
    }
    interface IListModel {
        Observable<?> getTransactionList(String address,String contractAddress,String nftId,int page,int limit,int type);
    }

    interface IView extends BaseView {
        void getCurrWalletSuccess(Wallet_Main walletMain);
        void onLoadFail(String msg);
    }

    interface IListView extends BaseView {
        void getTransactionListSuccess(TransationResultBean transationResultBean,boolean isLoadMore);
        void onLoadFail(String msg);
    }
}
