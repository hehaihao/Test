package com.xm6leefun.home_module.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.bean.AppVersionBean;
import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.home_module.api.HomeApiService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmList;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class HomeModelImp implements HomeContract.IModel{

    private HomeApiService apiService;

    public HomeModelImp(HomeApiService apiService) {

        this.apiService = apiService;
    }

    @Override
    public Observable<?> getHomeData(String address) {
        return apiService.getIndexData(address);
    }

    @Override
    public Observable<?> savePointsAssets(String address,List<HomeDataResultBean.FtBean> ftBeans) {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main wallet_main;
            if(StrUtils.isEmpty(address)){
                wallet_main = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            }else{
                wallet_main = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            }
            if (wallet_main == null) {
                wallet_main = realm.where(Wallet_Main.class).findFirst();
                wallet_main.setChecked(true);
            }
            //更新数据库数据
            for(HomeDataResultBean.FtBean ftBean : ftBeans){
                Points_Assets points_assets = wallet_main.getPoints_assets().where().equalTo("totalKey",address+ftBean.getId()).findFirst();
                if(points_assets == null){
                    points_assets = new Points_Assets(address+ftBean.getId());
                    points_assets.setId(ftBean.getId());
                    points_assets.setNum(ftBean.getNum());
                    points_assets.setToken_logo(ftBean.getToken_logo());
                    points_assets.setToken_name(ftBean.getToken_name());
                    points_assets.setContract_address(ftBean.getContract_address());
                    points_assets.setTx_id(ftBean.getTx_id());
                    wallet_main.getPoints_assets().add(points_assets);//添加数据
                }else{
                    points_assets.setId(ftBean.getId());
                    points_assets.setNum(ftBean.getNum());
                    points_assets.setToken_logo(ftBean.getToken_logo());
                    points_assets.setToken_name(ftBean.getToken_name());
                    points_assets.setContract_address(ftBean.getContract_address());
                    points_assets.setTx_id(ftBean.getTx_id());
                }
            }
            Wallet_Main walletMain = realm.copyFromRealm(wallet_main);
            emitter.onNext(walletMain);
            emitter.onComplete();
        }));
    }

    @Override
    public Observable<?> getCurrWallet() {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main checkedMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            if (checkedMain == null) {
                checkedMain = realm.where(Wallet_Main.class).findFirst();
                checkedMain.setChecked(true);
            }
            Wallet_Main walletMain = realm.copyFromRealm(checkedMain);
            emitter.onNext(walletMain);
            emitter.onComplete();
        }));
    }
    @Override
    public Observable<?> getAppVersion() {
        return apiService.getAppVersionInfo("android");
    }

}
