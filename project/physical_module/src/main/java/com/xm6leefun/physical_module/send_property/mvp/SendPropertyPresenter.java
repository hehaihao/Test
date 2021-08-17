package com.xm6leefun.physical_module.send_property.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;
import com.xm6leefun.physical_module.send_property.api.SendPhysicalApiService;


public class SendPropertyPresenter extends BasePresenter<SendPropertyContract.IView> {

    private SendPropertyContract.IModel model;

    public SendPropertyPresenter(SendPropertyContract.IView baseView) {
        super(baseView);
        model = new SendPropertyModelImp(retrofitManager.createRs(SendPhysicalApiService.class));
    }

    /**
     * 检验密码
     * @param txId
     * @param toAddress
     * @param psw
     * @param wrongTipStr
     */
    public void checkPsw(String txId, String contract_address,String toAddress, long points,String psw, String wrongTipStr){
        addDisposableObserveOnMain(model.checkPsw(psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,txId,contract_address,toAddress,points,psw,keysInfo);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,txId,contract_address,toAddress,points,psw,null);
            }
        });
    }

    /**
     * 资产转移前處理數據
     * @param privateKey
     * @param txId
     * @param toAddress
     */
    public void physicalTran(String privateKey, String txId, String contract_address,String toAddress,long points){
        addDisposableObserveOnMain(model.dealPhysicalTranData(privateKey,txId,contract_address,toAddress,points), new BaseObserver<String>(baseView,true) {
            @Override
            public void onSuccess(String action) {
                subPhysicalTran(action);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 资产转移
     */
    private void subPhysicalTran(String action){
        addDisposableObserveOnMain(model.physicalTran(action), new BaseObserver<BaseNftDataBean<String>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<String> o) {
                baseView.physicalTranSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}