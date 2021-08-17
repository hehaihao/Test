package com.xm6leefun.wallet_module.home_wallet.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.common.wallet.KeysInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface HomeWalletContract {
    interface IModel {
        Observable<?> getHomeData(String address);
        Observable<?> getCurrWallet();
        Observable<?> getWalletList();
        Observable<?> checkPsw(String address,String psw,String wrongTipStr);
        Observable<?> setAddress(String address, String lastAddress);
        Observable<?> savePointsAssets(String address,List<HomeDataResultBean.FtBean> ftBeans);
        Observable<?> getNftList(String address);
        Observable<?> getDbPointsAssets(String address);
    }

    interface IView extends BaseView {
        void getHomeDataSuccess(HomeDataResultBean homeDataResultBean,String requestAddress);
        void getHomeDataFail(String msg,String requestAddress);
        void getDbFtListSuccess(List<HomeDataResultBean.FtBean> ftBeans,String requestAddress);
        void getDbFtListFail(String msg,String requestAddress);
        void checkPswResult(boolean isPass, String psw, KeysInfo keysInfo);
        void getCurrWalletSuccess(Wallet_Main walletMain);
        void getWalletListSuccess(List<Wallet_Main> idWalletList,List<Wallet_Main> normalWalletList);
        void setAddressSuccess(Wallet_Main walletMain);
        void getNftListSuccess(List<PhysicalListBean> physicalListBeans);
        void getNftListFail(String msg);
        void onLoadFail(String msg);
    }
}
