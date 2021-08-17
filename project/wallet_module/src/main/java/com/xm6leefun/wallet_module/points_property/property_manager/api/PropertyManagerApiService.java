package com.xm6leefun.wallet_module.points_property.property_manager.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/25
 */
public interface PropertyManagerApiService {

    /**
     * 删除资产
     * @return
     */
    @POST("Assets/deleteFt")
    @FormUrlEncoded
    Observable<BaseNftDataBean<String>> deleteFt(@Field("id") String id, @Field("address") String address);

}
