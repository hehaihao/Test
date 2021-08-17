package com.xm6leefun.wallet_module.wallet_setting.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;

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
public class WalletSettingModelImpl implements WalletSettingContract.IModel{

    /**
     * 获取钱包列表
     * @return
     */
    @Override
    public Observable<?> getWalletList() {
        return Observable.create((ObservableOnSubscribe<List<Wallet_Main>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            RealmResults<Wallet_Main> walletMains = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
            List<Wallet_Main> walletMainList = realm.copyFromRealm(walletMains);
            emitter.onNext(walletMainList);
            emitter.onComplete();
        }));
    }
    /**
     * 根据地址获取钱包信息
     * @param address
     * @return
     */
    @Override
    public Observable<?> getWalletByAddress(String address) {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            if(walletMain != null) {
                Wallet_Main dc_mainList = realm.copyFromRealm(walletMain);
                emitter.onNext(dc_mainList);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    @Override
    public Observable<?> removeWallet(String address) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            if (walletMain != null) {
                walletMain.setDeletedStatus(1);
                walletMain.setChecked(false);
                walletMain.setIsBackup(0);
                emitter.onNext(isHaveData(realm));
                emitter.onComplete();
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

    @Override
    public Observable<?> modifyWalletName(String mCurrAddress, String name) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main first = realm.where(Wallet_Main.class).equalTo("address", mCurrAddress).findFirst();
            if (first != null) {
                first.setWalletName(name);
                emitter.onNext(name);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    @Override
    public Observable<?> setSelectedWalletQuickPay(String mCurrAddress, boolean toStatue) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main first = realm.where(Wallet_Main.class).equalTo("address", mCurrAddress).findFirst();
            if (first != null) {
                first.setQuickPay(toStatue);
                emitter.onNext(toStatue);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    /**
     * 查询提示语
     * @param mCurrAddress
     * @return
     */
    @Override
    public Observable<?> getPromptInfo(String mCurrAddress) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main first = realm.where(Wallet_Main.class).equalTo("address", mCurrAddress).findFirst();
            if (first != null) {
                String promptInfoStr = first.getPromptInfo();
                emitter.onNext(promptInfoStr);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }
}
