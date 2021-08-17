package com.xm6leefun.wallet_module.create_wallet.mvp;

import com.google.gson.Gson;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.TimeUtil;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;

/**
 * @Description: 业务逻辑处理
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:05
 */
public class CreateWalletModelImpl implements CreateWalletContract.IModel{

    @Override
    public Observable<?> createWallet(boolean isFirst,String walletName,String promptInfo,String pwd) {
        return Observable.create((ObservableOnSubscribe<CusCreateWalletBean>) emitter -> {
            //助记词
            CusCreateWalletBean cusCreateWalletBean = makeWords(walletName, promptInfo, pwd);
            KeysInfo keysInfo = cusCreateWalletBean.getKeysInfo();
            if(keysInfo.getCode() == 200){
                cusCreateWalletBean.setFirst(isFirst);
                cusCreateWalletBean.setAddress(keysInfo.getAddress());
                emitter.onNext(cusCreateWalletBean);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception(keysInfo.getMsg()));
            }
        });
    }

    /**
     * 生成助记词
     * @param walletName
     * @param promptInfo
     * @param pwd
     * @return
     */
    private CusCreateWalletBean makeWords(String walletName,String promptInfo,String pwd) {
        CusCreateWalletBean cusCreateWalletBean = new CusCreateWalletBean();
        cusCreateWalletBean.setWalletName(walletName);
        cusCreateWalletBean.setType(0);//助记词
        cusCreateWalletBean.setWhere(0);//创建
        cusCreateWalletBean.setPromptInfo(promptInfo);//密码提示
        cusCreateWalletBean.setPwd(pwd);//密码
        KeysInfo mKeysInfoBywords = WalletUtil.makeWords();
        KeysInfo keysInfo = WalletUtil.makeKeyStore(mKeysInfoBywords.getPrivateKey(), pwd);
        mKeysInfoBywords.setKeystore(keysInfo.getKeystore());
        cusCreateWalletBean.setKeysInfo(mKeysInfoBywords);
        return cusCreateWalletBean;
    }
}
