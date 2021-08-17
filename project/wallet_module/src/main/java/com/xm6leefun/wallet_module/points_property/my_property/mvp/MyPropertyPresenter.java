package com.xm6leefun.wallet_module.points_property.my_property.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

public class MyPropertyPresenter extends BasePresenter<MyPropertyContract.IView> {

    private MyPropertyContract.IModel model;

    public MyPropertyPresenter(MyPropertyContract.IView baseView) {
        super(baseView);
        model = new MyPropertyModelImp();
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
}