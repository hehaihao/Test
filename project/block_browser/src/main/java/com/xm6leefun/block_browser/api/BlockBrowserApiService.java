package com.xm6leefun.block_browser.api;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.home.bean.BlockBrowserHomeBean;
import com.xm6leefun.block_browser.product_detail.bean.ProductDetailBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.common.base.BaseNftDataBean;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/27
 */
public interface BlockBrowserApiService {


    /**
     * 区块浏览器首页
     * @return
     */

    @GET("Browser/blockIndex")
    Observable<BaseNftDataBean<BlockBrowserHomeBean>> getBlockBrowserHomeData();


    /**
     * 区块信息
     * @return
     */

    @POST("Browser/blockHeightPage")
    @FormUrlEncoded
    Observable<BaseNftDataBean<BlockBrowserBlockBean>> getBlockBrowserBlockList(@Field("limit")long limit,@Field("page")long page);



    /**
     * 交易信息
     * @return
     */
    @POST("Browser/blockActionPage")
    @FormUrlEncoded
    Observable<BaseNftDataBean<BlockBrowserTradeBean>> getBlockBrowserTradeList(@Field("type") int type,@Field("limit")long limit, @Field("page")long page);






    /**
     *
     * 区块详情
     */
    @POST("browser/blockSearchInfo")
    @FormUrlEncoded
    Observable<BaseNftDataBean<BlockBrowserSearchBean>> getBlockBrowserSearchDetail(@Field("search")String search,@Field("limit")int limit, @Field("page")int page);




    /**
     *
     * 地址详情
     */
    @POST("browser/blockSearchInfo")
    @FormUrlEncoded
    Observable<BaseNftDataBean<BlockBrowserSearchBean>> getBlockBrowserAddressDetail(@Field("search")String search,@Field("type") int type,@Field("limit")int limit, @Field("page")int page);

}
