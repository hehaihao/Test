package com.xm6leefun.points_module.points_type.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/12 17:39
 */
public interface PointsTypeApiService {
    /**
     * 获取资产和实体资产
     * @return
     */
    @POST("Home/index")
    @FormUrlEncoded
    Observable<BaseNftDataBean<HomeDataResultBean>> getList(@Field("address") String address);
}
