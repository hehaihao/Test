package com.xm6leefun.common.app.fragment;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xm6leefun.common.R;
import com.xm6leefun.common.listener.PermissionListener;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.widget.svprogress.SVProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description:基类BaseFragment
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public abstract class BaseComFragment extends Fragment implements View.OnClickListener {

    protected SVProgressHUD svProgressHUD;
    protected Context mContext;
    protected FragmentManager mChildFragmentManager;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChildFragmentManager = getChildFragmentManager();
    }

    protected View view = null;
    protected Unbinder mUnbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        svProgressHUD = new SVProgressHUD(getActivity());
        mUnbinder = ButterKnife.bind(this,view);
        //得到context,在后面的子类Fragment中都可以直接调用
        mContext = ActivityUtil.getCurrentActivity();
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //do something
    }

    private void initListener() {
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
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setText(string);
            ToastUtil.showCenterToast(getContext().getResources().getString(R.string.copy_success));
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
            FragmentTransaction fragmentTransaction = mChildFragmentManager.beginTransaction();
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
