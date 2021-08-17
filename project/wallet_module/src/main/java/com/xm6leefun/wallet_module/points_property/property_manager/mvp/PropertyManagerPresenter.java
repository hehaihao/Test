package com.xm6leefun.wallet_module.points_property.property_manager.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.points_property.property_manager.api.PropertyManagerApiService;

import java.util.List;

public class PropertyManagerPresenter extends BasePresenter<PropertyManagerContract.IView> {

    private PropertyManagerContract.IModel model;

    public PropertyManagerPresenter(PropertyManagerContract.IView baseView) {
        super(baseView);
        model = new PropertyManagerModelImp(retrofitManager.createRs(PropertyManagerApiService.class));
    }

    public void getList(String key) {
        addDisposableObserveOnMain(model.getList(key), new BaseObserver<List<HomeDataResultBean.FtBean>>() {
            @Override
            public void onSuccess(List<HomeDataResultBean.FtBean> detailBean) {
                baseView.getListSuccess(detailBean);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }


    public void deleteList(List<HomeDataResultBean.FtBean> list,String address) {
        if(StrUtils.isEmpty(address)){
            return;
        }
        addDisposableObserveOnMain(model.deleteList(list,address), new BaseObserver<BaseNftDataBean<String>>() {
            @Override
            public void onSuccess(BaseNftDataBean<String> o) {
                deleteDbList(list, address);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.deleteListFail(msg);
            }
        });
    }

    private void deleteDbList(List<HomeDataResultBean.FtBean> list,String address){
        addDisposableObserveOnMain(model.deleteDbList(list,address), new BaseObserver<String>() {
            @Override
            public void onSuccess(String o) {
                baseView.deleteListSuccess();
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }


    public void sortList(List<HomeDataResultBean.FtBean> list) {
        addDisposableObserveOnMain(model.sortList(list), new BaseObserver<String>() {
            @Override
            public void onSuccess(String o) {
                baseView.sortListSuccess(o);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.sortListFail(msg);
            }
        });
    }
}