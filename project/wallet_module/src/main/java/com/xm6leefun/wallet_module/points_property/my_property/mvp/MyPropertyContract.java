package com.xm6leefun.wallet_module.points_property.my_property.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

import io.reactivex.Observable;


public interface MyPropertyContract {


    interface IModel {
        Observable<?> getList(String key);
    }


    interface IView extends BaseView {
        void getListSuccess(List<HomeDataResultBean.FtBean> listBean);

        void onLoadFail(String msg);
    }
}
