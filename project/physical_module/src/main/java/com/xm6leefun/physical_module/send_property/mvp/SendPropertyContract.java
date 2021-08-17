package com.xm6leefun.physical_module.send_property.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import io.reactivex.Observable;

/**
 * 契约
 */
public interface SendPropertyContract {
    interface IModel {
        Observable<?> checkPsw(String psw,String wrongTipStr);
        Observable<?> physicalTran(String action);
        Observable<?> dealPhysicalTranData(String privateKey, String txId,String contract_address, String toAddress,long points);
    }

    interface IView extends BaseView {
        void checkPswResult(boolean isPass, String txId, String contract_address,String toAddress,long points, String psw, KeysInfo keysInfo);
        void onLoadFail(String msg);
        void physicalTranSuccess();
    }
}
