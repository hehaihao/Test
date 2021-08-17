package com.xm6leefun.wallet_module.points_property.property_manager.mvp;

import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.points_property.property_manager.api.PropertyManagerApiService;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class PropertyManagerModelImp implements PropertyManagerContract.IModel {
    private PropertyManagerApiService apiService;

    public PropertyManagerModelImp(PropertyManagerApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getList(String key) {
        return Observable.create((ObservableOnSubscribe<List<HomeDataResultBean.FtBean>>) emitter -> {
            RealmUtils.getRealm().executeTransaction(realm -> {
                Wallet_Main wallet_main = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
                if (wallet_main == null) {
                    wallet_main = realm.where(Wallet_Main.class).findFirst();
                    wallet_main.setChecked(true);
                }
                RealmResults<Points_Assets> assets;
                List<HomeDataResultBean.FtBean> list = new ArrayList<>();
                if(!StrUtils.isEmpty(key)) {
                    assets = wallet_main.getPoints_assets().where().like("token_name", "*" + key + "*").or().like("contract_address", "*" + key + "*").findAll().sort("position", Sort.DESCENDING);
                }else{
                    assets = wallet_main.getPoints_assets().where().findAll().sort("position", Sort.ASCENDING);
                }
                if (assets != null && assets.size() > 0){
                    for (Points_Assets asset:assets){
                        list.add(getFtBeanFromAsset(asset));
                    }
                }
                emitter.onNext(list);
                emitter.onComplete();
            });
        });
    }

    @Override
    public Observable<?> deleteList(List<HomeDataResultBean.FtBean> listBeans,String address) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<listBeans.size();i++){
            if(i==0) stringBuffer.append(listBeans.get(i).getId()+"");
            else stringBuffer.append(","+listBeans.get(i).getId());
        }
        return apiService.deleteFt(stringBuffer.toString(),address);
    }


    @Override
    public Observable<?> deleteDbList(List<HomeDataResultBean.FtBean> listBean,String address) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if (listBean != null && listBean.size() > 0) {
                RealmUtils.getRealm().executeTransaction(realm -> {
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
                    for (HomeDataResultBean.FtBean bean : listBean){
                        Points_Assets points_assets = wallet_main.getPoints_assets().where().equalTo("totalKey",address+bean.getId()).findFirst();
                        if (points_assets!=null){
                            points_assets.deleteFromRealm();
                        }
                    }
                    emitter.onNext("success");
                    emitter.onComplete();
                });
            }
        });

    }

    @Override
    public Observable<?> sortList(List<HomeDataResultBean.FtBean> listBean) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> {
            RealmUtils.getRealm().executeTransaction(realm -> {
                Wallet_Main wallet_main = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
                if (wallet_main == null) {
                    wallet_main = realm.where(Wallet_Main.class).findFirst();
                    wallet_main.setChecked(true);
                }
                String address = wallet_main.getAddress();
                if (listBean != null && listBean.size() > 0){
                    for (HomeDataResultBean.FtBean ftBean : listBean){
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
                    }
                }
                emitter.onNext("success");
                emitter.onComplete();
            });
        });
    }


    public HomeDataResultBean.FtBean getFtBeanFromAsset (Points_Assets points_assets){
        HomeDataResultBean.FtBean ftBean = new HomeDataResultBean.FtBean();
        ftBean.setId(points_assets.getId());
        ftBean.setToken_name(points_assets.getToken_name());
        ftBean.setToken_logo(points_assets.getToken_logo());
        ftBean.setNum(points_assets.getNum());
        ftBean.setContract_address(points_assets.getContract_address());
        return ftBean;
    }

}