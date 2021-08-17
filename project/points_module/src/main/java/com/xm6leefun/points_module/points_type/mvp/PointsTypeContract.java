package com.xm6leefun.points_module.points_type.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import java.util.List;

import io.reactivex.Observable;


public interface PointsTypeContract {


    interface IModel {
        Observable<?> getList(String address);
    }


    interface IView extends BaseView {
        void getListSuccess(List<HomeDataResultBean.FtBean> listBean);

        void onLoadFail(String msg);
    }
}
