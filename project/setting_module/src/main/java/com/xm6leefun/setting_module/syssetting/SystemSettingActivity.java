package com.xm6leefun.setting_module.syssetting;



import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.HelpUtils;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.R2;
import com.xm6leefun.common.bean.AppVersionBean;
import com.xm6leefun.setting_module.nodesetting.NodeSettingActivity;
import com.xm6leefun.setting_module.syssetting.mvp.SystemSettingContract;
import com.xm6leefun.setting_module.syssetting.mvp.SystemSettingPresenter;
import com.xm6leefun.common.dialog.UpdateDialogFragment;

import butterknife.BindView;


/**
 * @Description:系统设置
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */

@Route(path = ModuleRouterTable.SYSTEM_SETTING)
public class SystemSettingActivity extends BaseToolbarPresenterActivity<SystemSettingPresenter> implements SystemSettingContract.IView {


    @BindView(R2.id.switch_touch_id)
    Switch switchTouchId;

    @BindView(R2.id.version_update_layout)
    LinearLayout versionUpdateLayout;


    @BindView(R2.id.version_tv)
    TextView versionTv;


    @Override
    protected SystemSettingPresenter createPresenter() {
        return new SystemSettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_settings;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.system_setting_str);
        addOnClickListeners(R.id.node_layout, R.id.switch_touch_id, R.id.version_update_layout);
    }

    @Override
    protected void initData() {
        presenter.getTouchIDStatus();
        versionTv.setText("v" + HelpUtils.getLocalVersionName(this));

    }


    @Override
    public void getTouchIDStatus(boolean success) {
        switchTouchId.setChecked(success);
    }

    @Override
    public void OpenTouchID(boolean success) {
        SharePreferenceUtil.setBoolean(AppAllKey.TOUCH_ID_STATUS, success);
        switchTouchId.setChecked(success);
        if (!success) ToastUtil.showCenterToast(getString(R.string.open_touch_id_fail));

    }


    @Override
    public void CloseTouchID(boolean success) {
        SharePreferenceUtil.setBoolean(AppAllKey.TOUCH_ID_STATUS, success);
        switchTouchId.setChecked(success);
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
        } else {
            ToastUtil.showCenterToast(getString(R.string.new_updata));
        }

    }

    @Override
    public void getAppVersionFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.node_layout) {
            ActivityUtil.startActivity(NodeSettingActivity.class, false);
        } else if (id == R.id.switch_touch_id) {
            if (switchTouchId.isChecked()) {
                presenter.openTouchID(this);
            } else {
                presenter.closeTouchID();
            }
        } else if (id == R.id.version_update_layout) {
            presenter.getAppVersion();
        }
    }

}
