package com.xm6leefun.common.app.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xm6leefun.common.R2;
import com.xm6leefun.common.listener.PermissionListener;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.widget.svprogress.SVProgressHUD;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/27 13:57
 */
public abstract class BaseToolbarComActivity extends BaseComActivity implements View.OnClickListener{

    @BindView(R2.id.root_view)
    protected LinearLayout root_view;
    @BindView(R2.id.base_topBar_iv_back)
    protected ImageView topBarIvBack;
    @BindView(R2.id.base_topBar_tv_title)
    protected TextView topBarTvTitle;
    @BindView(R2.id.base_topBar_tv_right)
    protected TextView topBarTvRight;
    @BindView(R2.id.base_topBar_iv_right)
    protected ImageView topBarIvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTopBar();
    }

    /**
     * 初始化自定义topBar
     */
    private void initTopBar() {
        if (topBarIvBack != null) {
            topBarIvBack.setOnClickListener(view -> ActivityUtil.finishActivity(ActivityUtil.getCurrentActivity()));
        }
    }

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

}
