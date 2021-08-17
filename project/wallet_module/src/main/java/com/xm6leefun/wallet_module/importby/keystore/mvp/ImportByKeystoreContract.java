package com.xm6leefun.wallet_module.importby.keystore.mvp;

import android.content.Context;
import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface ImportByKeystoreContract {
    interface IModel {
        Observable<?> importByKeystore(Context context,String walletName, String pwd, String keystoreStr);
    }

    interface IView extends BaseView {
        void importByKeystoreSuccess(CusCreateWalletBean createWallet);
        void onLoadFail(String msg);
    }
}
