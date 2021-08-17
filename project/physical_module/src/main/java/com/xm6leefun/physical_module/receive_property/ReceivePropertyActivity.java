package com.xm6leefun.physical_module.receive_property;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.dialog.CollectionCodeDialog;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.SavePhoto;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;

import com.xm6leefun.physical_module.receive_property.mvp.ReceivePropertyContract;
import com.xm6leefun.physical_module.receive_property.mvp.ReceivePropertyPresenter;

import butterknife.BindView;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/22
 */
@Route(path = ModuleRouterTable.RECEIVE_PROPERTY_PAGE)
public class ReceivePropertyActivity extends BaseToolbarPresenterActivity<ReceivePropertyPresenter>  implements ReceivePropertyContract.IView {
    @BindView(R2.id.qr_root_view)
    LinearLayout qr_root_view;
    @BindView(R2.id.receipt_qrcode_tv_sub_title)
    TextView tvSubTitle;
    @BindView(R2.id.receipt_qrcode_iv_qrcode)
    ImageView ivQrCode;
    @BindView(R2.id.receipt_qrcode_tv_save)
    TextView tvSave;
    @BindView(R2.id.receipt_qrcode_tv_address)
    TextView tvAddress;

    private boolean isClear = false;



    @Override
    protected ReceivePropertyPresenter createPresenter() {
        return new ReceivePropertyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive_property;
    }

    @Override
    protected void initView() {
        addOnClickListeners(R.id.receipt_qrcode_tv_save
                , R.id.receipt_qrcode_lin_copy
        );
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                topBarTvTitle.setText(R.string.receipt_property_title);
                tvSubTitle.setText(getResources().getString(R.string.receipt_physical_qrcode_hint, "资产"));
            }
        }

        //获取当前选中的地址
        presenter.getCurrWallet();

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.receipt_qrcode_tv_save) {  // 保存二维码
            if (DoubleClickUtils.isNoDoubleClick()) {
                //保存二维码
                showQRDialog();
            }
        } else if (id == R.id.receipt_qrcode_lin_copy) {  // 复制地址
            if (DoubleClickUtils.isNoDoubleClick()) {
                copyStrings(tvAddress.getText().toString().trim());
            }
        }

    }

    /**
     * 显示收款码分享弹框
     */
    private void showQRDialog() {
        checkPermissionsRequest(this, isAllow -> {
            if(isAllow) {
                CollectionCodeDialog collectionCodeDialog = new CollectionCodeDialog.Builder()
                        .setName(getString(R.string.app_name))
                        .setTitle(getString(R.string.receive_physical_code_title_str))
                        .setContent(getResources().getString(R.string.receipt_physical_qrcode_hint,"资产"))
                        .setCode(code)
                        .setAddress(walletMain.getAddress())
                        .setClickListener(imagePath -> toShare(imagePath)).build();
                collectionCodeDialog.show(mFragmentManager, CollectionCodeDialog.TAG);
            } else {
                ToastUtil.showCenterToast(getResources().getString(R.string.scan_to_open_read_write_permission));
            }
        },Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }



    private Wallet_Main walletMain;
    private String code = "";
    private String address = "";

    /**
     * 分享
     */
    private void toShare(String imagePath) {

    }

    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        qr_root_view.setVisibility(View.VISIBLE);
        this.walletMain = walletMain;
        address = walletMain.getAddress();
        tvAddress.setText(address);
        code = ConstantValue.QR_CODE_HEAD + ConstantValue.QR_CODE_WALLET_OC_FLAG +ConstantValue.QR_CODE_VALUE_FLAG + "0_" + address;
        Bitmap bitmap = SavePhoto.createQrCodeImg(code, 220);
        if(bitmap!=null)ivQrCode.setImageBitmap(bitmap);
        tvSubTitle.setText(getResources().getString(R.string.receipt_physical_qrcode_hint,"资产"));
        isClear = !isClear;
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
