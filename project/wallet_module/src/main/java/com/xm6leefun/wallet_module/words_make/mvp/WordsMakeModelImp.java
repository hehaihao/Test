package com.xm6leefun.wallet_module.words_make.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.StrUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class WordsMakeModelImp implements WordsMakeContract.IModel{
    @Override
    public Observable<?> getCurrWallet(String address) {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain;
            if (StrUtils.isEmpty(address)) {
                walletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            } else {
                walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            }
            if (walletMain == null) {
                walletMain = realm.where(Wallet_Main.class).findFirst();
                walletMain.setChecked(true);
            }
            Wallet_Main wallet_main = realm.copyFromRealm(walletMain);
            emitter.onNext(wallet_main);
            emitter.onComplete();
        }));
    }
}
