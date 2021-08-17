package com.xm6leefun.wallet_module.words_make.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class WordsMakePresenter extends BasePresenter<WordsMakeContract.IView> {
    private  WordsMakeContract.IModel iModel;
    public WordsMakePresenter(WordsMakeContract.IView baseView) {
        super(baseView);
        iModel = new WordsMakeModelImp();
    }

    /**
     * 获取当前选中地址
     */
    public void getCurrWallet(String address){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getCurrWallet(address), new BaseObserver<Wallet_Main>() {
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
