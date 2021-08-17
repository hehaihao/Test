package com.xm6leefun.wallet_module.reset_psw;

import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xm6leefun.common.app.fragment.BasePresenterFragment;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.utils.TextWatcherUtils;
import com.xm6leefun.common.wallet.WalletUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.importby.words.dialog.SelectPathDialog;
import com.xm6leefun.wallet_module.reset_psw.mvp.ResetPswContract;
import com.xm6leefun.wallet_module.reset_psw.mvp.ResetPswPresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/31 13:50
 */
public class ResetPswFragment extends BasePresenterFragment<ResetPswPresenter> implements ResetPswContract.IView, View.OnClickListener {
    private int type;  // 0 助记词    1 私钥
    @BindView(R2.id.reset_pwd_tv_sub_title)
    TextView tvHint;
    @BindView(R2.id.reset_pwd_ed_mnemonic)
    EditText edWords;
    @BindView(R2.id.import_wallet_lin_path_all)
    LinearLayout pathLayout;
    @BindView(R2.id.import_wallet_tv_path)
    TextView tvPath;
    @BindView(R2.id.import_wallet_tv_path_type)
    TextView tvDefaultType;
    @BindView(R2.id.reset_pwd_ed_pwd)
    EditText edPwd;
    @BindView(R2.id.reset_pwd_iv_show_or_hide)
    ImageView ivShowOrHide;
    @BindView(R2.id.reset_pwd_ed_pwd_confirm)
    EditText edPwdConfirm;
    @BindView(R2.id.reset_pwd_ed_pwd_hint)
    EditText edPwdHint;
    @BindView(R2.id.reset_pwd_tv_import)
    TextView tvImport;

    private boolean isShow = false;
    private String address;

    public static ResetPswFragment getInstance(int type, String address){
        ResetPswFragment fragment = new ResetPswFragment();
        Bundle args = new Bundle();
        args.putInt(ResetPswActivity.TYPE, type);
        args.putString(ResetPswActivity.ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ResetPswPresenter createPresenter() {
        return new ResetPswPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_psw_layout;
    }

    @Override
    protected void initView() {
        tvPath.setText(WalletUtil.getKeyWords());
        ivShowOrHide.setOnClickListener(this);
        tvImport.setOnClickListener(this);
        enterKeyListener();
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args != null){
            address = args.getString(ResetPswActivity.ADDRESS,"");
            type = args.getInt(ResetPswActivity.TYPE,0);
            switch (type) {
                case 0:
                    tvHint.setText(getResources().getString(R.string.reset_pwd_title_words));
                    edWords.setHint(getResources().getString(R.string.reset_pwd_hint_words));
                    break;
                case 1:
                    tvHint.setText(getResources().getString(R.string.reset_pwd_title_pk));
                    edWords.setHint(getResources().getString(R.string.reset_pwd_hint_pk));
                    break;
            }
        }
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
        if(!selectPathDialog.isAdded()) selectPathDialog.show(mChildFragmentManager, SelectPathDialog.TAG);
    }


    /**
     * 监听回车键
     */
    private void enterKeyListener() {
        edWords.setOnEditorActionListener((textView, i, keyEvent) -> (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER));
        edPwd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edPwdConfirm.setFocusable(true);
                edPwdConfirm.requestFocus();
                return true;
            }
            return false;
        });
        edPwdConfirm.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edPwdHint.setFocusable(true);
                edPwdHint.requestFocus();
                return true;
            }
            return false;
        });
        edPwdHint.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                // 创建钱包
                dataCheck();
                return true;
            }
            return false;
        });
    }

    private void dataCheck() {
        String key = edWords.getText().toString().trim();
        String setPwd = edPwd.getText().toString().trim();
        String confirmPwd = edPwdConfirm.getText().toString().trim();
        String promptInfo = edPwdHint.getText().toString().trim();
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

        switch (type) {
            case 0:  // 助记词导入修改密码
                if (StrUtils.isEmpty(key)) {
                    ToastUtil.showCenterToast(getResources().getString(R.string.import_wallet_mnemonic_cannot_empty));
                    return;
                }
                String wordPath = tvPath.getText().toString().trim();
                presenter.resetPwdByWord(getActivity(),address, key, wordPath,setPwd, promptInfo);
                break;

            case 1:  // 私钥导入修改密码
                if (StrUtils.isEmpty(key)) {
                    ToastUtil.showCenterToast(getResources().getString(R.string.import_wallet_pk_cannot_empty));
                    return;
                }
                presenter.resetPwdByPrivateKey(getActivity(),address, key, setPwd, promptInfo);
                break;
        }
    }

    @Override
    public void resetPwdSuccess() {
        ToastUtil.showCenterToast(getResources().getString(R.string.reset_pwd_reset_success));
        ActivityUtil.finishActivity(getActivity());
    }

    @Override
    public void showLoading(String s) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.import_wallet_lin_path) {//选择路径
            showSelectPathDialog();
        }else if (id == R.id.reset_pwd_iv_show_or_hide) {  // 显示或隐藏密码
            if (DoubleClickUtils.isNoDoubleClick()) {
                isShow = !isShow;
                if (isShow) {  // 显示
                    edPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    edPwdConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowOrHide.setImageDrawable(getResources().getDrawable(R.mipmap.eye_icon));
                    edPwd.setSelection(edPwd.getText().toString().trim().length());
                    edPwdConfirm.setSelection(edPwdConfirm.getText().toString().trim().length());
                } else {
                    edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edPwdConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowOrHide.setImageDrawable(getResources().getDrawable(R.mipmap.eye_close_icon));
                    edPwd.setSelection(edPwd.getText().toString().trim().length());
                    edPwdConfirm.setSelection(edPwdConfirm.getText().toString().trim().length());
                }
            }
        } else if (id == R.id.reset_pwd_tv_import) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                dataCheck();
            }
        }
    }

    protected void setScanResult(String scanResult){
        if(edWords != null)edWords.setText(scanResult);
    }
}
