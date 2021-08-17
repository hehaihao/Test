package com.xm6leefun.wallet_module.importby.words;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.importby.words.dialog.SelectPathDialog;
import com.xm6leefun.wallet_module.importby.words.mvp.ImportByWordsContract;
import com.xm6leefun.wallet_module.importby.words.mvp.ImportByWordsPresenter;
import com.xm6leefun.common.wallet.WalletUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @Description:助记词导入
 * @Author: hhh
 * @CreateDate: 2021/3/27 12:26
 */
public class ImportByWordsActivity extends BaseToolbarPresenterActivity<ImportByWordsPresenter> implements ImportByWordsContract.IView {
    @BindView(R2.id.import_wallet_ed_mnemonic)
    EditText edWords;
    @BindView(R2.id.import_wallet_tv_path)
    TextView tvPath;
    @BindView(R2.id.import_wallet_tv_path_type)
    TextView tvDefaultType;
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
    protected ImportByWordsPresenter createPresenter() {
        return new ImportByWordsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_import_words_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.import_wallet_title);
        addOnClickListeners(
                R.id.import_wallet_lin_path,
                R.id.import_wallet_iv_show_or_hide,
                R.id.import_wallet_tv_import);
        tvPath.setText(WalletUtil.getKeyWords());
    }

    @Override
    protected void initData() {

    }

    private boolean isShow = false;
    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.import_wallet_lin_path) {
            showSelectPathDialog();
        } else if (id == R.id.import_wallet_tv_import) {//导入
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
        String wordsStr = edWords.getText().toString().trim();
        String wordPath = tvPath.getText().toString().trim();
        String walletName = edName.getText().toString().trim();
        String setPwd = edPwd.getText().toString().trim();
        String confirmPwd = edPwdConfirm.getText().toString().trim();
        String promptInfo = edPwdHint.getText().toString().trim();
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
        presenter.importByWords(this,wordPath,walletName,promptInfo,setPwd,wordsStr);
    }

    private SelectPathDialog selectPathDialog;

    /**
     * 选择路径
     */
    private void showSelectPathDialog(){
        if(selectPathDialog == null)
            selectPathDialog = new SelectPathDialog.Builder()
                    .setSelectPath(tvPath.getText().toString().trim())
                    .setClickListener((path, isDefault) -> {
                        tvPath.setText(path);
                        tvDefaultType.setVisibility(isDefault ? View.VISIBLE : View.GONE);
                    }).build();
        if(!selectPathDialog.isAdded()) selectPathDialog.show(mFragmentManager, SelectPathDialog.TAG);
    }

    @Override
    public void importByWordsSuccess(CusCreateWalletBean createWallet) {
        svProgressHUD.showSuccessWithStatus(getResources().getString(R.string.words_confirm_verify_pass));
        EventBus.getDefault().post(new RefreshWalletDataBusBean());//刷新首页
        ModuleServiceUtils.navigateHomePage();
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
