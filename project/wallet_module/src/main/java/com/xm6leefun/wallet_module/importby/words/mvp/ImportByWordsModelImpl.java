package com.xm6leefun.wallet_module.importby.words.mvp;

import android.content.Context;

import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.TimeUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class ImportByWordsModelImpl implements ImportByWordsContract.IModel{

    @Override
    public Observable<?> importByWords(Context context,String wordPath,String walletName, String promptInfo, String pwd, String wordsStr) {
        return Observable.create((ObservableOnSubscribe<CusCreateWalletBean>) emitter -> {
            String[] mnemonic = wordsStr.split("\\s+");
            List<String> wordsList = Arrays.asList(mnemonic);
            // 0 助记词
            if (wordsList.size() < 12) {
                emitter.onError(new Exception(context.getString(R.string.mnemonic_confirm_mnemonic_is_lacking)));
            }else{
                WalletUtil.setKeyWords(wordPath);//设置路径
                KeysInfo mKeysInfoBywords = WalletUtil.importWalletByWords(wordsList);
                if(mKeysInfoBywords.getCode() == 200){
                    String address = mKeysInfoBywords.getAddress();
                    // 判断助记词是否正确
                    KeysInfo makeKeyStore = WalletUtil.makeKeyStore(mKeysInfoBywords.getPrivateKey(), pwd);
                    makeKeyStore.setWords(wordsList);
                    CusCreateWalletBean cusCreateWalletBean = new CusCreateWalletBean();
                    cusCreateWalletBean.setWalletName(walletName);
                    cusCreateWalletBean.setType(0);//助记词
                    cusCreateWalletBean.setWhere(1);//导入
                    cusCreateWalletBean.setPromptInfo(promptInfo);//密码提示
                    cusCreateWalletBean.setPwd(pwd);//密码
                    cusCreateWalletBean.setKeysInfo(makeKeyStore);
                    //创建钱包并保存数据库
                    RealmUtils.getRealm().executeTransaction(realm -> {
                        Wallet_Main walletMain_byAddress = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
                        if (walletMain_byAddress != null) {//当前地址已存在数据库
                            if(walletMain_byAddress.getDeletedStatus() == 1){//已删除，已被用户“删除”的地址复原
                                updateWalletInfo(walletMain_byAddress,cusCreateWalletBean,realm);
                                emitter.onNext(cusCreateWalletBean);
                                emitter.onComplete();
                            }else{//未删除，不能重复导入相同的地址
                                emitter.onError(new Exception(context.getString(R.string.import_address_exist)));
                            }
                        }else{
                            createWallet(cusCreateWalletBean, realm);
                            emitter.onNext(cusCreateWalletBean);
                            emitter.onComplete();
                        }
                    });
                }else{
                    emitter.onError(new Exception(mKeysInfoBywords.getMsg()));
                }
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
        List<String> words = keysInfo.getWords();
        String wordsJson = new Gson().toJson(words);
        String wordsString = AESUtil.encrypt(pwd, wordsJson);
        walletMain.setKeystore(keysInfo.getKeystore());
        walletMain.setWordsJson(wordsString);
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
