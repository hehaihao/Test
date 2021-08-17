package com.xm6leefun.wallet_module.backup_tips.mvp;

import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.TimeUtil;
import com.xm6leefun.common.wallet.KeysInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021年3月26日14:34:37
 */
public class BackupTipsModelImpl implements BackupTipsContract.IModel{

    public BackupTipsModelImpl() {

    }

    @Override
    public Observable<?> saveWallet(CusCreateWalletBean cusCreateWalletBean) {
        return Observable.create((ObservableOnSubscribe<CusCreateWalletBean>) emitter -> {
            KeysInfo keysInfo = cusCreateWalletBean.getKeysInfo();
            if(keysInfo.getCode() == 200){
                //创建钱包并保存数据库
                RealmUtils.getRealm().executeTransaction(realm -> {
                    Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
                    if (walletMain != null) {
                        walletMain.setChecked(false);
                    }
                    saveDB(cusCreateWalletBean, realm);
                    emitter.onNext(cusCreateWalletBean);
                    emitter.onComplete();
                });
            }else{
                emitter.onError(new Exception(keysInfo.getMsg()));
            }
        });
    }

    /**
     * 入库
     * @param cusCreateWallet
     * @param realm
     */
    private void saveDB(CusCreateWalletBean cusCreateWallet, Realm realm) {
        String pwd = cusCreateWallet.getPwd();
        KeysInfo keysInfo = cusCreateWallet.getKeysInfo();
        String address = keysInfo.getAddress();
        Wallet_Main walletMain = realm.createObject(Wallet_Main.class,address);
        List<String> words = keysInfo.getWords();
        String wordsJson = new Gson().toJson(words);
        String wordsString = AESUtil.encrypt(pwd, wordsJson);
        walletMain.setIdWallet(cusCreateWallet.isFirst());//第一次创建的为身份钱包
        walletMain.setKeystore(keysInfo.getKeystore());
        walletMain.setWordsJson(wordsString);
        walletMain.setChecked(true);
        walletMain.setDeletedStatus(0);  // 0否  1是
        String encryptPwd = AESUtil.encrypt(ConstantValue.PRIVATE_KEY_AES_PWD, pwd);
        walletMain.setPlainPwd(encryptPwd);
        walletMain.setIsBackup(0);  // 0否  1是
        walletMain.setCreated(TimeUtil.getCurrentTimeStamps());
        walletMain.setModified(TimeUtil.getCurrentTimeStamps());
        walletMain.setPromptInfo(cusCreateWallet.getPromptInfo());
        walletMain.setWalletName(cusCreateWallet.getWalletName());
        walletMain.setQuickPay(false);
        walletMain.setTotalAssets(0L);
    }
}
