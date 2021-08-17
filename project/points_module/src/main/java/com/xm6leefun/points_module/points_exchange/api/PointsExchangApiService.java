package com.xm6leefun.points_module.points_exchange.api;

import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.points_module.points_exchange.mvp.PointsExchangeResultBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/27 13:49
 */
public interface PointsExchangApiService {
    /**
     * 我的积分列表接口（nft)
     * @return
     */
    @POST("userInfo/getIntegralList")
    @FormUrlEncoded
    Observable<BaseDataBean<PointsExchangeResultBean>> getIntegralList(@Field("mobile") String mobile);
}
