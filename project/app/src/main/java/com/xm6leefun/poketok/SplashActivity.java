package com.xm6leefun.poketok;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.xm6leefun.common.app.activity.BasePresenterActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.home_module.HomeActivity;
import com.xm6leefun.poketok.mvp.SplashContract;
import com.xm6leefun.poketok.mvp.SplashPresenter;
import java.util.concurrent.TimeUnit;

/**
 * 启动页
 * @author hhh
 */
public class SplashActivity extends BasePresenterActivity<SplashPresenter> implements SplashContract.IView {

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        presenter.checkTouchId(this);
    }

    private void postDelay(boolean hasWallet){
        disposable = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (hasWallet) {
                        ModuleServiceUtils.navigateHomePage();
                    } else {
                        ModuleServiceUtils.navigateFirstPage(true);
                    }
                    ActivityUtil.finishActivity(this);
                });
    }

    private Disposable disposable;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onTouchIdCancel() {
        ActivityUtil.closeAllActivity();
        System.exit(0);
    }

    @Override
    public void isOpenTouchId(boolean isOpen) {
        presenter.checkWallet();
    }

    @Override
    public void checkWalletSuccess(boolean hasWallet) {
        postDelay(hasWallet);
    }

    @Override
    public void onLoadFail(String msg) {
        postDelay(false);
        ToastUtil.showCenterToast(msg);
    }
}