package com.xm6leefun.points_module.points_exchange_record.mvp;

import com.xm6leefun.points_module.points_exchange_record.api.PointsExchangRecordApiService;
import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/9 10:12
 */
public class PointsExchangeRecordModelImp implements PointsExchangeRecordContract.IModel{
    private PointsExchangRecordApiService apiService;

    public PointsExchangeRecordModelImp(PointsExchangRecordApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> exchangeList(String mobile, String expendId, long state, String startDate, String endDate, long page) {
        return apiService.exchangeList(mobile, expendId, state, startDate, endDate, page);
    }

    @Override
    public Observable<?> integralExchange(String mobile, String expendId, String address, long num) {
        return apiService.integralExchange(mobile, expendId, address, num);
    }
}
