package com.xm6leefun.setting_module.address.list.mvp;

import com.xm6leefun.common.db.bean.AddressBook;
import com.xm6leefun.common.db.utils.RealmUtils;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.realm.RealmResults;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class AddressListModelImp implements AddressListContract.IModel{
    @Override
    public Observable<?> getAddressList() {
        return Observable.create((ObservableOnSubscribe<List<AddressBook>>) emitter -> RealmUtils.getRealm().executeTransaction(realm -> {
            RealmResults<AddressBook> addressBooks = RealmUtils.getRealm().where(AddressBook.class).findAll();
            List<AddressBook> addressBookList = realm.copyFromRealm(addressBooks);
            emitter.onNext(addressBookList);
            emitter.onComplete();
        }));
    }
}
