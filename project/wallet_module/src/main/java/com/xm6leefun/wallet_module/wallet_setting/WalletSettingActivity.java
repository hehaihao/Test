package com.xm6leefun.wallet_module.wallet_setting;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.dialog.HintDialog;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.BackupTipsActivity;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.reset_psw.ResetPswActivity;
import com.xm6leefun.wallet_module.wallet_manage.adapter.WalletListAdapter;
import com.xm6leefun.wallet_module.wallet_setting.dialog.InputDialog;
import com.xm6leefun.wallet_module.wallet_setting.mvp.WalletSettingContract;
import com.xm6leefun.wallet_module.wallet_setting.mvp.WalletSettingPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * @Description:钱包设置
 * @Author: hhh
 * @CreateDate: 2021/3/30 10:14
 */
@Route(path = ModuleRouterTable.HOME_WALLET_SETTING)
public class WalletSettingActivity extends BaseToolbarPresenterActivity<WalletSettingPresenter> implements WalletSettingContract.IView {

    @BindView(R2.id.wallet_name_tv)
    TextView wallet_name_tv;
    @BindView(R2.id.wallet_address_tv)
    TextView wallet_address_tv;
    @BindView(R2.id.wallet_forget_psw)
    TextView wallet_forget_psw;
    @BindView(R2.id.wallet_address_switch_quick_pay)
    Switch wallet_address_switch_quick_pay;
    @BindView(R2.id.wallet_export_words_layout)
    RelativeLayout mRLWords;
    @BindView(R2.id.wallet_export_keystore_layout)
    RelativeLayout mRLKeystore;

    private InputDialog inputDialog;

    private WalletListAdapter adapter;
    @Override
    protected WalletSettingPresenter createPresenter() {
        return new WalletSettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_setting_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.wallet_setting_title_str);
        addOnClickListeners(
                R.id.wallet_name_layout,
                R.id.wallet_address_layout,
                R.id.wallet_reset_psw_layout,
                R.id.wallet_forget_psw,
                R.id.wallet_no_psw_pay_layout,
                R.id.wallet_export_words_layout,
                R.id.wallet_export_keystore_layout,
                R.id.wallet_export_private_key_layout,
                R.id.wallet_remove_layout);
    }

    private String mCurrAddress = "";
    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            mCurrAddress = args.getString(ModuleServiceUtils.ADDRESS,"");
            wallet_address_tv.setText(mCurrAddress);
            presenter.getWalletByAddress(mCurrAddress);
        }
    }

    private Wallet_Main mWalletMain;

    @Override
    public void checkPswResult(boolean isPass, String psw,KeysInfo keysInfo,int intentType) {
        if(checkPswListener != null)checkPswListener.checkPswResult(isPass);
        if(isPass){//验证通过
            dismissInputDialog();
            dealResult(psw,keysInfo,intentType);
        }else{
            ToastUtil.showCenterToast(getString(R.string.import_by_keystore_psw_error));
        }
    }

    /**
     * 取消弹窗
     */
    private void dismissInputDialog() {
        if(inputDialog != null && inputDialog.isAdded())
            inputDialog.dismiss();
    }

    @Override
    public void getWalletByAddressSuccess(Wallet_Main walletMain) {
        this.mWalletMain = walletMain;
        wallet_name_tv.setText(walletMain.getWalletName());
        wallet_address_tv.setText(walletMain.getAddress());
        if (StrUtils.isEmpty(walletMain.getWordsJson())) {
            mRLWords.setVisibility(View.GONE);
        } else {
            mRLWords.setVisibility(View.VISIBLE);
        }
        wallet_address_switch_quick_pay.setChecked(walletMain.isQuickPay());
    }

    /**
     * 移除钱包后，没有钱包数据
     */
    @Override
    public void noWalletData() {
        ActivityUtil.closeAllActivity();
        ModuleServiceUtils.navigateFirstPage(true);
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    /**
     * 移除钱包后，有钱包数据，选择第一个
     */
    @Override
    public void setFirstWalletCheckedSuccess() {
        ActivityUtil.closeAllActivity();
        ModuleServiceUtils.navigateHomePage();
    }

    /**
     * 钱包名称修改成功
     */
    @Override
    public void modifyWalletNameSuccess(String walletName) {
        wallet_name_tv.setText(walletName);
        dismissInputDialog();
        EventBus.getDefault().post(new RefreshWalletDataBusBean());
    }

    /**
     * 设置免密支付成功
     * @param status
     */
    @Override
    public void setSelectedWalletQuickPaySuccess(boolean status) {
        wallet_address_switch_quick_pay.setChecked(status);
    }

    @Override
    public void getPromptInfoSuccess(String promptInfoStr) {
        if(StrUtils.isEmpty(promptInfoStr)){
            promptInfoStr = getString(R.string.wallet_setting_empty_desc_str);
        }
        inputDialog = getBuilder()
                .setDialogType(InputDialog.Wallet_prompt_info)
                .setContent(promptInfoStr).build();
        inputDialog.show(mFragmentManager,InputDialog.TAG);
    }

    /**
     * 获取钱包列表成功
     * @param walletMains
     */
    @Override
    public void getWalletListSuccess(List<Wallet_Main> walletMains) {
        if(walletMains.size() == 1){//只有一个钱包
            showRemoveDialog();
        }else if(walletMains.size() > 1){//多个钱包
            ModuleServiceUtils.navigateRemoveWalletPage(mCurrAddress);
        }
    }

    /**
     * 处理数据跳转
     * @param keysInfo
     * @param intentType
     */
    private void dealResult(String psw,KeysInfo keysInfo, int intentType) {
        Bundle bundle = new Bundle();
        CusCreateWalletBean cusCreateWallet = new CusCreateWalletBean();
        cusCreateWallet.setKeysInfo(keysInfo);
        cusCreateWallet.setPwd(psw);
        cusCreateWallet.setAddress(mCurrAddress);
        cusCreateWallet.setWhere(2);  // 0 创建，1 导入，2 备份
        switch (intentType){
            case 0://助记词
                cusCreateWallet.setType(0);
                bundle.putParcelable(BackupTipsActivity.CUS_DATA, cusCreateWallet);
                ActivityUtil.startActivity(BackupTipsActivity.class, false, bundle);
                break;
            case 1://私钥
                cusCreateWallet.setType(1);  // 0 助记词，1 私钥, 2 keystore
                bundle.putParcelable(BackupTipsActivity.CUS_DATA, cusCreateWallet);
                ActivityUtil.startActivity(BackupTipsActivity.class, false, bundle);
                break;
            case 2://keystore
                cusCreateWallet.setType(2);  // 0 助记词，1 私钥, 2 keystore
                bundle.putParcelable(BackupTipsActivity.CUS_DATA, cusCreateWallet);
                ActivityUtil.startActivity(BackupTipsActivity.class, false, bundle);
                break;
            case 3://移除钱包
                presenter.removeWallet(mCurrAddress);
                break;
            case 4://免密支付
                boolean toStatue = !wallet_address_switch_quick_pay.isChecked();
                presenter.setSelectedWalletQuickPay(mCurrAddress,toStatue);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.wallet_name_layout) {  // 修改钱包名称
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setContent(wallet_name_tv.getText().toString().trim())
                        .setDialogType(InputDialog.Wallet_Name)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        } else if (id == R.id.wallet_address_layout) {  // 钱包地址
            if (DoubleClickUtils.isNoDoubleClick()) {
                copyStrings(wallet_address_tv.getText().toString().trim());
            }
        } else if (id == R.id.wallet_reset_psw_layout) {  // 重置密码
            if (DoubleClickUtils.isNoDoubleClick()) {
                showResetDialog();
            }
        } else if (id == R.id.wallet_forget_psw) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                presenter.getPromptInfo(mCurrAddress);
            }
        } else if (id == R.id.wallet_export_words_layout) {  // 导出助记词
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_words)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        } else if (id == R.id.wallet_export_keystore_layout) {  // 导出keystore
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_keystore)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        } else if (id == R.id.wallet_export_private_key_layout) {  // 导出私钥
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_privatekey)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        } else if (id == R.id.wallet_remove_layout) {  // 移除钱包
            if (DoubleClickUtils.isNoDoubleClick()) {
                presenter.getWalletList();//获取钱包列表
            }
        } else if (id == R.id.wallet_no_psw_pay_layout) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_quick_pay)
                        .build();
                inputDialog.show(mFragmentManager,InputDialog.TAG);
            }
        }
    }

    private HintDialog hintDialog;

    /**
     * 重置密码
     */
    private void showResetDialog() {
        if(hintDialog == null) {
            hintDialog = new HintDialog.Builder()
                    .setIsCenterGravity(false)
                    .setTitle(getResources().getString(R.string.wallet_setting_reset_psw_dialog_title))
                    .setContent(getResources().getString(R.string.wallet_setting_reset_psw_dialog_content))
                    .setClickListener(() -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(ResetPswActivity.ADDRESS, mCurrAddress);
                            ActivityUtil.startActivity(ResetPswActivity.class, false, bundle);
                    })
                    .build();
        }
        if(!hintDialog.isAdded())
            hintDialog.show(mFragmentManager, HintDialog.TAG);
    }

    /**
     * 移除钱包提示
     */
    private void showRemoveDialog() {
        if(hintDialog == null) {
            hintDialog = new HintDialog.Builder()
                    .setIsCenterGravity(false)
                    .setTitle(getResources().getString(R.string.wallet_setting_tips_title))
                    .setContent(getResources().getString(R.string.wallet_setting_remove_dialog_content))
                    .setSureText(getResources().getString(R.string.wallet_setting_remove_dialog_sure))
                    .setClickListener(() -> {
                        inputDialog = getBuilder().setDialogType(InputDialog.Wallet_remove)
                                .build();
                        inputDialog.show(mFragmentManager,InputDialog.TAG);
                    })
                    .build();
        }
        if(!hintDialog.isAdded())
            hintDialog.show(mFragmentManager, HintDialog.TAG);
    }

    private InputDialog.CheckPswListener checkPswListener;
    private InputDialog.Builder builder;
    private InputDialog.Builder getBuilder() {
        if(builder == null)
            builder = new InputDialog.Builder();
        builder.setContent("");//重置内容
        builder.setClickListener((inputType, content, listener) -> {
            this.checkPswListener = listener;
            switch (inputType){
                case InputDialog.Wallet_prompt_info:  // 密码提示词
                    dismissInputDialog();
                    break;
                case InputDialog.Wallet_Name://修改钱包名
                    presenter.modifyWalletName(mCurrAddress, content);
                    break;
                case InputDialog.Wallet_words://导出助记词
                    presenter.checkPsw(mCurrAddress, content, 0, WalletSettingActivity.this.getString(R.string.password_wrong));
                    break;
                case InputDialog.Wallet_keystore://导出keystore
                    presenter.checkPsw(mCurrAddress, content, 2, WalletSettingActivity.this.getString(R.string.password_wrong));
                    break;
                case InputDialog.Wallet_privatekey://导出私钥
                    presenter.checkPsw(mCurrAddress, content, 1, WalletSettingActivity.this.getString(R.string.password_wrong));
                    break;
                case InputDialog.Wallet_remove://移除钱包
                    presenter.checkPsw(mCurrAddress, content, 3, WalletSettingActivity.this.getString(R.string.password_wrong));
                    break;
                case InputDialog.Wallet_quick_pay:  // 免密支付
                    presenter.checkPsw(mCurrAddress, content, 4, WalletSettingActivity.this.getString(R.string.password_wrong));
                    break;
            }
        });
        return builder;
    }
}
