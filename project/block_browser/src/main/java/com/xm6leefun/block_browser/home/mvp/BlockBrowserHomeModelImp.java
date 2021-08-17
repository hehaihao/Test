package com.xm6leefun.block_browser.home.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;


import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/14
 */
public class BlockBrowserHomeModelImp implements BlockBrowserHomeContract.IModel{

    private BlockBrowserApiService apiService;

    public static final int LIMIT = 3;

    public static final int PAGE = 1;

    public BlockBrowserHomeModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getStatics() {
        return apiService.getBlockBrowserHomeData();
    }

    @Override
    public Observable<?> getBlockList() {
       return apiService.getBlockBrowserBlockList(LIMIT,PAGE);
    }

    @Override
    public Observable<?> getTradeList() {
        return apiService.getBlockBrowserTradeList(0,LIMIT,PAGE);
    }

    @Override
    public Observable<?> search(String search) {
        return apiService.getBlockBrowserSearchDetail(search,LIMIT,PAGE);
    }

}
