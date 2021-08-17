package com.xm6leefun.wallet_module.backup.prikey;

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
 * @Description:备份私钥
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class BackupPrivateKeyFragment extends BaseComFragment implements View.OnClickListener {
    public static final String PRIKEY = "privateKey";
    @BindView(R2.id.backup_ks_tv_keystore)
    TextView tvPrivateKey;
    @BindView(R2.id.backup_ks_tv_copy)
    TextView tvCopy;

    public static BackupPrivateKeyFragment getInstance(String privateKey){
        BackupPrivateKeyFragment fragment = new BackupPrivateKeyFragment();
        Bundle args = new Bundle();
        args.putString(PRIKEY, privateKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_backup_keystore_prikey_layout;
    }

    @Override
    protected void initView() {
        tvCopy.setText(getString(R.string.backup_private_key_copy));
        tvCopy.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args != null){
            String privateKey = args.getString(PRIKEY,"");
            tvPrivateKey.setText(privateKey);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backup_ks_tv_copy) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                copyStrings(tvPrivateKey.getText().toString().trim());
            }
        }
    }
}
