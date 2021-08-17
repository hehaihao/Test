package com.xm6leefun.wallet_module.words_confirm.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface WordsConfirmContract {
    interface IModel {
        Observable<?> verify(CusCreateWalletBean createWalletBean, KeysInfo keysInfo, String psw, List<CusMnemonic> wordsList);
    }

    interface IView extends BaseView {
        void verifySuccess();
        void verifyFail(String msg);
    }
}
