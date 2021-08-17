package com.xm6leefun.points_module.points_exchange_record.mvp;

import com.xm6leefun.common.base.BaseView;
import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/9 10:11
 */
public interface PointsExchangeRecordContract {
    interface IModel {
        Observable<?> exchangeList(String mobile,String expendId,long state,String startDate,String endDate,long page);
        Observable<?> integralExchange(String mobile,String expendId,String address,long num);
    }


    interface IView extends BaseView {
        void getListSuccess(RecordResultBean recordResultBean, boolean isLoadMore);
        void getListFail(String msg);

        void integralExchangeSuccess();
        void integralExchangeFail(String msg);
    }
}
