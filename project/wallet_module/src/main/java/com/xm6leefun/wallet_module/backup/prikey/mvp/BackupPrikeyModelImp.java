package com.xm6leefun.wallet_module.backup.prikey.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class BackupPrikeyModelImp implements BackupPrikeyContract.IModel{
    @Override
    public Observable<?> getPriKey(String psw,String address) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            String keystore = walletMain.getKeystore();
            KeysInfo keysInfo = WalletUtil.importWalletByKeyStore(psw, keystore);
            if(keysInfo.getCode() == 200){
                emitter.onNext(keysInfo.getPrivateKey());
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("err"));
            }
        }));
    }
}
