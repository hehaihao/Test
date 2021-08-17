package com.xm6leefun.wallet_module.backup.prikey.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class BackupPrikeyPresenter extends BasePresenter<BackupPrikeyContract.IView> {
    private  BackupPrikeyContract.IModel iModel;
    public BackupPrikeyPresenter(BackupPrikeyContract.IView baseView) {
        super(baseView);
        iModel = new BackupPrikeyModelImp();
    }

    /**
     * 获取当前选中地址
     */
    public void getPriKey(String psw,String address){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getPriKey(psw, address), new BaseObserver<String>() {
            @Override
            public void onSuccess(String priKey) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
}
