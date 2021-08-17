package com.xm6leefun.physical_module.record.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.physical_module.record.api.RecordListApiService;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class RecordListPresenter extends BasePresenter<RecordListContract.IView> {
    private  RecordListContract.IModel iModel;
    public RecordListPresenter(RecordListContract.IView baseView) {
        super(baseView);
        iModel = new RecordListModelImp(retrofitManager.createRs(RecordListApiService.class));
    }

    /**
     * 获取列表
     */
    public void getRecordList(boolean isLoadMore,int page,int limit,String address){
        addDisposableObserveOnMain(iModel.getRecordList(page, limit, address), new BaseObserver<BaseNftDataBean<PhysicalRecordResultBean>>(baseView,true){
            @Override
            public void onSuccess(BaseNftDataBean<PhysicalRecordResultBean> o) {
                if(o != null && o.getResult() != null) baseView.getRecordListSuccess(o.getResult(),isLoadMore);
                else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
