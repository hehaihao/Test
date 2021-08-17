package com.xm6leefun.points_module.points_type.mvp;

import com.xm6leefun.points_module.points_type.api.PointsTypeApiService;
import io.reactivex.Observable;

public class PointsTypeModelImp implements PointsTypeContract.IModel {
    private PointsTypeApiService apiService;

    public PointsTypeModelImp(PointsTypeApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getList(String address) {
        return apiService.getList(address);
    }
}