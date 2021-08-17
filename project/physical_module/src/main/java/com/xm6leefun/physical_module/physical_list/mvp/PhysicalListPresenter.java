package com.xm6leefun.physical_module.physical_list.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.physical_module.physical_list.api.PhysicalListApiService;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class PhysicalListPresenter extends BasePresenter<PhysicalListContract.IView> {
    private  PhysicalListContract.IModel iModel;
    public PhysicalListPresenter(PhysicalListContract.IView baseView) {
        super(baseView);
        iModel = new PhysicalListModelImp(retrofitManager.createRs(PhysicalListApiService.class));
    }

    /**
     * 获取列表
     */
    public void getPhysicalList(boolean isLoadMore,String address,String contractAddress,String name){
        addDisposableObserveOnMain(iModel.getPhysicalList(address,contractAddress,name), new BaseObserver<BaseNftDataBean<List<PhysicalListBean>>>(){
            @Override
            public void onSuccess(BaseNftDataBean<List<PhysicalListBean>> o) {
                if(o != null) baseView.getPhysicalListSuccess(o.getResult(),isLoadMore);
                else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
