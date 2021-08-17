package com.xm6leefun.setting_module.address.edit.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class AddressEditPresenter extends BasePresenter<AddressEditContract.IView> {
    private  AddressEditContract.IModel iModel;
    public AddressEditPresenter(AddressEditContract.IView baseView) {
        super(baseView);
        iModel = new AddressEditModelImp();
    }

    /**
     * 编辑
     */
    public void editAddress(String lastAddress, String address, String name, String desc){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.editAddress(lastAddress, address, name, desc), new BaseObserver<String>() {
            @Override
            public void onSuccess(String s) {
                baseView.editAddressSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 添加
     */
    public void addAddress(String address,String name,String desc,String addressExistsStr){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.addAddress(address, name, desc,addressExistsStr), new BaseObserver<String>() {
            @Override
            public void onSuccess(String s) {
                baseView.addAddressSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 添加
     */
    public void deleteAddress(String address){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.deleteAddress(address), new BaseObserver<String>() {
            @Override
            public void onSuccess(String s) {
                baseView.deleteAddressSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
