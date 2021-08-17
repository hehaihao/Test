package com.xm6leefun.block_browser.block_detail.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.block_detail.bean.BlockBrowserBlockDetailBean;


import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserBlockDetailModelImp  implements BlockBrowserBlockDetailContract.IModel {


    public static final  int PAGE = 1;

    public static final  int LIMIT = 5;


    private BlockBrowserApiService apiService;

    public BlockBrowserBlockDetailModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getDetail(int height) {
        return apiService.getBlockBrowserSearchDetail(height + "" ,LIMIT,PAGE);
    }

    @Override
    public Observable<?> search(String Search) {
        return apiService.getBlockBrowserSearchDetail(Search,LIMIT,PAGE);
    }
}
