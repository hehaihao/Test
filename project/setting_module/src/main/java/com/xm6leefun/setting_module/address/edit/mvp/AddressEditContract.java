package com.xm6leefun.setting_module.address.edit.mvp;

import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface AddressEditContract {
    interface IModel {
        Observable<?> editAddress(String lastAddress,String address,String name,String desc);
        Observable<?> addAddress(String address,String name,String desc,String addressExistsStr);
        Observable<?> deleteAddress(String address);
    }

    interface IView extends BaseView {
        void deleteAddressSuccess();
        void editAddressSuccess();
        void addAddressSuccess();
        void onLoadFail(String msg);
    }
}
