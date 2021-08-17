package com.xm6leefun.home_module.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.bean.AppVersionBean;
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
public interface HomeApiService {
    /**
     * 获取首页资产和实体资产
     * @return
     */
    @POST("Home/index")
    @FormUrlEncoded
    Observable<BaseNftDataBean<HomeDataResultBean>> getIndexData(@Field("address") String address);

    /**
     * 版本更新信息
     * @return
     */
    @POST("AppConfig/getAppVersionInfo")
    @FormUrlEncoded
    Observable<BaseNftDataBean<AppVersionBean>> getAppVersionInfo(@Field("version_type") String type);

}
