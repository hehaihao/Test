package com.xm6leefun.wallet_module.importby.prikey.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class ImportByPrikeyPresenter extends BasePresenter<ImportByPrikeyContract.IView> {
    private ImportByPrikeyContract.IModel model;
    public ImportByPrikeyPresenter(ImportByPrikeyContract.IView baseView) {
        super(baseView);
        model = new ImportByPrikeyModelImpl();
    }

    /**私钥导入
     * @param walletName
     * @param promptInfo
     * @param pwd
     */
    public void importByPrikey(Context context,String walletName, String promptInfo, String pwd, String privateKeyStr) {
        addDisposableObserveOnMain(model.importByPrikey(context, walletName, promptInfo, pwd, privateKeyStr), new BaseObserver<CusCreateWalletBean>(baseView,true) {
            @Override
            public void onSuccess(CusCreateWalletBean createWallet) {
                baseView.importByPrikeySuccess(createWallet);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
