package com.xm6leefun.block_browser.trade_detail.mvp;


import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.trade_detail.bean.BlockBrowserTradeDetailBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserTradeDetailModelImp implements BlockBrowserTradeDetailContract.IModel{



    public static final  int PAGE = 1;

    public static final  int LIMIT = 5;


    private BlockBrowserApiService apiService;

    public BlockBrowserTradeDetailModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getDetail(String hash) {
        return apiService.getBlockBrowserSearchDetail(hash,LIMIT,PAGE);
    }

    @Override
    public Observable<?> search(String search) {
        return apiService.getBlockBrowserSearchDetail(search,LIMIT,PAGE);
    }
}
