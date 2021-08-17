package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.points_module.transaction.api.TransationApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class TransactionListModelImp implements TransactionContract.IListModel{
    private TransationApiService apiService;

    public TransactionListModelImp(TransationApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getTransactionList(String address,String contractAddress,String nftId,int page,int limit,int type) {
        return apiService.ftTransferList(address,contractAddress, nftId,page,limit,type);
    }
}
