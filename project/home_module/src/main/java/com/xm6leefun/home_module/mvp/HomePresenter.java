package com.xm6leefun.home_module.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.bean.AppVersionBean;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.home_module.api.HomeApiService;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class HomePresenter extends BasePresenter<HomeContract.IView> {
    private  HomeContract.IModel iModel;
    public HomePresenter(HomeContract.IView baseView) {
        super(baseView);
        iModel = new HomeModelImp(retrofitManager.createRs(HomeApiService.class));
    }

    /**
     * 获取首页ft和nft数据
     * @param address
     */
    public void getHomeData(String address){
        addDisposableObserveOnMain(iModel.getHomeData(address), new BaseObserver<BaseNftDataBean<HomeDataResultBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<HomeDataResultBean> o) {
                HomeDataResultBean homeDataResultBean = o.getResult();
                if(homeDataResultBean != null) {
                    baseView.getHomeDataSuccess(homeDataResultBean, address);
                    if(homeDataResultBean.getFt() != null && homeDataResultBean.getFt().size() > 0){
                        savePointsAssets(address,homeDataResultBean.getFt());
                    }
                }else baseView.getHomeDataFail("data_null",address);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getHomeDataFail(msg,address);
            }
        });
    }
    /**
     * 保存积分资产入库
     * @param address
     */
    private void savePointsAssets(String address, List<HomeDataResultBean.FtBean> ftBeans){
        addDisposableObserveOnMain(iModel.savePointsAssets(address,ftBeans), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main wallet_main) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 获取当前选中地址
     */
    public void getCurrWallet(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getCurrWallet(), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main walletMain) {
                baseView.getCurrWalletSuccess(walletMain);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    public void getAppVersion(){
        addDisposableObserveOnMain(iModel.getAppVersion(), new BaseObserver<BaseNftDataBean<AppVersionBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<AppVersionBean> o) {
                baseView.getAppVersionSuccess(o.getResult());
            }
            @Override
            public void onError(int code,String msg) {
                baseView.getAppVersionFail(msg);
            }
        });
    }
}
