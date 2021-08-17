package com.xm6leefun.common.app.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xm6leefun.common.R;
import com.xm6leefun.common.R2;
import com.xm6leefun.common.listener.PermissionListener;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.common.widget.svprogress.SVProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/27 13:57
 */
public abstract class BaseComActivity extends AppCompatActivity implements View.OnClickListener{
    protected SVProgressHUD svProgressHUD;
    protected FragmentManager mFragmentManager;


    protected Unbinder mUnbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        svProgressHUD = new SVProgressHUD(this);
        StatusBarUtil.setLightModeNotFull(this);
        initView();
        initData();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected boolean isLogin(String userIdKey){
        if(TextUtils.isEmpty(userIdKey)) return false;
        String userId = SharePreferenceUtil.getString(userIdKey);
        if(TextUtils.isEmpty(userId)) return false;
        else return true;
    }

    /**
     * 判断是否登录，如果未登录则跳转指定界面
     * @param userIdKey
     * @param cls
     * @return
     */
    protected boolean isLoginAndJumpLogin(String userIdKey, Class<?> cls){
        if(TextUtils.isEmpty(userIdKey)) return false;
        String userId = SharePreferenceUtil.getString(userIdKey);
        if(TextUtils.isEmpty(userId)) {
            ActivityUtil.startActivity(cls,false);
            return false;
        }else return true;
    }

    /**
     * 显示加载框
     * @param showMessage
     */
    protected void show(String showMessage){
        if(!svProgressHUD.isShowing()){
            svProgressHUD.showWithStatus(showMessage);
        }
    }

    /**
     * 关闭加载框
     */
    protected void dismiss(){
        if(svProgressHUD.isShowing()){
            svProgressHUD.dismiss();
        }
    }

    /**
     * 关闭加载框
     */
    protected void dismissOnMainThread(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(svProgressHUD.isShowing()){
                    svProgressHUD.dismiss();
                }
            }
        });
    }

    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }

    protected void addOnClickListeners(@IdRes int... ids) {
        try {
            if (ids != null) {
                for (@IdRes int id : ids) {
                    getView(id).setOnClickListener(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {}

    /***
     * 申请多个权限
     */
    @SuppressLint("CheckResult")
    public void checkPermissionsRequest(FragmentActivity activity, PermissionListener listener, String... permission) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(permission)
                .subscribe(aBoolean -> {
                    if(listener != null)listener.permissionResult(aBoolean);
                    LogUtil.i("获取多个权限--:" + aBoolean);
                });
    }

    /**
     * 申请单个权限
     * @param activity
     */
    @SuppressLint("CheckResult")
    public void checkPermissionRequest(FragmentActivity activity, String permission, PermissionListener listener) {
        RxPermissions permissions = new RxPermissions(activity);
        permissions.setLogging(true);
        permissions.request(permission)
                .subscribe(aBoolean -> {
                    if(listener != null)listener.permissionResult(aBoolean);
                    LogUtil.i("获取单个权限--:" + aBoolean);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    protected int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 复制字符串
     */
    protected void copyStrings(String string) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setText(string);
            ToastUtil.showCenterToast(getResources().getString(R.string.copy_success));
        }
    }

    protected Fragment mFragment = null;

    /**
     * 切换fragment
     * @param from
     * @param to
     * @param tag
     */
    protected void switchFragment(Fragment from, Fragment to, String tag){
        if(to != mFragment){
            mFragment = to;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            if (!to.isAdded()) {  // 先判断是否被add过
                // 隐藏当前的fragment，add下一个到activity中
                if (from != null) {
                    fragmentTransaction.hide(from);
                }
                fragmentTransaction.add(R.id.container, to , tag).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                if (from != null) {
                    fragmentTransaction.hide(from);
                }
                fragmentTransaction.show(to).commitAllowingStateLoss();
            }
        }
    }
}
