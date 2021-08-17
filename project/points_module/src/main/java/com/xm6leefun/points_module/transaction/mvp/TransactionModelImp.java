package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class TransactionModelImp implements TransactionContract.IModel{

    @Override
    public Observable<?> getCurrWallet() {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main checkedMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            if (checkedMain == null) {
                checkedMain = realm.where(Wallet_Main.class).findFirst();
                checkedMain.setChecked(true);
            }
            Wallet_Main walletMain = realm.copyFromRealm(checkedMain);
            emitter.onNext(walletMain);
            emitter.onComplete();
        }));
    }
}
