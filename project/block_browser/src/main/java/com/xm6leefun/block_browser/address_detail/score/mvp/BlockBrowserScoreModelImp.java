package com.xm6leefun.block_browser.address_detail.score.mvp;





import com.xm6leefun.block_browser.api.BlockBrowserApiService;

import io.reactivex.Observable;


public class BlockBrowserScoreModelImp implements BlockBrowserScoreContract.IModel {


    private BlockBrowserApiService apiService;

    public final int SCORE_TYPE = 1;

    public static final  int LIMIT = 5;

    public static final  int PAGE = 1;



    public BlockBrowserScoreModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<?> getList(String address, int limit, int page) {
        return apiService.getBlockBrowserAddressDetail(address,SCORE_TYPE,limit,page);
    }


    @Override
    public Observable<?> search(String Search) {
        return apiService.getBlockBrowserSearchDetail(Search,LIMIT,PAGE);
    }
}