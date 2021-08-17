package com.xm6leefun.poketok.mvp;

import android.app.Activity;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface SplashContract {
    interface IModel {
        Observable<?> checkWallet();
        Observable<?> checkTouchId();
    }

    interface IView extends BaseView {
        void onTouchIdCancel();
        void isOpenTouchId(boolean isOpen);
        void checkWalletSuccess(boolean hasWallet);
        void onLoadFail(String msg);
    }
}
