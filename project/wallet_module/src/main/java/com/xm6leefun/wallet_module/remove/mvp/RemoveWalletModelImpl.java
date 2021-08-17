package com.xm6leefun.wallet_module.remove.mvp;

import android.content.Context;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;
import com.xm6leefun.wallet_module.R;

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
public class RemoveWalletModelImpl implements RemoveWalletContract.IModel{

    @Override
    public Observable<?> removeWallet(Context context,String address) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            if (walletMain != null) {
                if(walletMain.isIdWallet()){//拥有多个钱包时，身份钱包必须最后一个才能移除，此处不可移除
                    emitter.onError(new Exception(context.getString(R.string.id_wallet_remove_err)));
                }else {
                    walletMain.setDeletedStatus(1);
                    walletMain.setChecked(false);
                    walletMain.setIsBackup(0);
                    emitter.onNext(isHaveData(realm));
                    emitter.onComplete();
                }
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    /**
     * 是否还有钱包数据
     * @param realm
     * @return
     */
    private boolean isHaveData(Realm realm) {
        RealmResults<Wallet_Main> dcMainRealmResults = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
        return dcMainRealmResults.size() > 0;
    }

    /**
     * 校验密码
     * @param psw
     * @return
     */
    @Override
    public Observable<?> checkPsw(String address,String psw,String wrongTipStr) {
        return Observable.create((ObservableOnSubscribe<KeysInfo>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain;
            if (StrUtils.isEmpty(address)) {
                walletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            } else {
                walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            }
            if(walletMain != null) {
                String password = AESUtil.decrypt(ConstantValue.PRIVATE_KEY_AES_PWD, walletMain.getPlainPwd());
                if (StrUtils.isEmpty(password)) {//密码错误
                    emitter.onError(new Exception(wrongTipStr));
                }else{//验证通过
                    String keystoreJson = walletMain.getKeystore();
                    KeysInfo keysInfo = WalletUtil.importWalletByKeyStore(psw, keystoreJson);
                    if(keysInfo.getCode() == 200){//验证通过
                        emitter.onNext(keysInfo);
                        emitter.onComplete();
                    }else{
                        emitter.onError(new Exception(wrongTipStr));
                    }
                }
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    @Override
    public Observable<?> setFirstWalletChecked() {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main select = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            if(select != null){
                select.setChecked(false);
            }
            Wallet_Main first = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findFirst();
            if (first != null) {
                first.setChecked(true);
                emitter.onNext(true);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }
}
