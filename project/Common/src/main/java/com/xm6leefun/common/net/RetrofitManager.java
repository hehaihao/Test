package com.xm6leefun.common.net;

import android.util.Log;


import com.xm6leefun.common.BuildConfig;
import com.xm6leefun.common.base.BaseRetrofitConfig;
import com.xm6leefun.common.net.interceptor.ParamInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @Description:retrofit管理
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public class RetrofitManager {
    private static RetrofitManager apiRetrofit;
    private Retrofit mRetrofit;
    private Retrofit mZwdRetrofit;

    /**
     * 获取retrofit
     * @return RetrofitService
     */
    public static RetrofitManager getInstance() {
        if (apiRetrofit == null) {
            synchronized (RetrofitManager.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new RetrofitManager();
                }
            }
        }
        return apiRetrofit;
    }

    /**
     * 初始化retrofit
     */
    private RetrofitManager() {
        OkHttpClient.Builder baseBuilder = new OkHttpClient.Builder()
                //设置超时时间
                .connectTimeout(15, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            //配置okHttp并设置时间、日志信息和cookies
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                //打印信息
                Log.i("http",message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            baseBuilder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient okHttpClient = baseBuilder.build();
        //普通接口关联okHttp并加上rxJava和Gson的配置和baseUrl
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseRetrofitConfig.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(BaseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        OkHttpClient.Builder zwdBuilder = new OkHttpClient.Builder()
                //设置超时时间
                .connectTimeout(15, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            //配置okHttp并设置时间、日志信息和cookies
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                //打印信息
                Log.i("http",message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            zwdBuilder.addInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient zwdOkHttpClient = zwdBuilder.addInterceptor(new ParamInterceptor()).build();
        //真唯度（唯物链客户端）
        //普通接口关联okHttp并加上rxJava和Gson的配置和baseUrl
        mZwdRetrofit = new Retrofit.Builder()
                .baseUrl(BaseRetrofitConfig.getZwdBaseUrl())
                .client(zwdOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(BaseZwdConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 利用泛型传入接口class返回接口实例
     *
     * @param ser 类
     * @param <T> 类的类型
     * @return Observable
     */
    public <T> T createRs(Class<T> ser) {
        return mRetrofit.create(ser);
    }

    public <T> T createZwdRs(Class<T> ser) {
        return mZwdRetrofit.create(ser);
    }

}
