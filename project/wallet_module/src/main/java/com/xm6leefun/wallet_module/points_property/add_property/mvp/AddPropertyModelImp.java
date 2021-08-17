package com.xm6leefun.wallet_module.points_property.add_property.mvp;

import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.points_property.add_property.api.AddPointsPropertyApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddPropertyModelImp implements AddPropertyContract.IModel {

    private AddPointsPropertyApiService apiService;


    public AddPropertyModelImp(AddPointsPropertyApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<?> getList(String key) {
        return apiService.getHotProperty(key,"");
    }

    @Override
    public Observable<?> addProperty(long id, String address) {
        return apiService.addProperty(id,address);
    }

    @Override
    public Observable<?> updatePointsAssets(HomeDataResultBean.FtBean ftBean,String address) {
        return Observable.create((ObservableOnSubscribe<List<Long>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
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
            Points_Assets points_assets = wallet_main.getPoints_assets().where().equalTo("totalKey",address+ftBean.getId()).findFirst();
            if(points_assets == null){
                points_assets = new Points_Assets(address+ftBean.getId());
                points_assets.setId(ftBean.getId());
                points_assets.setNum(ftBean.getNum());
                points_assets.setToken_logo(ftBean.getToken_logo());
                points_assets.setToken_name(ftBean.getToken_name());
                points_assets.setContract_address(ftBean.getContract_address());
                points_assets.setPosition(ftBean.getPosition());
                points_assets.setTx_id(ftBean.getTx_id());
                wallet_main.getPoints_assets().add(points_assets);//添加数据
            }else{
                points_assets.setId(ftBean.getId());
                points_assets.setNum(ftBean.getNum());
                points_assets.setToken_logo(ftBean.getToken_logo());
                points_assets.setToken_name(ftBean.getToken_name());
                points_assets.setContract_address(ftBean.getContract_address());
                points_assets.setPosition(ftBean.getPosition());
                points_assets.setTx_id(ftBean.getTx_id());
            }
            RealmList<Points_Assets> pointsAssets = wallet_main.getPoints_assets();
            if(pointsAssets != null && pointsAssets.size() > 0) {
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < pointsAssets.size(); i++) {
                    ids.add(pointsAssets.get(i).getId());
                }
                emitter.onNext(ids);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("assets_data_empty"));
            }
        }));
    }

    @Override
    public Observable<?> getAssetsFromDataBase(String address) {
        return Observable.create((ObservableOnSubscribe<List<Long>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
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
            RealmList<Points_Assets> pointsAssets = wallet_main.getPoints_assets();
            if(pointsAssets != null && pointsAssets.size() > 0) {
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < pointsAssets.size(); i++) {
                    ids.add(pointsAssets.get(i).getId());
                }
                emitter.onNext(ids);
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("assets_data_empty"));
            }
        }));
    }
}