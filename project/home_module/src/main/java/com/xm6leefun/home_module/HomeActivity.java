package com.xm6leefun.home_module;

import android.content.Intent;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BasePresenterActivity;
import com.xm6leefun.common.bean.AppVersionBean;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.dialog.UpdateDialogFragment;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.HelpUtils;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataBusBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.busbean.LoginOutBusBean;
import com.xm6leefun.export_module.busbean.LoginResultBean;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.home_module.mvp.HomeContract;
import com.xm6leefun.home_module.mvp.HomePresenter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.util.List;

import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 13:36
 */
@Route(path = ModuleRouterTable.HOME_INDEX_PAGE)
public class HomeActivity extends BasePresenterActivity<HomePresenter> implements RadioGroup.OnCheckedChangeListener, HomeContract.IView {
    @BindView(R2.id.radiogroup)
    RadioGroup mGroup;
    @BindView(R2.id.wallet)
    RadioButton wallet;
    @BindView(R2.id.points_exchange)
    RadioButton points_exchange;
    @BindView(R2.id.me)
    RadioButton me;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    private Fragment homeWalletFragment,pointsExchangeAuthFragment,pointsExchangeFragment,meFragment;
    @Override
    protected void initView() {
        StatusBarUtil.setStatusBg(this);
        EventBus.getDefault().register(this);
        mGroup.setOnCheckedChangeListener(this);
        homeWalletFragment = (Fragment) ARouter.getInstance().build(ModuleRouterTable.HOME_WALLET_FRAGMENT).navigation();
        pointsExchangeAuthFragment = (Fragment) ARouter.getInstance().build(ModuleRouterTable.HOME_POINTS_EXCHANGE_AUTH_FRAGMENT).navigation();
        pointsExchangeFragment = (Fragment) ARouter.getInstance().build(ModuleRouterTable.HOME_POINTS_EXCHANGE_FRAGMENT).navigation();
        meFragment = (Fragment) ARouter.getInstance().build(ModuleRouterTable.HOME_ME_FRAGMENT).navigation();
        mGroup.check(R.id.wallet);//选中第一个
    }

    @Override
    protected void initData() {
        //获取当前钱包
        presenter.getCurrWallet();
        //获取版本信息
        presenter.getAppVersion();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.wallet) {
            switchFragment(mFragment, homeWalletFragment, "HomeWalletFragment");
        } else if (checkedId == R.id.points_exchange) {
            String userId = SharePreferenceUtil.getString(AppAllKey.User_ID);
            if(StrUtils.isEmpty(userId)) switchFragment(mFragment, pointsExchangeAuthFragment, "PointsExchangeAuthFragment");
            else switchFragment(mFragment, pointsExchangeFragment, "PointsExchangeFragment");
        } else if (checkedId == R.id.me) {
            switchFragment(mFragment, meFragment, "MeFragment");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_QR_CODE://处理扫码结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_QR_SCAN){
                    if(data != null){
                        String result = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN);
                        String points = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_POINTS_SCAN);
                        int type = data.getIntExtra(ModuleServiceUtils.ADDRESS_TYPE,0);
                        switch (type){
                            case 1://存储地址
                                ModuleServiceUtils.navigateEditAddressPage(true,result);
                                break;
                            case 2://转账
                                if(ftBeans != null && ftBeans.size() > 0) {//默认选择第一个积分类型
                                    ModuleServiceUtils.navigateSendPointsPage(ftBeans.get(0),mCurrAddress, result,points);
                                }else{
                                    ToastUtil.showCenterToast(getString(R.string.home_points_empty_str));
                                }
                                break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    //当前地址
    private String mCurrAddress;
    private Wallet_Main walletMain;
    private List<HomeDataResultBean.FtBean> ftBeans;

    @Override
    public void getHomeDataSuccess(HomeDataResultBean homeDataResultBean,String address) {
        ftBeans = homeDataResultBean.getFt();
        EventBus.getDefault().postSticky(new HomeDataBusBean(address,homeDataResultBean));//发送粘性事件，更新首页资产数据
    }

    @Override
    public void getHomeDataFail(String msg,String address) {
        EventBus.getDefault().postSticky(new HomeDataBusBean(address,new HomeDataResultBean()));//发送粘性事件，更新首页资产数据
    }

    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        this.walletMain = walletMain;
        mCurrAddress = walletMain.getAddress();
        //获取首页资产
        presenter.getHomeData(mCurrAddress);
        EventBus.getDefault().postSticky(walletMain);//发送粘性事件，更新首页对应各个组件的数据
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void getAppVersionSuccess(AppVersionBean appVersionBean) {
        int localVersion = HelpUtils.getLocalVersion(this);

        int num = Integer.parseInt(appVersionBean.getVersion_num());

        if (num > localVersion) {
            boolean isforce;
            if (appVersionBean.getIs_force() == 1) {//强制更新
                isforce = true;
            } else {//提示更新
                isforce = false;
            }
            String downLoadUrl = appVersionBean.getVersion_url();
            String remark = appVersionBean.getRemark();
            String versionName = appVersionBean.getVersion_name();
            UpdateDialogFragment
                    .newInstance(remark, versionName,downLoadUrl, isforce)
                    .show(mFragmentManager, UpdateDialogFragment.TAG);
        }
    }

    @Override
    public void getAppVersionFail(String errorMessage) {}

    /**
     * 刷新当前首页钱包数据
     * @param refreshWalletBusBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshWalletData(RefreshWalletDataBusBean refreshWalletBusBean){
        presenter.getCurrWallet();
    }

    /**
     * 登录成功刷新界面
     * @param userInfoBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshUserData(LoginResultBean.UserInfoBean userInfoBean) {
        //登录成功后切换到积分兑换界面
        switchFragment(mFragment, pointsExchangeFragment, "PointsExchangeFragment");
        if(walletMain!=null)
            EventBus.getDefault().postSticky(walletMain);//发送粘性事件，更新首页对应各个组件的数据;
    }
    /**
     * 退出登录刷新界面，重新登录真唯度账户
     * @param loginOutBusBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginOut(LoginOutBusBean loginOutBusBean) {
        ToastUtil.showCenterToast(getString(R.string.home_relogin_tips));
        //登录成功后切换到积分兑换界面
        switchFragment(mFragment, pointsExchangeAuthFragment, "PointsExchangeAuthFragment");
        ModuleServiceUtils.navigateLoginPage(false);//打开登录界面，并重置数据
    }


    private long lastClickBackTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickBackTime < 2000) {
                ActivityUtil.closeAllActivity();
                System.exit(0);
            } else {
                ToastUtil.showCenterToast(getString(R.string.press_again_to_exit));
                lastClickBackTime = currentTime;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
