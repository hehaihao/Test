package com.xm6leefun.points_module.points_intro.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.points_module.points_intro.mvp.PointsIntroBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/12 17:39
 */
public interface PointsIntroApiService {
    /**
     * FT信息介绍
     * @return
     */
    @POST("Assets/ftInfo")
    @FormUrlEncoded
    Observable<BaseNftDataBean<PointsIntroBean>> ftInfo(@Field("chainId") String chainId);
}
