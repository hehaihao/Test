package com.xm6leefun.physical_module.send_property.api;

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
public interface SendPhysicalApiService {

    /**
     * 实物资产转移
     * @return
     */
    @POST("Assets/nftTransfer")
    @FormUrlEncoded
    Observable<BaseNftDataBean<String>> nftTransfer(@Field("action") String action);
}
