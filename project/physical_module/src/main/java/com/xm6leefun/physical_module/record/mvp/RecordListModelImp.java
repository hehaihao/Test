package com.xm6leefun.physical_module.record.mvp;

import com.xm6leefun.physical_module.record.api.RecordListApiService;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class RecordListModelImp implements RecordListContract.IModel{

    private RecordListApiService apiService;

    public RecordListModelImp(RecordListApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getRecordList(int page,int limit,String address) {
        return apiService.getNftTransferList(page,limit,address);
    }
}
