package com.xm6leefun.wallet_module.home_wallet.api;

import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.home_wallet.mvp.PhysicalListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/12 17:39
 */
public interface HomeWalletApiService {
    /**
     * 获取首页资产和实体资产
     * @return
     */
    @POST("Home/index")
    @FormUrlEncoded
    Observable<BaseNftDataBean<HomeDataResultBean>> getIndexData(@Field("address") String address);

    /**
     * nft实物资产列表接口
     * @return
     */
    @POST("Assets/nftList")
    @FormUrlEncoded
    Observable<BaseNftDataBean<List<PhysicalListBean>>> getNftList(@Field("address") String address);
}
