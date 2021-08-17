package com.xm6leefun.physical_module.physical_detail.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/13
 */
public interface PhysicalDetailApiService {


    /**
     * 实物资产详情
     * @return
     */
    @POST("Assets/nftDetails")
    @FormUrlEncoded
    Observable<BaseNftDataBean<PhysicalDetailBean>> getDetail(@Field("goodsId") long id);

    /**
     * 实物资产转移
     * @return
     */
    @POST("Assets/nftTransfer")
    @FormUrlEncoded
    Observable<BaseNftDataBean<String>> nftTransfer(@Field("action") String action);
}
