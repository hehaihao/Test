package com.xm6leefun.setting_module.address.list.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.AddressBook;
import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class AddressListPresenter extends BasePresenter<AddressListContract.IView> {
    private  AddressListContract.IModel iModel;
    public AddressListPresenter(AddressListContract.IView baseView) {
        super(baseView);
        iModel = new AddressListModelImp();
    }

    /**
     * 获取地址本
     */
    public void getAddressList(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getAddressList(), new BaseObserver<List<AddressBook>>() {
            @Override
            public void onSuccess(List<AddressBook> addressBooks) {
                baseView.getAddressListSuccess(addressBooks);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
