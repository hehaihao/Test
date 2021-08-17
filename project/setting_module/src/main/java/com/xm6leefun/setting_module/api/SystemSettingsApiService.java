package com.xm6leefun.setting_module.api;


import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.setting_module.nodesetting.mvp.NodeResultBean;
import com.xm6leefun.common.bean.AppVersionBean;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/5/14
 */
public interface SystemSettingsApiService {

    /**
     * 版本更新信息
     * @return
     */
    @POST("AppConfig/getAppVersionInfo")
    @FormUrlEncoded
    Observable<BaseNftDataBean<AppVersionBean>> getAppVersionInfo(@Field("version_type") String type);

    /**
     * 版本更新信息
     * @return
     */
    @POST("AppConfig/getNodeList")
    Observable<BaseNftDataBean<NodeResultBean>> getNodeList();
}
