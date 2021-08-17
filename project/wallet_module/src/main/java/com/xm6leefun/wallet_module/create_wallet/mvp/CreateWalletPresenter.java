package com.xm6leefun.wallet_module.create_wallet.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class CreateWalletPresenter extends BasePresenter<CreateWalletContract.IView> {
    private CreateWalletContract.IModel model;
    public CreateWalletPresenter(CreateWalletContract.IView baseView) {
        super(baseView);
        model = new CreateWalletModelImpl();
    }

    /**创建钱包
     * @param walletName
     * @param promptInfo
     * @param pwd
     */
    public void createWallet(boolean isFirst,String walletName,String promptInfo,String pwd) {
        addDisposableObserveOnMain(model.createWallet(isFirst,walletName, promptInfo, pwd), new BaseObserver<CusCreateWalletBean>(baseView,true) {
            @Override
            public void onSuccess(CusCreateWalletBean createWallet) {
                baseView.createWalletSuccess(createWallet);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
