package com.xm6leefun.wallet_module.importby.prikey;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.importby.prikey.mvp.ImportByPrikeyContract;
import com.xm6leefun.wallet_module.importby.prikey.mvp.ImportByPrikeyPresenter;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * @Description:私钥导入
 * @Author: hhh
 * @CreateDate: 2021/3/27 12:26
 */
public class ImportByPrikeyActivity extends BaseToolbarPresenterActivity<ImportByPrikeyPresenter> implements ImportByPrikeyContract.IView {
    @BindView(R2.id.import_wallet_ed_prikey)
    EditText edPrikey;
    @BindView(R2.id.import_wallet_ed_name)
    EditText edName;
    @BindView(R2.id.import_wallet_ed_pwd)
    EditText edPwd;
    @BindView(R2.id.import_wallet_iv_show_or_hide)
    ImageView ivShowOrHide;
    @BindView(R2.id.import_wallet_ed_pwd_confirm)
    EditText edPwdConfirm;
    @BindView(R2.id.import_wallet_ed_pwd_hint)
    EditText edPwdHint;
    @Override
    protected ImportByPrikeyPresenter createPresenter() {
        return new ImportByPrikeyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_import_privatekey_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.import_wallet_title);
        topBarTvRight.setVisibility(View.GONE);
        topBarIvRight.setVisibility(View.VISIBLE);
        topBarIvRight.setImageResource(R.mipmap.home_scaner_icon);
        addOnClickListeners(R.id.base_topBar_iv_right,R.id.import_wallet_iv_show_or_hide, R.id.import_wallet_tv_import);
    }

    @Override
    protected void initData() {

    }

    private boolean isShow = false;
    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.base_topBar_iv_right){//扫一扫
            ARouter.getInstance().build(ModuleRouterTable.SCAN_PAGE).navigation(this,ModuleServiceUtils.REQ_QR_CODE);
        }else if (id == R.id.import_wallet_tv_import) {//导入
            if (DoubleClickUtils.isNoDoubleClick()) {
                dataCheck();
            }
        } else if (id == R.id.import_wallet_iv_show_or_hide) {  // 显示或隐藏密码
            if (DoubleClickUtils.isNoDoubleClick()) {
                isShow = !isShow;
                if (isShow) {  // 显示
                    edPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edPwdConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowOrHide.setImageResource(R.mipmap.eye_icon);
                    edPwd.setSelection(edPwd.getText().toString().trim().length());
                    edPwdConfirm.setSelection(edPwdConfirm.getText().toString().trim().length());
                } else {
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPwdConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowOrHide.setImageResource(R.mipmap.eye_close_icon);
                    edPwd.setSelection(edPwd.getText().toString().trim().length());
                    edPwdConfirm.setSelection(edPwdConfirm.getText().toString().trim().length());
                }
            }
        }
    }

    private void dataCheck() {
        String privateKeyStr = edPrikey.getText().toString().trim();
        String walletName = edName.getText().toString().trim();
        String setPwd = edPwd.getText().toString().trim();
        String confirmPwd = edPwdConfirm.getText().toString().trim();
        String promptInfo = edPwdHint.getText().toString().trim();
        if (privateKeyStr.length() != 64) {
            ToastUtil.showCenterToast(getString(R.string.import_by_private_key_error));
            return;
        }
        if (StrUtils.isEmpty(walletName)) {
            ToastUtil.showCenterToast(getString(R.string.import_wallet_name_input));
            return;
        }
        if (StrUtils.isEmpty(setPwd)) {
            ToastUtil.showCenterToast(getString(R.string.import_wallet_pwd_input));
            return;
        }
        if (StrUtils.isEmpty(confirmPwd)) {
            ToastUtil.showCenterToast(getString(R.string.import_wallet_pwd_confirm_input));
            return;
        }
        if (!setPwd.equals(confirmPwd)) {
            ToastUtil.showCenterToast(getString(R.string.import_wallet_pwd_confirm_mismatch));
            return;
        }
        if (!StrUtils.isValidatePayPwd(setPwd)) {
            ToastUtil.showCenterToast(getString(R.string.import_wallet_pwd_incompatible));
            return;
        }
        presenter.importByPrikey(this,walletName,promptInfo,setPwd,privateKeyStr);
    }


    @Override
    public void importByPrikeySuccess(CusCreateWalletBean createWallet) {
        svProgressHUD.showSuccessWithStatus(getResources().getString(R.string.words_confirm_verify_pass));
        EventBus.getDefault().post(new RefreshWalletDataBusBean());//刷新首页
        ModuleServiceUtils.navigateHomePage();
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_QR_CODE://处理扫码结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_QR_SCAN){
                    if(data != null){
                        String result = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN);
                        edPrikey.setText(result);
                    }
                }
                break;
        }
    }
}
