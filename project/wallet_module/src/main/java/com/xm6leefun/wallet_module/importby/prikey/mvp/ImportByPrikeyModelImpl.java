package com.xm6leefun.wallet_module.importby.prikey.mvp;

import android.content.Context;

import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.utils.TimeUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class ImportByPrikeyModelImpl implements ImportByPrikeyContract.IModel{

    @Override
    public Observable<?> importByPrikey(Context context,String walletName, String promptInfo, String pwd, String privateKeyStr) {
        return Observable.create((ObservableOnSubscribe<CusCreateWalletBean>) emitter -> {
            // 1 私钥
            KeysInfo keysInfoByPrivateKey = WalletUtil.importWalletByPrivateKey(privateKeyStr);
            // 判断私钥是否正确
            if (keysInfoByPrivateKey.getCode() == 200) {
                KeysInfo makeKeyStore = WalletUtil.makeKeyStore(keysInfoByPrivateKey.getPrivateKey(), pwd);
                dealRealm(context,makeKeyStore, promptInfo, pwd, walletName,emitter);
            } else {
                emitter.onError(new Exception(context.getString(R.string.import_by_private_key_error)));
            }
        });
    }

    /**
     * 处理数据库业务
     * @param mKeyStore
     * @param promptInfo
     * @param pwd
     * @param walletName
     * @param emitter
     */
    private void dealRealm(Context context,KeysInfo mKeyStore, String promptInfo, String pwd, String walletName, ObservableEmitter<CusCreateWalletBean> emitter) {
        RealmUtils.getRealm().executeTransaction(realm -> {
            final String walletAddress = mKeyStore.getAddress();
            if (StrUtils.isWalletAddress(walletAddress)) {
                CusCreateWalletBean cusCreateWalletBean = new CusCreateWalletBean();
                cusCreateWalletBean.setWalletName(walletName);
                cusCreateWalletBean.setType(1);//私钥
                cusCreateWalletBean.setWhere(1);//导入
                cusCreateWalletBean.setPromptInfo(promptInfo);//密码提示
                cusCreateWalletBean.setPwd(pwd);//密码
                cusCreateWalletBean.setKeysInfo(mKeyStore);
                Wallet_Main mRm_WalletOC = realm.where(Wallet_Main.class).equalTo("address", walletAddress).findFirst();
                // 导入钱包时，此钱包有可能存在
                if (mRm_WalletOC == null) {//不存在，则创建
                    createWallet(cusCreateWalletBean,realm);
                    emitter.onNext(cusCreateWalletBean);
                    emitter.onComplete();
                }else{//钱包存在，更新信息
                    if(mRm_WalletOC.getDeletedStatus() == 1){//已删除，已被用户“删除”的地址复原
                        updateWalletInfo(mRm_WalletOC,cusCreateWalletBean,realm);
                        emitter.onNext(cusCreateWalletBean);
                        emitter.onComplete();
                    }else{//未删除，不能重复导入相同的地址
                        emitter.onError(new Exception(context.getString(R.string.import_address_exist)));
                    }
                }
            }else{//无效的钱包地址
                emitter.onError(new Exception(context.getString(R.string.import_by_invalid_address)));
            }
        });
    }

    /**
     * 更新已存在的钱包信息
     * @param walletMain
     * @param cusCreateWallet
     * @param realm
     */
    private void updateWalletInfo(Wallet_Main walletMain, CusCreateWalletBean cusCreateWallet, Realm realm) {
        // 设置原本选中的地址为未选中状态
        Wallet_Main wallet_main = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
        if (wallet_main != null) {
            wallet_main.setChecked(false);
        }
        String pwd = cusCreateWallet.getPwd();
        KeysInfo keysInfo = cusCreateWallet.getKeysInfo();
        String address = keysInfo.getAddress();
        cusCreateWallet.setAddress(address);
        walletMain.setWordsJson("");
        walletMain.setKeystore(keysInfo.getKeystore());
        walletMain.setChecked(true);
        walletMain.setDeletedStatus(0);  // 0否  1是
        walletMain.setIdWallet(false);
        String encryptPwd = AESUtil.encrypt(ConstantValue.PRIVATE_KEY_AES_PWD, pwd);
        walletMain.setPlainPwd(encryptPwd);
        walletMain.setModified(TimeUtil.getCurrentTimeStamps());
        walletMain.setPromptInfo(cusCreateWallet.getPromptInfo());
        walletMain.setWalletName(cusCreateWallet.getWalletName());
    }

    /**
     * 新建钱包入库
     * @param cusCreateWallet
     * @param realm
     */
    private void createWallet(CusCreateWalletBean cusCreateWallet, Realm realm) {
        // 设置原本选中的地址为未选中状态
        Wallet_Main wallet_main = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
        if (wallet_main != null) {
            wallet_main.setChecked(false);
        }
        String pwd = cusCreateWallet.getPwd();
        KeysInfo keysInfo = cusCreateWallet.getKeysInfo();
        String address = keysInfo.getAddress();
        cusCreateWallet.setAddress(address);
        Wallet_Main walletMain = realm.createObject(Wallet_Main.class,address);
        List<String> words = keysInfo.getWords();
        if(words != null && words.size()>0) {
            String wordsJson = new Gson().toJson(words);
            String wordsString = AESUtil.encrypt(pwd, wordsJson);
            walletMain.setWordsJson(wordsString);
        }
        walletMain.setKeystore(keysInfo.getKeystore());
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
