package com.xm6leefun.setting_module.address.list.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.db.bean.AddressBook;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface AddressListContract {
    interface IModel {
        Observable<?> getAddressList();
    }

    interface IView extends BaseView {
        void getAddressListSuccess(List<AddressBook> addressBooks);
        void onLoadFail(String msg);
    }
}
