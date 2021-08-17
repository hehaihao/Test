package com.xm6leefun.common.app;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.taopao.rxtoast.RxToast;
import com.xm6leefun.common.BuildConfig;
import com.xm6leefun.common.base.BaseRetrofitConfig;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.ShowUtil;
import com.xm6leefun.common.utils.StrUtils;

import org.onlychain.wallet.base.ApiConfig;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * @Description: 基类application
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public abstract class BaseApplication extends MultiDexApplication {

    private static Context mContext;//上下文
    private String baseUrl = "";
    private String zwdBaseUrl = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        MultiDex.install(this);
        //数据库
        RealmUtils.setInitWalletOCRealm();
        initConfig();
        //初始化联盟链sdk
        String mCurrNode = SharePreferenceUtil.getString(ConstantValue.CURRENT_NODE);
        ApiConfig.init(BuildConfig.SIDE_IP+BuildConfig.SIDE_VERSION, (StrUtils.isEmpty(mCurrNode) ? BuildConfig.SUB_IP : mCurrNode)+BuildConfig.SUB_VERSION);
        initToast();
    }

    private void initToast() {
        RxToast.init(this)
                .setBackgroundColor("#A5000000")//背景颜色：35%的黑色透明度
                .setTextColor("#FFFFFF")//字体颜色
                .setGravity(Gravity.CENTER)//显示位置
                .setCornerRadius(6)//圆角大小  单位dp
                .setPadding(24, 24, 16, 16)//textview左右上下间距 单位dp
                .setMaxLines(2)//textview最大行数
                .setTextSize(14)//textview字体大小  单位dp
                .setZ(30)//Z轴的高度（阴影）
                .setLineSpacing(1.5f)//设置行间距
                .apply();//应用设置
    }

    public static Context getAppContext() {
        return mContext;
    }

    /**
     * 初始化 application
     */
    public void initConfig() {
        //初始化
        ShowUtil.initialize(this);
        //设置打印开关
        LogUtil.setIsLog(BuildConfig.DEBUG);
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(ActivityUtil.getMyActivityLifeCycleCallBack());
        baseUrl = initBaseUrl();
        zwdBaseUrl = initZwdBaseUrl();
        BaseRetrofitConfig.setBaseUrl(baseUrl);
        BaseRetrofitConfig.setZwdBaseUrl(zwdBaseUrl);
    }

    public String initBaseUrl() {
        return BuildConfig.HTTP_BASE_URL;
    }

    public String initZwdBaseUrl() {
        return BuildConfig.HTTP_ZWD_BASE_URL;
    }

}
