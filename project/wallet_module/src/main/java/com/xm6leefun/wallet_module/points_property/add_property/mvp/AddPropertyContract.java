package com.xm6leefun.wallet_module.points_property.add_property.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

import io.reactivex.Observable;


public interface AddPropertyContract {


    interface IModel {
        Observable<?> getList(String key);
        Observable<?> addProperty(long id,String address);
        Observable<?> updatePointsAssets(HomeDataResultBean.FtBean bean,String address);

        Observable<?> getAssetsFromDataBase(String address);
    }


    interface IView extends BaseView {
        void getListSuccess(List<HomeDataResultBean.FtBean> listBean);
        void getListFail(String msg);
        void onLoadFail(String msg);
        void updateList(List<Long> ids);
        void addPropertyFail(String msg);
        void addPropertySuccess();

    }
}
