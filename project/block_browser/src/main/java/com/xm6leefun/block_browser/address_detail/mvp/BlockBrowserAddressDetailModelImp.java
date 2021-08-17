package com.xm6leefun.block_browser.address_detail.mvp;


import com.xm6leefun.block_browser.api.BlockBrowserApiService;



import io.reactivex.Observable;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/19
 */
public class BlockBrowserAddressDetailModelImp implements BlockBrowserAddressDetailContract.IModel{


    public static final int PAGE = 1;

    public static final int LIMIT = 3;

    public static final int SCORE_TYPE = 1;

    public static final int NFT_TYPE = 5;


    private BlockBrowserApiService apiService;

    public BlockBrowserAddressDetailModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }



    @Override
    public Observable<?> getScoreList(String address) {
        return apiService.getBlockBrowserAddressDetail(address,SCORE_TYPE ,LIMIT,PAGE);
    }

    @Override
    public Observable<?> getNftList(String address) {
        return apiService.getBlockBrowserAddressDetail(address,NFT_TYPE ,LIMIT,PAGE);
    }

    @Override
    public Observable<?> search(String Search) {
        return apiService.getBlockBrowserSearchDetail(Search,LIMIT,PAGE);
    }
}
