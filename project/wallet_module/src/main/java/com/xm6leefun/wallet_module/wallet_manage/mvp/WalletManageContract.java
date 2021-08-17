package com.xm6leefun.wallet_module.wallet_manage.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;
import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface WalletManageContract {
    interface IModel {
        Observable<?> getWalletList();
    }

    interface IView extends BaseView {
        void getWalletListSuccess(List<Wallet_Main> idWalletList,List<Wallet_Main> normalWalletList);
        void onLoadFail(String msg);
    }
}
