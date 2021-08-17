package com.xm6leefun.points_module.points_exchange.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.points_module.points_exchange.api.PointsExchangApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmResults;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class PointsExchangeModelImp implements PointsExchangeContract.IModel{
    private PointsExchangApiService apiService;

    public PointsExchangeModelImp(PointsExchangApiService apiService) {
        this.apiService = apiService;
    }

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

    @Override
    public Observable<?> getIntegralList(String mobile) {
        return apiService.getIntegralList(mobile);
    }

    @Override
    public Observable<?> getAllWalletList() {
        return Observable.create((ObservableOnSubscribe<List<Wallet_Main>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            RealmResults<Wallet_Main> walletMains = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
            List<Wallet_Main> walletMainList = realm.copyFromRealm(walletMains);
            emitter.onNext(walletMainList);
            emitter.onComplete();
        }));
    }

    @Override
    public Observable<?> setAddress(String address, String lastAddress) {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main lastWalletMain = realm.where(Wallet_Main.class).equalTo("address", lastAddress).findFirst();
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            lastWalletMain.setChecked(false);//将上次选择的地址置为不选中
            walletMain.setChecked(true);//将本次选择的地址置为选中
            emitter.onNext(realm.copyFromRealm(walletMain));
            emitter.onComplete();
        }));
    }
}
