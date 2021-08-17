package com.xm6leefun.wallet_module.words_confirm.mvp;


import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.utils.TimeUtil;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class WordsConfirmModelImpl implements WordsConfirmContract.IModel{

    @Override
    public Observable<?> verify(CusCreateWalletBean createWalletBean, KeysInfo keysInfo, String psw, List<CusMnemonic> wordsList) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            // 设置keystore
            List<String> wordStrings = new ArrayList<>();
            for (int i = 0; i < wordsList.size(); i++){
                wordStrings.add(wordsList.get(i).getWord());
            }
            KeysInfo mOcKeysInfoBywords = WalletUtil.importWalletByWords(wordStrings);
            // 判断用户输入的助记词列表生成的model是否与上个界面传值过来的model三要素一致
            if (mOcKeysInfoBywords != null) {
                String address = mOcKeysInfoBywords.getAddress();
                String privateKey = mOcKeysInfoBywords.getPrivateKey();
                if (keysInfo.getAddress().equals(address) && keysInfo.getPrivateKey().equals(privateKey) && keysInfo.getWords().equals(wordStrings)){
                    //使用已知私钥和密码生成keyStore
                    KeysInfo makePrivateKeyKeystore = WalletUtil.makeKeyStore(mOcKeysInfoBywords.getPrivateKey(), psw);
                    List<String> words = mOcKeysInfoBywords.getWords();
                    String wordsJson = new Gson().toJson(words);
                    String wordsString = AESUtil.encrypt(psw, wordsJson);
                    if (makePrivateKeyKeystore.getKeystore() != null && !StrUtils.isEmpty(wordsString)) {
                        String walletAddress = makePrivateKeyKeystore.getAddress();
                        if (StrUtils.checkCurrAddress(walletAddress)) {
                            //数据库，备份
                            RealmUtils.getRealm().executeTransaction(realm -> {
                                Wallet_Main selectWalletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
                                if (createWalletBean.getWhere() == 2) {  //  2 备份
                                    if (selectWalletMain != null) {
                                        selectWalletMain.setIsBackup(1);  // 0否  1是
                                    }
                                }else{//创建
                                    if (selectWalletMain != null) {
                                        selectWalletMain.setChecked(false);
                                    }
                                    Wallet_Main mWalletMain = realm.where(Wallet_Main.class).equalTo("address", walletAddress).findFirst();
                                    if (mWalletMain == null) {//创建钱包时，此钱包必定不存在
                                        saveDB(createWalletBean,realm);
                                    }
                                }
                                emitter.onNext("");
                                emitter.onComplete();
                            });
                        } else {
                            //无效地址
                            emitter.onError(new Exception("无效地址"));
                        }
                    }
                }
            } else {
                    //助记词有误
                emitter.onError(new Exception("助记词有误"));
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
        walletMain.setIsBackup(1);  // 0否  1是
        walletMain.setCreated(TimeUtil.getCurrentTimeStamps());
        walletMain.setModified(TimeUtil.getCurrentTimeStamps());
        walletMain.setPromptInfo(cusCreateWallet.getPromptInfo());
        walletMain.setWalletName(cusCreateWallet.getWalletName());
        walletMain.setQuickPay(false);
        walletMain.setTotalAssets(0L);
    }
}
