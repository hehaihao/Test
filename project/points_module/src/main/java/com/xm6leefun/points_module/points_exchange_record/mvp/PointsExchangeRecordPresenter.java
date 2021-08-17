package com.xm6leefun.points_module.points_exchange_record.mvp;

import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.points_module.points_exchange_record.api.PointsExchangRecordApiService;


import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/9 10:11
 */
public class PointsExchangeRecordPresenter extends BasePresenter<PointsExchangeRecordContract.IView> {

    private PointsExchangeRecordContract.IModel model;
    public PointsExchangeRecordPresenter(PointsExchangeRecordContract.IView baseView) {
        super(baseView);
        model = new PointsExchangeRecordModelImp(retrofitManager.createZwdRs(PointsExchangRecordApiService.class));
    }


    /**
     * 获取兑换记录
     */
    public void exchangeList(boolean isLoadMore,String mobile,String expendId,long state,String startDate,String endDate,long page){
        addDisposableObserveOnMain(model.exchangeList(mobile, expendId, state, startDate, endDate, page), new BaseObserver<BaseDataBean<RecordResultBean>>(baseView,!isLoadMore) {
            @Override
            public void onSuccess(BaseDataBean<RecordResultBean> o) {
                RecordResultBean recordResultBean = o.getRecord();
                if(recordResultBean != null)
                    baseView.getListSuccess(recordResultBean,isLoadMore);
                else
                    baseView.getListFail("null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getListFail(msg);
            }
        });
    }

    /**
     * 兑换积分
     */
    public void integralExchange(String mobile, String expendId, String address, long num){
        addDisposableObserveOnMain(model.integralExchange(mobile, expendId, address, num), new BaseObserver<BaseDataBean<String>>(baseView,true) {
            @Override
            public void onSuccess(BaseDataBean<String> o) {
                baseView.integralExchangeSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.integralExchangeFail(msg);
            }
        });
    }
}
