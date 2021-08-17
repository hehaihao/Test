package com.xm6leefun.points_module.zwd_login;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.zwd_login.adapter.RecordsListPageAdapter;
import com.xm6leefun.points_module.zwd_login.mvp.LoginContract;
import com.xm6leefun.points_module.zwd_login.mvp.LoginPresenter;
import com.xm6leefun.export_module.busbean.LoginResultBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/11/25 14:37
 */
@Route(path = ModuleRouterTable.POINTS_LOGIN_PAGE)
public class LoginActivity extends BaseToolbarPresenterActivity<LoginPresenter> implements LoginContract.IView
        , TabLayout.OnTabSelectedListener
        ,CodeLoginFragment.OnContentListener {

    @BindView(R2.id.tab_login)
    TabLayout tab_login;
    @BindView(R2.id.vp_login)
    ViewPager vp_login;
    private RecordsListPageAdapter recordsListPageAdapter;
    private ArrayList<Fragment> fragments;
    private Fragment mFragment = null;//当前选中
    private boolean isSwitchUser = false;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.app_login_register);
        initTab();
    }

    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            isSwitchUser = args.getBoolean(ModuleServiceUtils.IS_SWITCH_USER,false);
        }
        //跳转登录界面时，为了确保数据正确，需要清除用户信息
        removeLoginData(isSwitchUser);

    }

    private void initTab() {
        tab_login.addTab(tab_login.newTab());
        tab_login.addTab(tab_login.newTab());

        String[] titles = {getString(R.string.app_login_code), getString(R.string.app_login_pwd)};
        fragments = new ArrayList<>();
        fragments.add(CodeLoginFragment.getInstance());
        fragments.add(PwdLoginFragment.getInstance());
        mFragment = fragments.get(0);//设置默认值
        recordsListPageAdapter = new RecordsListPageAdapter(
                getSupportFragmentManager(),
                titles,
                fragments);
        vp_login.setAdapter(recordsListPageAdapter);
        vp_login.setOffscreenPageLimit(2);
        tab_login.addOnTabSelectedListener(this);
        tab_login.setupWithViewPager(vp_login);

    }

    @Override
    public void sendCodeSuccess(CodeLoginFragment.SendSmsCallBack sendSmsCallBack) {
        if(sendSmsCallBack != null)sendSmsCallBack.onSuccess();
    }

    @Override
    public void sendCodeFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }

    @Override
    public void loginSuccess(LoginResultBean loginResultBean) {
        saveLoginData(loginResultBean.getUserInfo());
        EventBus.getDefault().post(loginResultBean.getUserInfo());
        ActivityUtil.finishActivity(this);
    }

    @Override
    public void loginFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }

    /**
     * 登录
     */
    public void login(View view) {
        if (mFragment instanceof CodeLoginFragment) {  // 验证码登录
            CodeLoginFragment codeLoginFragment = (CodeLoginFragment) mFragment;
            String phone = codeLoginFragment.getPhone();
            String code = codeLoginFragment.getCode();
            if (StrUtils.isEmpty(phone)) {
                ToastUtil.showCenterToast(getResources().getString(R.string.phone_is_null));
                return;
            }
            if (StrUtils.isEmpty(code)) {
                ToastUtil.showCenterToast(getResources().getString(R.string.code_not_empty));
                return;
            }
            presenter.login(phone, code);
        } else if(mFragment instanceof PwdLoginFragment){  // 密码登录
            PwdLoginFragment pwdLoginFragment = (PwdLoginFragment) mFragment;
            String phone = pwdLoginFragment.getPhone();
            String pws = pwdLoginFragment.getPwd();
            if (StrUtils.isEmpty(phone)) {
                ToastUtil.showCenterToast(getResources().getString(R.string.phone_is_null));
                return;
            }
            if (StrUtils.isEmpty(pws)) {
                ToastUtil.showCenterToast(getResources().getString(R.string.pwd_not_empty));
                return;
            }
            presenter.pswLogin(phone, pws);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragment = fragments.get(tab.getPosition());
        View view = tab.getCustomView();
        if(view == null){
            tab.setCustomView(R.layout.tab_item_layout);
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
        textView.setTextColor(tab_login.getTabTextColors());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(tab.getText());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if(view == null){
            tab.setCustomView(R.layout.tab_item_layout);
        }
        TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
        textView.setTextColor(tab_login.getTabTextColors());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(tab.getText());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void sendSms(String phone, CodeLoginFragment.SendSmsCallBack sendSmsCallBack) {
        presenter.sendCode(phone,"userRegister",sendSmsCallBack);
    }

    /**
     * 记录登录信息
     * @param userInfoBean
     */
    public void saveLoginData(LoginResultBean.UserInfoBean userInfoBean){
        if(userInfoBean == null)return;
        SharePreferenceUtil.setString(AppAllKey.User_ID,userInfoBean.getUserId());
        SharePreferenceUtil.setString(AppAllKey.LOGIN_ID,userInfoBean.getId());
        SharePreferenceUtil.setString(AppAllKey.HEAD_PORTRAIT,userInfoBean.getHeadPortrait());
        String mobile = userInfoBean.getMobile();
        if(!StrUtils.isEmpty(mobile) && !"0".equals(mobile)) {
            SharePreferenceUtil.setString(AppAllKey.MOBILE, mobile);
        }
        SharePreferenceUtil.setString(AppAllKey.TOKEN,userInfoBean.getToken());
        SharePreferenceUtil.setString(AppAllKey.NICK_NAME,userInfoBean.getNickName());
        SharePreferenceUtil.setString(AppAllKey.WX_NAME,userInfoBean.getWxName());
        SharePreferenceUtil.setString(AppAllKey.USER_UUID,userInfoBean.getUserUuid());
        SharePreferenceUtil.setString(AppAllKey.OPEN_ID,userInfoBean.getOpenId());
        SharePreferenceUtil.setInt(AppAllKey.IS_SETPWD,userInfoBean.getIsSetPwd());
    }
    /**
     * 移除登录信息
     * @param isSwitchUser
     */
    public void removeLoginData(boolean isSwitchUser){
        if(isSwitchUser)return;//切换账户，不需要退出登录
        SharePreferenceUtil.removeByKey(AppAllKey.User_ID);
        SharePreferenceUtil.removeByKey(AppAllKey.LOGIN_ID);
        SharePreferenceUtil.removeByKey(AppAllKey.HEAD_PORTRAIT);
        SharePreferenceUtil.removeByKey(AppAllKey.MOBILE);
        SharePreferenceUtil.removeByKey(AppAllKey.TOKEN);
        SharePreferenceUtil.removeByKey(AppAllKey.NICK_NAME);
        SharePreferenceUtil.removeByKey(AppAllKey.WX_NAME);
        SharePreferenceUtil.removeByKey(AppAllKey.USER_UUID);
        SharePreferenceUtil.removeByKey(AppAllKey.OPEN_ID);
        SharePreferenceUtil.removeByKey(AppAllKey.IS_SETPWD);
    }
}
