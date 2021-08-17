package com.xm6leefun.wallet_module.backup.keystore;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.SavePhoto;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;

import butterknife.BindView;


/**
 * @Description:备份keystore
 * @Author: hhh
 * @CreateDate: 2021/3/30 16:05
 */
public class BackupKeystoreQrCodeFragment extends BaseComFragment implements View.OnClickListener {
    public static final String KEYSTORE = "keystore";
    @BindView(R2.id.backup_ks_qr_code_iv_keystore)
    ImageView ivQrCode;
    @BindView(R2.id.cover_view)
    View cover_view;
    @BindView(R2.id.backup_ks_qr_code_tv_show)
    TextView tvShow;

    private String keystore;

    public static BackupKeystoreQrCodeFragment getInstance(String keystore){
        BackupKeystoreQrCodeFragment fragment = new BackupKeystoreQrCodeFragment();
        Bundle args = new Bundle();
        args.putString(KEYSTORE, keystore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_backup_keystore_prikey_qr_code;
    }

    @Override
    protected void initView() {
        ivQrCode.setOnClickListener(this);
        tvShow.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle args = getArguments();
        if(args != null) {
            keystore = args.getString(KEYSTORE,"");
            createQrCodeImg(keystore);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backup_ks_qr_code_tv_show) {  // 显示二维码
            if (DoubleClickUtils.isNoDoubleClick()) {
                cover_view.setVisibility(View.GONE);
                tvShow.setVisibility(View.GONE);
            }
        } else if (id == R.id.backup_ks_qr_code_iv_keystore) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                cover_view.setVisibility(View.VISIBLE);
                tvShow.setVisibility(View.VISIBLE);
            }
        }
    }

    private Bitmap qrBitmap;
    // 创建二维码的方法
    private void createQrCodeImg(String keystore) {
        if(qrBitmap==null)
            qrBitmap = SavePhoto.createQrCodeImg(ConstantValue.QR_CODE_HEAD + ConstantValue.QR_CODE_WALLET_OC_FLAG + ConstantValue.QR_CODE_KEYSTORE_FLAG +keystore, 300);
        if(qrBitmap!=null)ivQrCode.setImageBitmap(qrBitmap);
    }
}
