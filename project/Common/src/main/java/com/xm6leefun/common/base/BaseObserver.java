package com.xm6leefun.common.base;

import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public abstract class BaseObserver<T> extends DisposableObserver<T> {

    protected BaseView view;
    private boolean isShowDialog;
    private String loadingStr;

    protected BaseObserver() {

    }

    protected BaseObserver(BaseView view) {
        this.view = view;
    }

    /**
     * 带进度条的初始化方法
     *
     * @param view         view
     * @param isShowDialog 是否显示进度条
     */
    protected BaseObserver(BaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }
    protected BaseObserver(BaseView view, boolean isShowDialog, String loadingStr) {
        this.view = view;
        this.isShowDialog = isShowDialog;
        this.loadingStr = loadingStr;
    }

    @Override
    protected void onStart() {
        if (view != null && isShowDialog) {
            view.showLoading(TextUtils.isEmpty(loadingStr) ? "加载中..." : loadingStr);
        }
    }

    @Override
    public void onNext(T o) {
        try {
            onSuccess(o);
        }catch (Exception e){
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
        BaseException be;

        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
                //系统异常
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(BaseException.NETWORK_ERR_CODE,BaseException.BAD_NETWORK_MSG, e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(BaseException.NETWORK_ERR_CODE,BaseException.CONNECT_ERROR_MSG, e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(BaseException.NETWORK_ERR_CODE,BaseException.CONNECT_TIMEOUT_MSG, e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(BaseException.PARSE_ERR_CODE,BaseException.PARSE_ERROR_MSG, e);
                } else {
                    be = new BaseException(BaseException.NETWORK_ERR_CODE,e.getMessage(), e);
                }
            }
        } else {
            be = new BaseException(BaseException.NETWORK_ERR_CODE,e.getMessage());
        }
        e.printStackTrace();
        onError(be.getErrorCode(),be.getErrorMsg());
        if (view != null && be.getErrorCode() == BaseException.RE_LOGIN) {//重新登录
            view.reLogin();
        }
    }

    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
    }


    public abstract void onSuccess(T o);

    public abstract void onError(int code, String msg);
}

