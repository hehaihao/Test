package com.xm6leefun.wallet_module.importby.words.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface ImportByWordsContract {
    interface IModel {
        Observable<?> importByWords(Context context,String wordPath,String walletName, String promptInfo, String pwd, String wordsStr);
    }

    interface IView extends BaseView {
        void importByWordsSuccess(CusCreateWalletBean createWallet);
        void onLoadFail(String msg);
    }
}
