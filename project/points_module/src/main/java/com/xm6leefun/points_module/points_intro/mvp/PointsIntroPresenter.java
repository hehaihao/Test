package com.xm6leefun.points_module.points_intro.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.points_module.points_intro.api.PointsIntroApiService;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class PointsIntroPresenter extends BasePresenter<PointsIntroContract.IView> {
    private  PointsIntroContract.IModel iModel;
    public PointsIntroPresenter(PointsIntroContract.IView baseView) {
        super(baseView);
        iModel = new PointsIntroModelImp(retrofitManager.createRs(PointsIntroApiService.class));
    }

    /**
     * 获取信息
     */
    public void getInfo(String id){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getInfo(id), new BaseObserver<BaseNftDataBean<PointsIntroBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<PointsIntroBean> o) {
                if(o != null && o.getResult() != null)
                baseView.getInfoSuccess(o.getResult());
                else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
