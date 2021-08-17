package com.xm6leefun.common.base;

/**
 * @Description:retrofit 配置
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public class BaseRetrofitConfig {
    private static String baseUrl;
    private static String zwdBaseUrl;

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        BaseRetrofitConfig.baseUrl = baseUrl;
    }

    public static String getZwdBaseUrl() {
        return zwdBaseUrl;
    }

    public static void setZwdBaseUrl(String zwdBaseUrl) {
        BaseRetrofitConfig.zwdBaseUrl = zwdBaseUrl;
    }
}
