package com.xm6leefun.wallet_module.home_wallet.mvp;

import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.home_wallet.api.HomeWalletApiService;
import com.xm6leefun.common.wallet.WalletUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class HomeWalletModelImp implements HomeWalletContract.IModel{
    private HomeWalletApiService apiService;

    public HomeWalletModelImp(HomeWalletApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getHomeData(String address) {
        return apiService.getIndexData(address);
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
    public Observable<?> getWalletList() {
        return Observable.create((ObservableOnSubscribe<List<Wallet_Main>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            RealmResults<Wallet_Main> walletMains = realm.where(Wallet_Main.class).equalTo("deletedStatus", 0).findAll();
            List<Wallet_Main> walletMainList = realm.copyFromRealm(walletMains);
            emitter.onNext(walletMainList);
            emitter.onComplete();
        }));
    }

    /**
     * 校验密码
     * @param psw
     * @return
     */
    @Override
    public Observable<?> checkPsw(String address,String psw,String wrongTipStr) {
        return Observable.create((ObservableOnSubscribe<KeysInfo>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main walletMain;
            if (StrUtils.isEmpty(address)) {
                walletMain = realm.where(Wallet_Main.class).equalTo("isChecked", true).findFirst();
            } else {
                walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            }
            if(walletMain != null) {
                String password = AESUtil.decrypt(ConstantValue.PRIVATE_KEY_AES_PWD, walletMain.getPlainPwd());
                if (StrUtils.isEmpty(password)) {//密码错误
                    emitter.onError(new Exception(wrongTipStr));
                }else{//验证通过
                    String keystoreJson = walletMain.getKeystore();
                    KeysInfo keysInfo = WalletUtil.importWalletByKeyStore(psw, keystoreJson);
                    if(keysInfo.getCode() == 200){//验证通过
                        emitter.onNext(keysInfo);
                        emitter.onComplete();
                    }else{
                        emitter.onError(new Exception(wrongTipStr));
                    }
                }
            }else{
                emitter.onError(new Exception("Empty_wallet_datas"));
            }
        }));
    }

    @Override
    public Observable<?> setAddress(String address, String lastAddress) {
        return Observable.create((ObservableOnSubscribe<Wallet_Main>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            Wallet_Main lastWalletMain = realm.where(Wallet_Main.class).equalTo("address", lastAddress).findFirst();
            Wallet_Main walletMain = realm.where(Wallet_Main.class).equalTo("address", address).findFirst();
            lastWalletMain.setChecked(false);//将上次选择的地址置为不选中
            walletMain.setChecked(true);//将本次选择的地址置为选中
            emitter.onNext(realm.copyFromRealm(walletMain));
            emitter.onComplete();
        }));
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
    public Observable<?> getDbPointsAssets(String address) {
        return Observable.create((ObservableOnSubscribe<List<HomeDataResultBean.FtBean>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
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
            //获取数据库中的列表，进行数据展示，这里需要从数据库中重新获取，因为后台返回的数据可能有重复
            List<HomeDataResultBean.FtBean> ftBeanList = new ArrayList<>();
            RealmResults<Points_Assets> list = wallet_main.getPoints_assets().where().findAll().sort("position", Sort.ASCENDING);
            for(Points_Assets points_assets : list){
                HomeDataResultBean.FtBean ftBean = new HomeDataResultBean.FtBean();
                ftBean.setContract_address(points_assets.getContract_address());
                ftBean.setId(points_assets.getId());
                ftBean.setNum(points_assets.getNum());
                ftBean.setToken_logo(points_assets.getToken_logo());
                ftBean.setToken_name(points_assets.getToken_name());
                ftBean.setTx_id(points_assets.getTx_id());
                ftBeanList.add(ftBean);
            }
            emitter.onNext(ftBeanList);
            emitter.onComplete();
        }));
    }

    @Override
    public Observable<?> getNftList(String address) {
        return apiService.getNftList(address);
    }
}
