package com.xm6leefun.wallet_module.remove;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.remove.mvp.RemoveWalletContract;
import com.xm6leefun.wallet_module.remove.mvp.RemoveWalletPresenter;
import com.xm6leefun.wallet_module.wallet_setting.dialog.InputDialog;

/**
 * @Description:移除钱包
 * @Author: hhh
 * @CreateDate: 2021/4/21 15:26
 */
@Route(path = ModuleRouterTable.REMOVE_WALLET_PAGE)
public class RemoveWalletActivity extends BaseToolbarPresenterActivity<RemoveWalletPresenter> implements RemoveWalletContract.IView {
    @Override
    protected RemoveWalletPresenter createPresenter() {
        return new RemoveWalletPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remove_wallet_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(getResources().getString(R.string.wallet_remove_title));
        addOnClickListeners(R.id.wallet_remove_tv_remove);
    }

    private String address;
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            address = extras.getString(ModuleServiceUtils.ADDRESS,"");
        }
    }


    private InputDialog inputDialog;
    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.wallet_remove_tv_remove){
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_remove)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        }
    }

    /**
     * 取消弹窗
     */
    private void dismissInputDialog() {
        if(inputDialog != null && inputDialog.isAdded())
            inputDialog.dismiss();
    }

    private InputDialog.CheckPswListener checkPswListener;
    private InputDialog.Builder builder;
    private InputDialog.Builder getBuilder() {
        if(builder == null)
            builder = new InputDialog.Builder();
        builder.setContent("");//重置内容
        builder.setClickListener((inputTye, content, listener) -> {
            this.checkPswListener = listener;
            presenter.checkPsw(address, content, getString(R.string.password_wrong));
        });
        return builder;
    }

    @Override
    public void checkPswResult(boolean isPass, String psw, KeysInfo keysInfo) {
        if(checkPswListener != null)checkPswListener.checkPswResult(isPass);
        if(isPass){//验证通过
            dismissInputDialog();
            presenter.removeWallet(this,address);
        }else{
            ToastUtil.showCenterToast(getString(R.string.import_by_keystore_psw_error));
        }
    }

    @Override
    public void removeWalletSuccess() {

    }

    /**
     * 移除钱包后，没有钱包数据
     */
    @Override
    public void noWalletData() {
        ActivityUtil.closeAllActivity();
        ModuleServiceUtils.navigateFirstPage(true);
    }

    /**
     * 移除钱包后，有钱包数据，选择第一个
     */
    @Override
    public void setFirstWalletCheckedSuccess() {
        ActivityUtil.closeAllActivity();
        ModuleServiceUtils.navigateHomePage();
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
