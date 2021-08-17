package com.xm6leefun.wallet_module.backup.prikey.mvp;

import com.xm6leefun.common.base.BaseView;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface BackupPrikeyContract {
    interface IModel {
        Observable<?> getPriKey(String psw,String address);
    }

    interface IView extends BaseView {

    }
}
