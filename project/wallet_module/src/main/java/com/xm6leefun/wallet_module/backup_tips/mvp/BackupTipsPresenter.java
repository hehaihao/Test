package com.xm6leefun.wallet_module.backup_tips.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021年3月26日14:36:03
 */
public class BackupTipsPresenter extends BasePresenter<BackupTipsContract.IView> {
    private BackupTipsContract.IModel model;
    public BackupTipsPresenter(BackupTipsContract.IView baseView) {
        super(baseView);
        model = new BackupTipsModelImpl();
    }

    /**
     * 保存钱包到数据库
     * @param cusCreateWalletBean
     */
    public void saveWallet(CusCreateWalletBean cusCreateWalletBean) {
        addDisposableObserveOnMain(model.saveWallet(cusCreateWalletBean), new BaseObserver<CusCreateWalletBean>(baseView,true) {
            @Override
            public void onSuccess(CusCreateWalletBean createWallet) {
                baseView.saveWalletSuccess(createWallet);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.saveWalletFail(msg);
            }
        });
    }
}
