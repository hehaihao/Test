package com.xm6leefun.setting_module.syssetting.mvp;

import android.app.Activity;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.bean.AppVersionBean;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public interface SystemSettingContract {
    interface IModel {
        Observable<?> getTouchIDStatus();
        Observable<?> OpenTouchID(Activity activity);
        Observable<?> CloseTouchID();

        Observable<?> getAppVersion();

    }

    interface IView extends BaseView {

        void getTouchIDStatus(boolean success);
        void OpenTouchID(boolean success);
        void CloseTouchID(boolean success);


        void getAppVersionSuccess(AppVersionBean appVersionBean);
        void getAppVersionFail(String errorMessage);

    }
}
