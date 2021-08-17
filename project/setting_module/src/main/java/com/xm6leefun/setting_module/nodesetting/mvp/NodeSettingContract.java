package com.xm6leefun.setting_module.nodesetting.mvp;

import com.xm6leefun.common.base.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public interface NodeSettingContract {

    interface IModel {
        Observable<?> getNodeList();
    }



    interface IView extends BaseView {
        void getListSuccess(List<NodeBean> listBeans);
        void onLoadFail(String msg);
    }

}
