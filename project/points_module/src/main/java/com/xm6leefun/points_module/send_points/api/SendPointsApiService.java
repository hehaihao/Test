package com.xm6leefun.points_module.send_points.api;

import com.xm6leefun.common.base.BaseNftDataBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/13
 */
public interface SendPointsApiService {

    /**
     * 实物资产转移
     * @return
     */
    @POST("Assets/ftTransfer")
    @FormUrlEncoded
    Observable<BaseNftDataBean<String>> ftTransfer(@Field("action") String action);
}
