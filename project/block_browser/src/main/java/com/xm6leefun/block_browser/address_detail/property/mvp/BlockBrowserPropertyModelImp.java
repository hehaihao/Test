package com.xm6leefun.block_browser.address_detail.property.mvp;


import com.xm6leefun.block_browser.api.BlockBrowserApiService;



import io.reactivex.Observable;


public class BlockBrowserPropertyModelImp implements BlockBrowserPropertyContract.IModel {


    private BlockBrowserApiService apiService;

    public static final int NFT_TYPE = 5;

    public static final  int LIMIT = 5;

    public static final  int PAGE = 1;

    public BlockBrowserPropertyModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<?> getList(String address, int limit, int page) {
        return apiService.getBlockBrowserAddressDetail(address,NFT_TYPE,limit,page);
    }


    @Override
    public Observable<?> search(String Search) {
        return apiService.getBlockBrowserSearchDetail(Search,LIMIT,PAGE);
    }

}