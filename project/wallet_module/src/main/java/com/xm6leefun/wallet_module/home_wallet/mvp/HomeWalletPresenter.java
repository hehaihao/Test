package com.xm6leefun.wallet_module.home_wallet.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.home_wallet.api.HomeWalletApiService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class HomeWalletPresenter extends BasePresenter<HomeWalletContract.IView> {
    private  HomeWalletContract.IModel iModel;
    public HomeWalletPresenter(HomeWalletContract.IView baseView) {
        super(baseView);
        iModel = new HomeWalletModelImp(retrofitManager.createRs(HomeWalletApiService.class));
    }

    /**
     * 获取首页ft和nft数据
     * @param mWalletMain
     */
    public void getHomeData(Wallet_Main mWalletMain){
        if(mWalletMain == null) return;
        String requestAddress = mWalletMain.getAddress();
        addDisposableObserveOnMain(iModel.getHomeData(requestAddress), new BaseObserver<BaseNftDataBean<HomeDataResultBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<HomeDataResultBean> o) {
                HomeDataResultBean homeDataResultBean = o.getResult();
                if(homeDataResultBean != null) {
                    baseView.getHomeDataSuccess(homeDataResultBean, requestAddress);
                    if(homeDataResultBean.getFt() != null && homeDataResultBean.getFt().size() > 0){
                        savePointsAssets(requestAddress,homeDataResultBean.getFt());
                    }else{
                        getDbPointsAssets(requestAddress);//从数据库中获取积分信息
                    }
                }else {
                    baseView.getHomeDataFail("data_null", requestAddress);
                    getDbPointsAssets(requestAddress);//从数据库中获取积分信息
                }
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getHomeDataFail(msg,requestAddress);
                getDbPointsAssets(requestAddress);//从数据库中获取积分信息
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

    /**
     * 校验密码
     * @param address
     * @param psw
     */
    public void checkPsw(String address,String psw,String wrongTipStr){
        addDisposableObserveOnMain(iModel.checkPsw(address,psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,psw,keysInfo);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,psw,null);
            }
        });
    }

    /**
     * 获取所有钱包列表
     */
    public void getWalletList(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getWalletList(), new BaseObserver<List<Wallet_Main>>() {
            @Override
            public void onSuccess(List<Wallet_Main> walletMains) {
                List<Wallet_Main> idWalletList = new ArrayList<>();
                List<Wallet_Main> normalWalletList = new ArrayList<>();
                for(Wallet_Main wallet_main : walletMains){
                    if(wallet_main.isIdWallet())
                        idWalletList.add(wallet_main);
                    else
                        normalWalletList.add(wallet_main);
                }
                baseView.getWalletListSuccess(idWalletList,normalWalletList);
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

    /**
     * 保存积分资产入库
     * @param address
     */
    public void savePointsAssets(String address, List<HomeDataResultBean.FtBean> ftBeans){
        if(StrUtils.isEmpty(address)|| ftBeans == null || ftBeans.size() == 0) return;
        addDisposableObserveOnMain(iModel.savePointsAssets(address,ftBeans), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main wallet_main) {
                getDbPointsAssets(address);//从数据库中获取积分信息
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
    /**
     * 获取数据库数据,并更新UI
     * @param address
     */
    public void getDbPointsAssets(String address){
        addDisposableObserveOnMain(iModel.getDbPointsAssets(address), new BaseObserver<List<HomeDataResultBean.FtBean>>() {
            @Override
            public void onSuccess(List<HomeDataResultBean.FtBean> dbFtBeans) {
                baseView.getDbFtListSuccess(dbFtBeans,address);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getDbFtListFail(msg,address);
            }
        });
    }

    /**
     * 获取实物资产接口
     * @param address
     */
    public void getNftList(String address){
        addDisposableObserveOnMain(iModel.getNftList(address), new BaseObserver<BaseNftDataBean<List<PhysicalListBean>>>(baseView,true){
            @Override
            public void onSuccess(BaseNftDataBean<List<PhysicalListBean>> o) {
                if(o != null) baseView.getNftListSuccess(o.getResult());
                else baseView.getNftListFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getNftListFail(msg);
            }
        });
    }
}
