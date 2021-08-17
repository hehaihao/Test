package com.xm6leefun.wallet_module.importby.keystore.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class ImportByKeystorePresenter extends BasePresenter<ImportByKeystoreContract.IView> {
    private ImportByKeystoreContract.IModel model;
    public ImportByKeystorePresenter(ImportByKeystoreContract.IView baseView) {
        super(baseView);
        model = new ImportByKeystoreModelImpl();
    }

    /**keystore导入
     * @param pwd
     */
    public void importByKeystore(Context context, String walletName,String pwd, String privateKeyStr) {
        addDisposableObserveOnMain(model.importByKeystore(context, walletName,pwd, privateKeyStr), new BaseObserver<CusCreateWalletBean>(baseView,true) {
            @Override
            public void onSuccess(CusCreateWalletBean createWallet) {
                baseView.importByKeystoreSuccess(createWallet);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
