package com.xm6leefun.setting_module.syssetting.mvp;

import android.app.Activity;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.setting_module.api.SystemSettingsApiService;
import com.xm6leefun.common.bean.AppVersionBean;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class SystemSettingPresenter extends BasePresenter<SystemSettingContract.IView> {

    private  SystemSettingContract.IModel iModel;

    public SystemSettingPresenter(SystemSettingContract.IView baseView) {
        super(baseView);
        iModel = new SystemSettingModelImp(retrofitManager.createRs(SystemSettingsApiService.class));
    }


    /**
     * 获取当前touchID状态
     */
    public void getTouchIDStatus(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getTouchIDStatus(), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                baseView.getTouchIDStatus(success);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getTouchIDStatus(false);
            }
        });
    }


    /**
     * 打开TouchID
     */
    public void openTouchID(Activity activity){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.OpenTouchID(activity), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
             baseView.OpenTouchID(success);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.OpenTouchID(false);;
            }
        });
    }



    /**
     * 打开TouchID
     */
    public void closeTouchID(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.CloseTouchID(), new BaseObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                baseView.CloseTouchID(success);
            }

            @Override
            public void onError(int code, String msg) {
            }
        });
    }



    public void getAppVersion(){
        addDisposableObserveOnMain(iModel.getAppVersion(), new BaseObserver<BaseNftDataBean<AppVersionBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<AppVersionBean> o) {
                baseView.getAppVersionSuccess(o.getResult());
            }

            @Override
            public void onError(int code,String msg) {
                baseView.getAppVersionFail(msg);
            }
        });
    }



}
