package com.xm6leefun.wallet_module.points_property.add_property.api;

import com.xm6leefun.common.base.BaseListDataBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/25
 */
public interface AddPointsPropertyApiService {

    /**
     * 热门资产首页
     * @return
     */
    @POST("Assets/ftList")
    @FormUrlEncoded
    Observable<BaseListDataBean<HomeDataResultBean.FtBean>> getHotProperty(@Field("tokenName") String key, @Field("contract_address") String contract_address);


    /**
     * @return
     */
    @POST("Assets/addFt")
    @FormUrlEncoded
    Observable<BaseNftDataBean<Object>> addProperty(@Field("id") long id, @Field("address") String address);

}
