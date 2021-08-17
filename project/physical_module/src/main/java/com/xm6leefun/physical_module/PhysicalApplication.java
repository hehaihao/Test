package com.xm6leefun.physical_module;

import android.content.Context;
import android.util.Log;

import com.xm6leefun.common.app.BaseApplication;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 10:47
 */
public class PhysicalApplication extends BaseApplication {

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = context;
        Log.i("PhysicalApplication", "onCreate");
    }
}
