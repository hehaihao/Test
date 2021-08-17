package com.xm6leefun.wallet_module.reset_psw.mvp;

import android.content.Context;

import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class ResetPswModelImpl implements ResetPswContract.IModel{


    @Override
    public Observable<?> resetPwdByWord(Context context, String address, String wordsStr,String wordsPath, String pwd, String promptInfo) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            String[] mnemonic = wordsStr.split("\\s+");
            List<String> wordsList = Arrays.asList(mnemonic);
            // 0 助记词
            if (wordsList.size() < 12) {
                emitter.onError(new Exception(context.getString(R.string.mnemonic_confirm_mnemonic_is_lacking)));
            }else{
                WalletUtil.setKeyWords(wordsPath);//设置路径
                KeysInfo mKeysInfoBywords = WalletUtil.importWalletByWords(wordsList);
                if(mKeysInfoBywords.getCode() == 200){
                    if(mKeysInfoBywords.getAddress().equals(address)){//输入的助记词与当前地址相匹配
                        // 判断助记词是否正确
                        KeysInfo makeKeyStore = WalletUtil.makeKeyStore(mKeysInfoBywords.getPrivateKey(), pwd);
                        makeKeyStore.setWords(wordsList);
                        CusCreateWalletBean cusCreateWalletBean = new CusCreateWalletBean();
                        cusCreateWalletBean.setType(0);//助记词
                        cusCreateWalletBean.setWhere(1);//导入
                        cusCreateWalletBean.setPromptInfo(promptInfo);//密码提示
                        cusCreateWalletBean.setPwd(pwd);//密码
                        cusCreateWalletBean.setKeysInfo(makeKeyStore);
                        //创建钱包并保存数据库
                        RealmUtils.getRealm().executeTransaction(realm -> {
                            Wallet_Main walletMain_byAddress = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
                            if (walletMain_byAddress != null) {//当前地址已存在数据库
                                updateWalletInfo(walletMain_byAddress,cusCreateWalletBean);
                                emitter.onNext(true);
                                emitter.onComplete();
                            }else{
                                emitter.onError(new Exception(context.getString(R.string.import_wallet_mnemonic_err)));
                            }
                        });
                    }else{
                        emitter.onError(new Exception(mKeysInfoBywords.getMsg()));
                    }
                }else{
                    emitter.onError(new Exception(mKeysInfoBywords.getMsg()));
                }
            }
        });
    }

    @Override
    public Observable<?> resetPwdByPrivateKey(Context context,String address, String privateKey, String pwd, String promptInfo) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            // 1 私钥
            KeysInfo keysInfoByPrivateKey = WalletUtil.importWalletByPrivateKey(privateKey);
            // 判断私钥是否正确
            if (keysInfoByPrivateKey.getCode() == 200) {
                KeysInfo makeKeyStore = WalletUtil.makeKeyStore(keysInfoByPrivateKey.getPrivateKey(), pwd);
                if(makeKeyStore.getAddress().equals(address)) {//输入的私钥与当前地址相匹配
                    RealmUtils.getRealm().executeTransaction(realm -> {
                        if (StrUtils.isWalletAddress(address)) {
                            CusCreateWalletBean cusCreateWalletBean = new CusCreateWalletBean();
                            cusCreateWalletBean.setType(1);//私钥
                            cusCreateWalletBean.setWhere(1);//导入
                            cusCreateWalletBean.setPromptInfo(promptInfo);//密码提示
                            cusCreateWalletBean.setPwd(pwd);//密码
                            cusCreateWalletBean.setKeysInfo(makeKeyStore);
                            Wallet_Main mRm_WalletOC = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
                            // 导入钱包时，此钱包有可能存在
                            if (mRm_WalletOC != null){//钱包存在，更新信息
                                updateWalletInfo(mRm_WalletOC,cusCreateWalletBean);
                                emitter.onNext(true);
                                emitter.onComplete();
                            }else{
                                emitter.onError(new Exception("Empty_wallet_datas"));
                            }
                        }else{//无效的钱包地址
                            emitter.onError(new Exception(context.getString(R.string.import_by_invalid_address)));
                        }
                    });
                }else{
                    emitter.onError(new Exception(context.getString(R.string.import_by_private_key_error)));
                }
            } else {
                emitter.onError(new Exception(context.getString(R.string.import_by_private_key_error)));
            }
        });
    }

    /**
     * 更新已存在的钱包信息
     * @param walletMain
     * @param cusCreateWallet
     */
    private void updateWalletInfo(Wallet_Main walletMain, CusCreateWalletBean cusCreateWallet) {
        String pwd = cusCreateWallet.getPwd();
        KeysInfo keysInfo = cusCreateWallet.getKeysInfo();
        String address = keysInfo.getAddress();
        cusCreateWallet.setAddress(address);
        List<String> words = keysInfo.getWords();
        if(words != null && words.size()>0){
            String wordsJson = new Gson().toJson(words);
            String wordsString = AESUtil.encrypt(pwd, wordsJson);
            walletMain.setWordsJson(wordsString);
        }else{
            String encodeWordsJson = walletMain.getWordsJson();
            if(!StrUtils.isEmpty(encodeWordsJson)) {//本来拥有助记词，则修改密码后需要重新加密
                String oldPassword = AESUtil.decrypt(ConstantValue.PRIVATE_KEY_AES_PWD, walletMain.getPlainPwd());
                String decodeWordsJson = AESUtil.decrypt(oldPassword, encodeWordsJson);//获取原始数据
                //用新密码重新加密
                String wordsString = AESUtil.encrypt(pwd, decodeWordsJson);
                walletMain.setWordsJson(wordsString);
            }
        }
        walletMain.setKeystore(keysInfo.getKeystore());
        String encryptPwd = AESUtil.encrypt(ConstantValue.PRIVATE_KEY_AES_PWD, pwd);
        walletMain.setPlainPwd(encryptPwd);
        walletMain.setPromptInfo(cusCreateWallet.getPromptInfo());
    }
}
