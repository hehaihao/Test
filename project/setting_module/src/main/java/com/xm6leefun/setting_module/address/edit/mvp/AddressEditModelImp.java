package com.xm6leefun.setting_module.address.edit.mvp;

import com.xm6leefun.common.db.bean.AddressBook;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.TimeUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class AddressEditModelImp implements AddressEditContract.IModel{

    @Override
    public Observable<?> editAddress(String lastAddress, String address, String name, String desc) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            AddressBook selectAddress = realm.where(AddressBook.class).equalTo("address",lastAddress).findFirst();
            if (selectAddress != null) selectAddress.deleteFromRealm();//先删除后插入更新
            AddressBook addressBook = realm.createObject(AddressBook.class, address);
            long currentTime = TimeUtil.getCurrentTimeStamps();
            addressBook.setCreated(currentTime);
            addressBook.setModified(currentTime);
            addressBook.setPerson(name);
            addressBook.setRemark(desc);
            emitter.onNext("");
            emitter.onComplete();
        }));
    }

    @Override
    public Observable<?> addAddress(String address, String name, String desc,String addressExistsStr) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            AddressBook findAddress = realm.where(AddressBook.class).equalTo("address",address).findFirst();
            if(findAddress == null){
                AddressBook addressBook = realm.createObject(AddressBook.class, address);
                long currentTime = TimeUtil.getCurrentTimeStamps();
                addressBook.setCreated(currentTime);
                addressBook.setModified(currentTime);
                addressBook.setPerson(name);
                addressBook.setRemark(desc);
                emitter.onNext("");
                emitter.onComplete();
            }else{
                emitter.onError(new Exception(addressExistsStr));
            }
        }));
    }

    @Override
    public Observable<?> deleteAddress(String address) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            AddressBook selectAddress = realm.where(AddressBook.class).equalTo("address",address).findFirst();
            if (selectAddress != null) {
                selectAddress.deleteFromRealm();//删除数据
                emitter.onNext("");
                emitter.onComplete();
            }else{
                emitter.onError(new Exception("empty_address_data"));
            }
        }));
    }
}
