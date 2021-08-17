package com.xm6leefun.physical_module.physical_detail.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;

import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface PhysicalDetailContract {
    interface IModel {
        Observable<?> checkPsw(String psw,String wrongTipStr);
        Observable<?> getPhysicalDetail(long id);
        Observable<?> physicalTran(String action);
        Observable<?> dealPhysicalTranData(String privateKey, PhysicalDetailBean.NftInfoBean nftInfoBean,String toAddress);
    }

    interface IView extends BaseView {
        void getListSuccess(List<PhysicalDetailBean.TransferBean> physicalTranListBeans);
        void getDetailSuccess(PhysicalDetailBean.NftInfoBean nftInfoBean);
        void getDetailFail(String msg);
        void checkPswResult(boolean isPass, PhysicalDetailBean.NftInfoBean nftInfoBean,String toAddress, String psw, KeysInfo keysInfo);
        void onLoadFail(String msg);
        void physicalTranSuccess();
    }
}
