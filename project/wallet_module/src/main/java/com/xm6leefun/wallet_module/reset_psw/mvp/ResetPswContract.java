package com.xm6leefun.wallet_module.reset_psw.mvp;

import android.content.Context;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface ResetPswContract {
    interface IModel {
        Observable<?> resetPwdByWord(Context context, String address, String wordsStr, String wordsPath,String pwd, String promptInfo);
        Observable<?> resetPwdByPrivateKey(Context context,String address, String privateKey, String pwd, String promptInfo);
    }

    interface IView extends BaseView {
        void resetPwdSuccess();
        void onLoadFail(String msg);
    }
}
