package com.xm6leefun.points_module.send_points.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.points_module.send_points.api.SendPointsApiService;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class SendPointsPresenter extends BasePresenter<SendPointsContract.IView> {
    private SendPointsContract.IModel model;
    public SendPointsPresenter(SendPointsContract.IView baseView) {
        super(baseView);
        model = new SendPointsModelImpl(retrofitManager.createRs(SendPointsApiService.class));
    }

    /**
     * 检验密码
     * @param toAddress
     * @param psw
     * @param wrongTipStr
     */
    public void checkPsw(String toAddress, long points,String psw, String wrongTipStr){
        addDisposableObserveOnMain(model.checkPsw(psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,toAddress,points,psw,keysInfo);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,toAddress,points,psw,null);
            }
        });
    }

    /**
     * 积分转账
     * @param privateKey
     * @param toAddress
     */
    public void pointsTran(String privateKey, HomeDataResultBean.FtBean ftBean,String toAddress, long points){
        addDisposableObserveOnMain(model.dealPointsTranData(privateKey,ftBean,toAddress,points), new BaseObserver<String>(baseView,true) {
            @Override
            public void onSuccess(String action) {
                subPointsTran(action);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    /**
     * 积分转移
     */
    private void subPointsTran(String action){
        addDisposableObserveOnMain(model.pointsTran(action), new BaseObserver<BaseNftDataBean<String>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<String> o) {
                baseView.pointsTranSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
