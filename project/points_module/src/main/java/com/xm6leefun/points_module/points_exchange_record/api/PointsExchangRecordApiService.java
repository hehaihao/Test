package com.xm6leefun.points_module.points_exchange_record.api;

import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeResultBean;
import com.xm6leefun.points_module.points_exchange_record.mvp.RecordResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/27 13:49
 */
public interface PointsExchangRecordApiService {
    /**
     * 积分兑换纪录接口（nft）
     * @return
     */
    @POST("userInfo/exchangeList")
    @FormUrlEncoded
    Observable<BaseDataBean<RecordResultBean>> exchangeList(
            @Field("mobile") String mobile,
            @Field("expendId") String expendId,
            @Field("state") long state,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate,
            @Field("page") long page);

    /**
     * 积分兑换接口(nft)
     * @return
     */
    @POST("userInfo/integralExchange")
    @FormUrlEncoded
    Observable<BaseDataBean<String>> integralExchange(
            @Field("mobile") String mobile,
            @Field("expendId") String expendId,
            @Field("address") String address,
            @Field("num") long num);
}
