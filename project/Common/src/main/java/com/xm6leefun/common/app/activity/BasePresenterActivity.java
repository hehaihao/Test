package com.xm6leefun.common.app.activity;

import android.os.Bundle;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.base.BaseView;

import androidx.annotation.Nullable;

/**
 * @Description: 基类BaseActivity
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public abstract class BasePresenterActivity<P extends BasePresenter> extends BaseComActivity implements BaseView {

    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        getLifecycle().addObserver(presenter);//添加生命周期观察者
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }

    protected void initListener() {
    }

    @Override
    public void showLoading(String s) {
        show(s);
    }

    @Override
    public void hideLoading() {
        dismiss();
    }

    /**
     * token过期重新登录
     */
    @Override
    public void reLogin() {

    }
}
