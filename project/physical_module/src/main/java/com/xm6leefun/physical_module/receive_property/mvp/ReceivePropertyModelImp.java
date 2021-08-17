package com.xm6leefun.physical_module.receive_property.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class ReceivePropertyModelImp implements ReceivePropertyContract.IModel {


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