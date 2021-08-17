package com.xm6leefun.wallet_module.points_property.add_property.mvp;

import com.xm6leefun.common.base.BaseListDataBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.points_property.add_property.api.AddPointsPropertyApiService;

import java.util.List;

import io.realm.Realm;

public class AddPropertyPresenter extends BasePresenter<AddPropertyContract.IView> {

    private AddPropertyContract.IModel model;
    private Realm realm;
    public AddPropertyPresenter(AddPropertyContract.IView baseView) {
        super(baseView);
        model = new AddPropertyModelImp(retrofitManager.createRs(AddPointsPropertyApiService.class));
        realm = RealmUtils.getRealm();
    }

    public void getList(String key,String address) {
        addDisposableObserveOnMain(model.getList(key), new BaseObserver<BaseListDataBean<HomeDataResultBean.FtBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseListDataBean<HomeDataResultBean.FtBean> o) {
                if (o.result!=null && o.result.size() > 0) {
                    baseView.getListSuccess(o.result);
                    getAssetsFromDataBase(address);
                }else {
                    baseView.getListFail("data null");
                }
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getListFail(msg);
            }
        });
    }

    /**
     * 获取数据库中的资产列表
     */
    private void getAssetsFromDataBase(String address) {
        addDisposableObserveOnMain(model.getAssetsFromDataBase(address), new BaseObserver<List<Long>>() {
            @Override
            public void onSuccess(List<Long> ids) {
                baseView.updateList(ids);
            }

            @Override
            public void onError(int code, String msg) {
//                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 请求接口添加首页资产
     * @param bean
     * @param address
     */
    public void addProperty(HomeDataResultBean.FtBean bean,String address) {
        addDisposableObserveOnMain(model.addProperty(bean.getId(),address), new BaseObserver<BaseNftDataBean<Object>>() {
            @Override
            public void onSuccess(BaseNftDataBean<Object> o) {
                baseView.addPropertySuccess();
                updatePointsAssets(bean,address);//请求成功后更新数据库
            }

            @Override
            public void onError(int code, String msg) {
                baseView.addPropertyFail(msg);
            }
        });
    }

    /**
     * 更新数据库数据
     * @param bean
     */
    private void updatePointsAssets(HomeDataResultBean.FtBean bean,String address){
        addDisposableObserveOnMain(model.updatePointsAssets(bean,address), new BaseObserver<List<Long>>() {
            @Override
            public void onSuccess(List<Long> ids) {
                baseView.updateList(ids);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}