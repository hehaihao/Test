package com.xm6leefun.common.base;

/**
 * @Description:基类view视图层
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
@SuppressWarnings("ALL")
public interface BaseView {

    void showLoading(String s);

    void hideLoading();

    /**
     * token过期，重新登录
     */
    void reLogin();

}
