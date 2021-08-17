package com.xm6leefun.points_module.points_intro.mvp;

import com.xm6leefun.points_module.points_intro.api.PointsIntroApiService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:49
 */
public class PointsIntroModelImp implements PointsIntroContract.IModel{
    private PointsIntroApiService apiService;

    public PointsIntroModelImp(PointsIntroApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getInfo(String id) {
        return apiService.ftInfo(id);
    }
}
