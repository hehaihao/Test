package com.xm6leefun.points_module.points_exchange.mvp;

import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.points_module.points_exchange.api.PointsExchangApiService;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class PointsExchangePresenter extends BasePresenter<PointsExchangeContract.IView> {
    private  PointsExchangeContract.IModel iModel;
    public PointsExchangePresenter(PointsExchangeContract.IView baseView) {
        super(baseView);
        iModel = new PointsExchangeModelImp(retrofitManager.createZwdRs(PointsExchangApiService.class));
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

    /**
     * 获取积分列表
     */
    public void getList(String mobile){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getIntegralList(mobile), new BaseObserver<BaseDataBean<PointsExchangeResultBean>>(baseView) {
            @Override
            public void onSuccess(BaseDataBean<PointsExchangeResultBean> o) {
                PointsExchangeResultBean bean = o.record;
                if(bean != null)
                    baseView.getListSuccess(bean);
                else
                    baseView.getListFail("null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getListFail(msg);
            }
        });
    }

    /**
     * 获取所有钱包列表
     */
    public void getAllWalletList(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getAllWalletList(), new BaseObserver<List<Wallet_Main>>() {
            @Override
            public void onSuccess(List<Wallet_Main> walletMains) {
                baseView.getAllWalletListSuccess(walletMains);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 设置选中钱包
     * @param address
     */
    public void setAddress(String address,String lastAddress){
        addDisposableObserveOnMain(iModel.setAddress(address,lastAddress), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main walletMain) {
                baseView.setAddressSuccess(walletMain);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
