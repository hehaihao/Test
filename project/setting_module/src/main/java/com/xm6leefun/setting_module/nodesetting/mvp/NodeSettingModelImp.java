package com.xm6leefun.setting_module.nodesetting.mvp;

import com.xm6leefun.setting_module.api.SystemSettingsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class NodeSettingModelImp implements NodeSettingContract.IModel{

    private SystemSettingsApiService apiService;

    public NodeSettingModelImp(SystemSettingsApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getNodeList() {
        return apiService.getNodeList();
    }
}
