package com.xm6leefun.points_module.send_points.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface SendPointsContract {
    interface IModel {
        Observable<?> checkPsw(String psw,String wrongTipStr);
        Observable<?> pointsTran(String action);
        Observable<?> dealPointsTranData(String privateKey, HomeDataResultBean.FtBean ftBean,String toAddress, long points);
    }

    interface IView extends BaseView {
        void checkPswResult(boolean isPass,String toAddress,long points, String psw, KeysInfo keysInfo);
        void onLoadFail(String msg);
        void pointsTranSuccess();
    }
}
