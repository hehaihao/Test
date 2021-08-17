package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.points_module.transaction.api.TransationApiService;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class TransactionListPresenter extends BasePresenter<TransactionContract.IListView> {
    private  TransactionContract.IListModel iModel;
    public TransactionListPresenter(TransactionContract.IListView baseView) {
        super(baseView);
        iModel = new TransactionListModelImp(retrofitManager.createRs(TransationApiService.class));
    }

    /**
     * 获取列表
     */
    public void getTransactionList(boolean isLoadMore,String address,String contractAddress,String nftId,int page,int limit,int type){
        addDisposableObserveOnMain(iModel.getTransactionList(address,contractAddress, nftId,page,limit,type), new BaseObserver<BaseNftDataBean<TransationResultBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<TransationResultBean> o) {
                if(o != null && o.getResult() != null) baseView.getTransactionListSuccess(o.getResult(),isLoadMore);
                else baseView.onLoadFail("data_null");
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
