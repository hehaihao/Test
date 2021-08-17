package com.xm6leefun.points_module.points_type.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.points_module.points_type.api.PointsTypeApiService;

public class PointsTypePresenter extends BasePresenter<PointsTypeContract.IView> {

    private PointsTypeContract.IModel model;

    public PointsTypePresenter(PointsTypeContract.IView baseView) {
        super(baseView);
        model = new PointsTypeModelImp(retrofitManager.createRs(PointsTypeApiService.class));
    }

    public void getList(String address) {
        addDisposableObserveOnMain(model.getList(address), new BaseObserver<BaseNftDataBean<HomeDataResultBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<HomeDataResultBean> o) {
                HomeDataResultBean homeDataResultBean = o.getResult();
                if(homeDataResultBean != null) baseView.getListSuccess(homeDataResultBean.getFt());
                else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}