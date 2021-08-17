package com.xm6leefun.physical_module.record.api;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.physical_module.record.mvp.PhysicalRecordResultBean;

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
public interface RecordListApiService {


    /**
     * NFT转移纪录接口
     * @return
     */
    @POST("Assets/nftTransferList")
    @FormUrlEncoded
    Observable<BaseNftDataBean<PhysicalRecordResultBean>> getNftTransferList(
            @Field("page") int page,
            @Field("limit") int limit,
            @Field("address") String address);
}
