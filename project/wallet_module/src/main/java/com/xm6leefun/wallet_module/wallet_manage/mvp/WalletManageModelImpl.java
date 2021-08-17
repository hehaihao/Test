package com.xm6leefun.wallet_module.wallet_manage.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class WalletManageModelImpl implements WalletManageContract.IModel{

    @Override
    public Observable<?> getWalletList() {
        return Observable.create((ObservableOnSubscribe<List<Wallet_Main>>) emitter -> RealmUtils.getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Wallet_Main> walletMains = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
                if(walletMains.size() > 0) {
                    List<Wallet_Main> dc_mainList = realm.copyFromRealm(walletMains);
                    emitter.onNext(dc_mainList);
                    emitter.onComplete();
                }else{
                    emitter.onError(new Exception("Empty_wallet_datas"));
                }
            }
        }));
    }
}
