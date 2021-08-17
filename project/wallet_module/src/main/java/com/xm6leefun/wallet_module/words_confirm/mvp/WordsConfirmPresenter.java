package com.xm6leefun.wallet_module.words_confirm.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class WordsConfirmPresenter extends BasePresenter<WordsConfirmContract.IView> {
    private WordsConfirmContract.IModel model;
    public WordsConfirmPresenter(WordsConfirmContract.IView baseView) {
        super(baseView);
        model = new WordsConfirmModelImpl();
    }

    public void verify(CusCreateWalletBean createWalletBean,KeysInfo keysInfo, String psw, List<CusMnemonic> wordsList, String loadingStr) {
        addDisposableObserveOnMain(model.verify(createWalletBean,keysInfo,psw, wordsList), new BaseObserver<String>(baseView,true,loadingStr) {
            @Override
            public void onSuccess(String s) {
                baseView.verifySuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.verifyFail(msg);
            }
        });
    }
}
