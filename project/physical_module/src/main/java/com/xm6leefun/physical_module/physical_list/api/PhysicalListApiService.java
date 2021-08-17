package com.xm6leefun.physical_module.physical_list.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.physical_module.physical_list.mvp.PhysicalListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/13
 */
public interface PhysicalListApiService {


    /**
     * nft实物资产列表接口
     * @return
     */
    @POST("Assets/nftList")
    @FormUrlEncoded
    Observable<BaseNftDataBean<List<PhysicalListBean>>> getNftList(
            @Field("address") String address,
            @Field("contractAddress") String contractAddress,
            @Field("name") String name);
}
