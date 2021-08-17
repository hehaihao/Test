package com.xm6leefun.points_module;

import android.content.Context;
import android.util.Log;

import com.xm6leefun.common.app.BaseApplication;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 10:47
 */
public class PointsApplication extends BaseApplication {

    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = context;
        Log.i("PointsApplication", "onCreate");
    }
}
