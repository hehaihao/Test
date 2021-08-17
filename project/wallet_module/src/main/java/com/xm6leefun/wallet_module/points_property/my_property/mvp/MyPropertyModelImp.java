package com.xm6leefun.wallet_module.points_property.my_property.mvp;

import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmResults;
import io.realm.Sort;

public class MyPropertyModelImp implements MyPropertyContract.IModel {


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


    private HomeDataResultBean.FtBean getFtBeanFromAsset (Points_Assets points_assets){
        HomeDataResultBean.FtBean ftBean = new HomeDataResultBean.FtBean();
        ftBean.setId(points_assets.getId());
        ftBean.setToken_name(points_assets.getToken_name());
        ftBean.setToken_logo(points_assets.getToken_logo());
        ftBean.setNum(points_assets.getNum());
        ftBean.setContract_address(points_assets.getContract_address());
        return ftBean;
    }
}