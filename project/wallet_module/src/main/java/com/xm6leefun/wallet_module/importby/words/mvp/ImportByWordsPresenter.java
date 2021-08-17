package com.xm6leefun.wallet_module.importby.words.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class ImportByWordsPresenter extends BasePresenter<ImportByWordsContract.IView> {
    private ImportByWordsContract.IModel model;
    public ImportByWordsPresenter(ImportByWordsContract.IView baseView) {
        super(baseView);
        model = new ImportByWordsModelImpl();
    }

    /**助记词导入
     * @param walletName
     * @param promptInfo
     * @param pwd
     */
    public void importByWords(Context context,String wordPath,String walletName, String promptInfo, String pwd, String wordsStr) {
        addDisposableObserveOnMain(model.importByWords(context,wordPath,walletName, promptInfo, pwd,wordsStr), new BaseObserver<CusCreateWalletBean>(baseView,true) {
            @Override
            public void onSuccess(CusCreateWalletBean createWallet) {
                baseView.importByWordsSuccess(createWallet);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
