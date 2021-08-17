package com.xm6leefun.home_module.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.bean.AppVersionBean;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface HomeContract {
    interface IModel {
        Observable<?> getHomeData(String address);
        Observable<?> savePointsAssets(String address,List<HomeDataResultBean.FtBean> ftBeans);
        Observable<?> getCurrWallet();

        Observable<?> getAppVersion();
    }

    interface IView extends BaseView {
        void getHomeDataSuccess(HomeDataResultBean homeDataResultBean,String address);
        void getHomeDataFail(String msg,String address);
        void getCurrWalletSuccess(Wallet_Main walletMain);
        void onLoadFail(String msg);

        void getAppVersionSuccess(AppVersionBean appVersionBean);
        void getAppVersionFail(String errorMessage);
    }
}
