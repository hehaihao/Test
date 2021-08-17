package com.xm6leefun.physical_module.physical_list.mvp;

import com.xm6leefun.physical_module.physical_list.api.PhysicalListApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class PhysicalListModelImp implements PhysicalListContract.IModel{

    private PhysicalListApiService apiService;

    public PhysicalListModelImp(PhysicalListApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getPhysicalList(String address,String contractAddress,String name) {
        return apiService.getNftList(address,contractAddress,name);
    }
}
