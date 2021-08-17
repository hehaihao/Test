package com.xm6leefun.physical_module.send_property.mvp;

import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.physical_module.send_property.api.SendPhysicalApiService;

import org.onlychain.bean.SubChainOutBean;
import org.onlychain.bean.SubChainTransactionBean;
import org.onlychain.utils.ChainSerializeUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 发送资产业务实现
 */
public class SendPropertyModelImp implements SendPropertyContract.IModel {

    private SendPhysicalApiService apiService;

    public SendPropertyModelImp(SendPhysicalApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> checkPsw(String psw, String wrongTipStr) {
        return Observable.create((ObservableOnSubscribe<KeysInfo>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain;
            walletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();

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
    public Observable<?> physicalTran(String action) {
        return apiService.nftTransfer(action);
    }

    /**
     * 请求钱处理账户和获取nonce等操作
     * @param privateKey
     * @param txId
     * @param toAddress
     * @return
     */
    @Override
    public Observable<?> dealPhysicalTranData(String privateKey, String txId, String contract_address,String toAddress,long points) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            SubChainTransactionBean subChainTransactionBean = new SubChainTransactionBean(privateKey);
            subChainTransactionBean.setContractAddress(contract_address);
            List<SubChainOutBean> outBeans = new ArrayList<>();
            SubChainOutBean outBean = new SubChainOutBean();
            outBean.setValue(points);
            outBean.setAddress(toAddress);
            outBean.setToken(txId);
            outBeans.add(outBean);
            subChainTransactionBean.setSubChainVountList(outBeans);
            //提交action
            String action = ChainSerializeUtils.getSerializeResult(subChainTransactionBean);
            emitter.onNext(action);
            emitter.onComplete();
        });
    }
}