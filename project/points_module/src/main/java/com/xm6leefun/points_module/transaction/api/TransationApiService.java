package com.xm6leefun.points_module.transaction.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.points_module.transaction.mvp.TransationResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/5/6 14:24
 */
public interface TransationApiService {
    /**
     * FT转移记录
     * @return
     */
    @POST("Assets/ftTransferList")
    @FormUrlEncoded
    Observable<BaseNftDataBean<TransationResultBean>> ftTransferList(
            @Field("address") String address,
            @Field("contractAddress") String contractAddress,
            @Field("chainId") String chainId,
            @Field("page") int page,
            @Field("limit") int limit,
            @Field("type") int type);//0全部1转出2转入3失败
}
