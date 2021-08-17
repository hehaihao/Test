package com.xm6leefun.block_browser.block.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;

import org.onlychain.bean.BlockBean;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserBlockListModelImp implements BlockBrowserBlockListContract.IModel {

    private BlockBrowserApiService apiService;

    public static final int LIMIT = 5;

    public static final int PAGE = 1;

    public BlockBrowserBlockListModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }



    @Override
    public Observable<?> getList(long limit,long page) {
        return  apiService.getBlockBrowserBlockList(limit,page);
    }

    @Override
    public Observable<?> search(String search) {
        return apiService.getBlockBrowserSearchDetail(search,LIMIT,PAGE);
    }
}
