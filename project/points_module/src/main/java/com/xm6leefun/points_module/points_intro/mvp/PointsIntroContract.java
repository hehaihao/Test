package com.xm6leefun.points_module.points_intro.mvp;

import com.xm6leefun.common.base.BaseView;
import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface PointsIntroContract {
    interface IModel {
        Observable<?> getInfo(String id);
    }

    interface IView extends BaseView {
        void getInfoSuccess(PointsIntroBean pointsIntroBean);
        void onLoadFail(String msg);
    }
}
