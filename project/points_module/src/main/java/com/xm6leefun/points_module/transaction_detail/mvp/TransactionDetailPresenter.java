package com.xm6leefun.points_module.transaction_detail.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class TransactionDetailPresenter extends BasePresenter<TransitionDetailContract.IView> {
    private  TransitionDetailContract.IModel iModel;
    public TransactionDetailPresenter(TransitionDetailContract.IView baseView) {
        super(baseView);
        iModel = new TransactionDetailModelImp();
    }
}
