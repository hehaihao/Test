package com.xm6leefun.wallet_module.create_wallet;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.BackupTipsActivity;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.create_wallet.dialog.SecurityTipDialog;
import com.xm6leefun.wallet_module.create_wallet.mvp.CreateWalletContract;
import com.xm6leefun.wallet_module.create_wallet.mvp.CreateWalletPresenter;

import butterknife.BindView;

/**
 * @Description: 创建钱包
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:02
 */
@Route(path = ModuleRouterTable.CREATE_WALLET_PAGE)
public class CreateWalletActivity extends BaseToolbarPresenterActivity<CreateWalletPresenter> implements
        CreateWalletContract.IView, CompoundButton.OnCheckedChangeListener {
    public static final String IS_FIRST = "isFirst";
    private boolean isFirst = false;//是否第一次创建
    @BindView(R2.id.id_wallet_tips)
    RelativeLayout id_wallet_tips;
    @BindView(R2.id.create_ed_name)
    EditText create_ed_name;
    @BindView(R2.id.create_ed_pwd)
    EditText create_ed_pwd;
    @BindView(R2.id.create_iv_show_or_hide)
    ImageView create_iv_show_or_hide;
    @BindView(R2.id.create_ed_pwd_confirm)
    EditText create_ed_pwd_confirm;
    @BindView(R2.id.create_ed_pwd_hint)
    EditText create_ed_pwd_hint;
    @BindView(R2.id.create_wallet_checkbox)
    CheckBox create_wallet_checkbox;
    @BindView(R2.id.create_wallet_tv_read_yet)
    TextView create_wallet_tv_read_yet;

    @Override
    protected CreateWalletPresenter createPresenter() {
        return new CreateWalletPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_wallet_layout;
    }

    @Override
    protected void initView() {
        create_wallet_checkbox.setOnCheckedChangeListener(this);
        addOnClickListeners(R.id.create_only_tv_create, R.id.create_iv_show_or_hide);
//        showSecurityDialog();
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) isFirst = extras.getBoolean(IS_FIRST,false);
        topBarIvBack.setVisibility(isFirst ? View.GONE : View.VISIBLE);
        topBarTvTitle.setText(isFirst ? R.string.create_id_wallet_title_str:R.string.create_wallet_create);
        id_wallet_tips.setVisibility(isFirst ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            create_wallet_checkbox.setButtonDrawable(getResources().getDrawable(R.mipmap.express_ico_area));
        } else {
            create_wallet_checkbox.setButtonDrawable(getResources().getDrawable(R.mipmap.express_ico_area_nor));
        }
    }

    private boolean isShow = false;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.create_only_tv_create) {
            dataCheck();
        } else if (id == R.id.create_iv_show_or_hide) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                isShow = !isShow;
                if (isShow) {  // 显示
                    create_ed_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    create_ed_pwd_confirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    create_iv_show_or_hide.setImageResource(R.mipmap.eye_icon);
                    create_ed_pwd.setSelection(create_ed_pwd.getText().toString().trim().length());
                    create_ed_pwd_confirm.setSelection(create_ed_pwd_confirm.getText().toString().trim().length());
                } else {
                    create_ed_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    create_ed_pwd_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    create_iv_show_or_hide.setImageResource(R.mipmap.eye_close_icon);
                    create_ed_pwd.setSelection(create_ed_pwd.getText().toString().trim().length());
                    create_ed_pwd_confirm.setSelection(create_ed_pwd_confirm.getText().toString().trim().length());
                }
            }
        }
    }

    /**
     * 检查数据合法性
     */
    private void dataCheck() {
        String setPwd = create_ed_pwd.getText().toString().trim();
        String confirmPwd = create_ed_pwd_confirm.getText().toString().trim();
        String promptInfo = create_ed_pwd_hint.getText().toString().trim();
        String walletName = create_ed_name.getText().toString().trim();
        if (StrUtils.isEmpty(walletName)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_wallet_name));
            return;
        }
        if (StrUtils.isEmpty(setPwd)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_pwd_is_null));
            return;
        }
        if (StrUtils.isEmpty(confirmPwd)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_pwd_confirm_pwd_is_null));
            return;
        }
        if (!setPwd.equals(confirmPwd)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_pwd_twice_pwd_is_not_same));
            return;
        }
        if (!StrUtils.isValidatePayPwd(setPwd)) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_pwd_pwd_is_error));
            return;
        }
        if (!create_wallet_checkbox.isChecked()) {
            ToastUtil.showCenterToast(getResources().getString(R.string.create_wallet_agree_first));
            return;
        }
        //创建钱包
        presenter.createWallet(isFirst,walletName, promptInfo, setPwd);
    }


    /**
     * 显示安全提示弹窗
     */
    private void showSecurityDialog() {
        SecurityTipDialog securityTipDialog = new SecurityTipDialog.Builder()
                .setClickListener(() -> ActivityUtil.finishActivity(CreateWalletActivity.this)).build();
        securityTipDialog.show(mFragmentManager, SecurityTipDialog.TAG);
    }

    @Override
    public void createWalletSuccess(CusCreateWalletBean createWallet) {
        Bundle args = new Bundle();
        args.putParcelable(BackupTipsActivity.CUS_DATA, createWallet);
        ActivityUtil.startActivity(BackupTipsActivity.class, false, args);//跳转备份界面
    }

    /**
     * 创建钱包失败
     *
     * @param errorMessage
     */
    @Override
    public void onLoadFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }

}
