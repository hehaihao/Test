package com.xm6leefun.points_module.transaction_detail.mvp;

import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;

import io.reactivex.Observable;

/**
 * @Description:契约层
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
public interface TransitionDetailContract {
    interface IModel {
        Observable<?> getDetail();
    }

    interface IView extends BaseView {
        void getDetailSuccess(TransactionListBean transactionListBean);
        void onLoadFail(String msg);
    }
}
