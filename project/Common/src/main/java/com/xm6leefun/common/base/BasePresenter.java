package com.xm6leefun.common.base;


import com.xm6leefun.common.net.RetrofitManager;
import com.xm6leefun.common.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Description:基类路由层
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public class BasePresenter<V extends BaseView> implements LifecycleObserver {

    private CompositeDisposable compositeDisposable;
    public V baseView;


    protected RetrofitManager retrofitManager = RetrofitManager.getInstance();

    public BasePresenter(V baseView) {
        this.baseView = baseView;
    }

    /**
     * 销毁时，解除绑定
     */
    private void detachView() {
        LogUtil.e("销毁，避免内存泄露");
        baseView = null;
        removeDisposable();
    }

    /**
     * 返回 view
     */
    public V getBaseView() {
        return baseView;
    }

    /**
     * 在io线程执行，在切换到回主线程
     * @param observable
     * @param observer
     */
    @SuppressWarnings("unchecked")
    protected void addDisposableObserveOnMain(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable
                .add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    /**
     * 使用发射器，先加载数据库中的数据，然后再去请求网络数据
     * @param dbObservable
     * @param netObservable
     * @param observer
     */
    @SuppressWarnings("unchecked")
    protected void addDisposableObserveOnMainByConcat(Observable<?> dbObservable,Observable<?> netObservable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable
                .add(Observable.concat(dbObservable,netObservable)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    /**
     * 在io线程执行
     * @param observable
     * @param observer
     */
    @SuppressWarnings("unchecked")
    protected void addDisposableObserveOnIo(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable
                .add(observable.subscribeOn(Schedulers.io())
                        .subscribeWith(observer));
    }

    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected RequestBody toRequestBody(String value){
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),value);
        return body;
    }

    protected List<MultipartBody.Part> imgToMultipartBodyParts(List<File> files){
        List<MultipartBody.Part> parts = new ArrayList<>();
        for(int i = 0;i<files.size();i++){
            File file = files.get(i);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("imgFile["+i+"]",file.getName(),requestBody);
            parts.add(part);
        }
        return parts;
    }
    protected MultipartBody.Part imgToMultipartBodyPart(File file, String key){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key,file.getName(),requestBody);
        return part;
    }

    /**
     * 以下通过lifecycle感知activity或者fragment生命周期，生命周期在Lifecycle.Event内查看
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner owner){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner){
        detachView();
    }

}
