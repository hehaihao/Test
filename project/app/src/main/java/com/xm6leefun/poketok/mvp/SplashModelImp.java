package com.xm6leefun.poketok.mvp;

import android.app.Activity;

import com.hailong.biometricprompt.fingerprint.FingerprintCallback;
import com.hailong.biometricprompt.fingerprint.FingerprintVerifyManager;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmResults;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class SplashModelImp implements SplashContract.IModel{
    @Override
    public Observable<?> checkWallet() {
        return Observable.create((ObservableOnSubscribe<List<Wallet_Main>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            RealmResults<Wallet_Main> mainRealmResults = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
            List<Wallet_Main> wallet_mains = realm.copyFromRealm(mainRealmResults);
            if(wallet_mains != null && wallet_mains.size() > 0){
                //获取当前选中的钱包
                Wallet_Main checkedMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
                if (checkedMain == null) {//所有没有选中的钱包，默认选中第一个
                    checkedMain = realm.where(Wallet_Main.class).findFirst();
                    checkedMain.setChecked(true);
                }
            }
            emitter.onNext(wallet_mains);
            emitter.onComplete();
        }));
    }

    @Override
    public Observable<?> checkTouchId() {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            boolean isOpenTouchId = SharePreferenceUtil.getBoolean(AppAllKey.TOUCH_ID_STATUS);
            emitter.onNext(isOpenTouchId);
            emitter.onComplete();
        });
    }
}
