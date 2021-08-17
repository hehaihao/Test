package com.xm6leefun.wallet_module.backup_tips;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup.keystore.BackupKeystoreActivity;
import com.xm6leefun.wallet_module.backup.prikey.BackupPrivateKeyActivity;
import com.xm6leefun.wallet_module.backup_tips.dialog.BackupTipDialog;
import com.xm6leefun.wallet_module.backup_tips.mvp.BackupTipsContract;
import com.xm6leefun.wallet_module.backup_tips.mvp.BackupTipsPresenter;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.wallet_module.words_make.WordsMakeActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * @Description: 备份提示页面
 * @Author: hhh
 * @CreateDate: 2021/3/26 14:32
 */
public class BackupTipsActivity extends BaseToolbarPresenterActivity<BackupTipsPresenter> implements BackupTipsContract.IView {
    public static final String CUS_DATA = "cus_data";

    @BindView(R2.id.backup_tv_hint)
    TextView backup_tv_hint;
    @BindView(R2.id.backup_lin_hint_words)
    LinearLayout linHintWords;
    @BindView(R2.id.backup_lin_hint_keystore)
    LinearLayout linHintKeystore;
    @BindView(R2.id.backup_lin_hint_private_key)
    LinearLayout linHintPrivateKey;
    @BindView(R2.id.backup_tv_backup_late)
    TextView tvBackupLate;
    private CusCreateWalletBean cusCreateWallet;

    @Override
    protected BackupTipsPresenter createPresenter() {
        return new BackupTipsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_backup_words_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.backup_hint_title);
        addOnClickListeners(R.id.backup_tv_next, R.id.backup_tv_backup_late);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            cusCreateWallet = bundle.getParcelable(CUS_DATA);
            dealWalletData(cusCreateWallet);
        }
    }

    /**
     * 处理数据
     * @param cusCreateWallet
     */
    private void dealWalletData(CusCreateWalletBean cusCreateWallet) {
        if (cusCreateWallet != null) {
            switch (cusCreateWallet.getType()){
                case 0:  // 助记词
                    backup_tv_hint.setText(R.string.backup_hint_title_sub_words);
                    linHintWords.setVisibility(View.VISIBLE);
                    linHintKeystore.setVisibility(View.GONE);
                    linHintPrivateKey.setVisibility(View.GONE);
                    break;
                case 2:  // keystore
                    backup_tv_hint.setText(R.string.backup_hint_title_sub_keystore);
                    linHintWords.setVisibility(View.GONE);
                    linHintKeystore.setVisibility(View.VISIBLE);
                    linHintPrivateKey.setVisibility(View.GONE);
                    break;
                case 1:  // 私钥
                    backup_tv_hint.setText(R.string.backup_hint_title_sub_private_key);
                    linHintWords.setVisibility(View.GONE);
                    linHintKeystore.setVisibility(View.GONE);
                    linHintPrivateKey.setVisibility(View.VISIBLE);
                    break;
            }
            tvBackupLate.setVisibility(cusCreateWallet.getWhere() != 2 ? View.VISIBLE : View.GONE);  // 0 创建，1 导入，2 备份
            topBarIvBack.setVisibility(cusCreateWallet.getWhere() != 2 ? View.INVISIBLE : View.VISIBLE);  // 0 创建，1 导入，2 备份
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.backup_tv_next) {  // 下一步
            if (DoubleClickUtils.isNoDoubleClick()) {
                // todo 弹框后点击按钮进行跳转
                Bundle bundle = new Bundle();
                bundle.putParcelable(CUS_DATA, cusCreateWallet);
                showBackupTipDialog(bundle);
            }
        } else if (id == R.id.backup_tv_backup_late) {  // 稍后备份
            if (DoubleClickUtils.isNoDoubleClick()) {
                // todo 保存钱包到数据库，但是不进行助记词备份模块，直接跳转至首页
                presenter.saveWallet(cusCreateWallet);
            }
        }
    }

    private BackupTipDialog backupTipDialog;

    /**
     * 显示提示弹窗
     */
    private void showBackupTipDialog(Bundle bundle) {
        if(backupTipDialog == null) {
            backupTipDialog = new BackupTipDialog.Builder()
                    .setClickListener(() -> {
                        switch (cusCreateWallet.getType()) {  // 0 助记词，1 私钥，2 keystore
                            case 0:  // 助记词
                                ActivityUtil.startActivity(WordsMakeActivity.class,false,bundle);
                                break;
                            case 1:  // 私钥
                                if (cusCreateWallet.getWhere() == 2) {  //  2 备份
                                    ActivityUtil.startActivity(BackupPrivateKeyActivity.class,false,bundle);
                                }
                                break;
                            case 2:  // keystore
                                if (cusCreateWallet.getWhere() == 2) {  //  2 备份
                                    ActivityUtil.startActivity(BackupKeystoreActivity.class,false,bundle);
                                }
                                break;
                        }
                    })
                    .build();
        }
        if(!backupTipDialog.isAdded()) backupTipDialog.show(mFragmentManager,BackupTipDialog.TAG);
    }

    /**
     * 修改返回键跳转至首页
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (cusCreateWallet.getWhere() != 2) {//备份
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                EventBus.getDefault().post(new RefreshWalletDataBusBean());//刷新首页
                ModuleServiceUtils.navigateHomePage();
                ActivityUtil.finishActivity(this);
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void saveWalletSuccess(CusCreateWalletBean cusCreateWalletBean) {
        EventBus.getDefault().post(new RefreshWalletDataBusBean());//刷新首页
        ModuleServiceUtils.navigateHomePage();
        ActivityUtil.finishActivity(this);
    }

    @Override
    public void saveWalletFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }
}
