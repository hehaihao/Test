package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class TransactionPresenter extends BasePresenter<TransactionContract.IView> {
    private  TransactionContract.IModel iModel;
    public TransactionPresenter(TransactionContract.IView baseView) {
        super(baseView);
        iModel = new TransactionModelImp();
    }

    /**
     * 获取当前选中钱包
     */
    public void getCurrWallet(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getCurrWallet(), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main walletMain) {
                baseView.getCurrWalletSuccess(walletMain);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
