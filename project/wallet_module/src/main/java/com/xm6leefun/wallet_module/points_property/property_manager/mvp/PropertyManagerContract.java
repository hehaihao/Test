package com.xm6leefun.wallet_module.points_property.property_manager.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

import io.reactivex.Observable;


public interface PropertyManagerContract {


    interface IModel {
        Observable<?> getList(String key);

        Observable<?> deleteList(List<HomeDataResultBean.FtBean> listBeans,String address);
        Observable<?> deleteDbList(List<HomeDataResultBean.FtBean> listBean,String address);

        Observable<?> sortList(List<HomeDataResultBean.FtBean> listBean);
    }


    interface IView extends BaseView {

        void getListSuccess(List<HomeDataResultBean.FtBean> listBean);
        void onLoadFail(String msg);

        void deleteListSuccess();
        void deleteListFail(String msg);

        void sortListSuccess(String msg);
        void sortListFail(String msg);
    }
}
