package com.xm6leefun.wallet_module.backup.keystore;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import butterknife.BindView;


/**
 * @Description:备份keystore
 * @Author: hhh
 * @CreateDate: 2021/3/30 16:05
 */
public class BackupKeystoreFragment extends BaseComFragment implements View.OnClickListener {
    public static final String KEYSTORE = "keystore";
    @BindView(R2.id.backup_ks_tv_keystore)
    TextView tvKeystore;
    @BindView(R2.id.backup_ks_tv_copy)
    TextView tvCopy;

    public static BackupKeystoreFragment getInstance(String keystore){
        BackupKeystoreFragment fragment = new BackupKeystoreFragment();
        Bundle args = new Bundle();
        args.putString(KEYSTORE, keystore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_backup_keystore_prikey_layout;
    }

    @Override
    protected void initView() {
        tvCopy.setText(getString(R.string.backup_keystore_copy));
        tvCopy.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args != null){
            String keystore = args.getString(KEYSTORE,"");
            tvKeystore.setText(keystore);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backup_ks_tv_copy) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                copyStrings(tvKeystore.getText().toString().trim());
            }
        }
    }

    public void setKeystore(String keystore) {
        dismiss();
        tvKeystore.setText(keystore);
    }

}
