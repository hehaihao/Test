package com.xm6leefun.points_module.receive_code;

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
import com.xm6leefun.common.dialog.TipsDialog;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.SavePhoto;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.common.dialog.CollectionCodeDialog;
import com.xm6leefun.common.dialog.InputMoneyDialog;
import com.xm6leefun.points_module.receive_code.mvp.ReceiveQrCodeContract;
import com.xm6leefun.points_module.receive_code.mvp.ReceiveQrCodePresenter;

import butterknife.BindView;


/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 11:45
 */
@Route(path = ModuleRouterTable.RECEIVE_POINTS_PAGE)
public class ReceiptQrCodeActivity extends BaseToolbarPresenterActivity<ReceiveQrCodePresenter> implements ReceiveQrCodeContract.IView {

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
    @BindView(R2.id.receipt_qrcode_tv_set_num)
    TextView tvSetNum;

    private boolean isClear = false;
    private String pointsName = "积分";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt_qrcode_layout;
    }

    @Override
    protected void initView() {
        addOnClickListeners(R.id.receipt_qrcode_tv_save
                , R.id.receipt_qrcode_tv_set_num
                , R.id.receipt_qrcode_lin_copy
        );
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                pointsName = bundle.getString(ModuleServiceUtils.POINTS_NAME,getString(R.string.points_str));
                topBarTvTitle.setText(R.string.receipt_qrcode_title);
                tvSubTitle.setText(getResources().getString(R.string.receipt_qrcode_hint,pointsName));
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
        } else if (id == R.id.receipt_qrcode_tv_set_num) {  // 指定金额
            if (DoubleClickUtils.isNoDoubleClick()) {
                //弹框 指定金额
                if (isClear) {
                    showInputMoneyDialog();
                } else {
                    //获取当前选中的地址
                    presenter.getCurrWallet();
                }
            }
        } else if (id == R.id.receipt_qrcode_lin_copy) {  // 复制地址
            if (DoubleClickUtils.isNoDoubleClick()) {
                copyStrings(tvAddress.getText().toString().trim());
            }
        }

    }

    /**
     * 显示输入金额
     */
    private void showInputMoneyDialog() {
        InputMoneyDialog inputMoneyDialog = new InputMoneyDialog.Builder()
                .setMoney(setMoney)
                .setClickListener(money -> {
                    if (!StrUtils.isEmpty(money)) {
                        dealMoney(money);
                        tvSetNum.setText("清除积分");
                        isClear = !isClear;
                    }
                }).build();
        inputMoneyDialog.show(mFragmentManager,InputMoneyDialog.TAG);
    }

    private void showTipsDialog(String msg) {
        TipsDialog tipsDialog = new TipsDialog.Builder()
                .setContent(msg)
                .build();
        tipsDialog.show(mFragmentManager, msg);
    }

    private String setMoney ="0";
    private void dealMoney(String money) {
        setMoney = money;
        tvSubTitle.setText(getResources().getString(R.string.receipt_qrcode_hint,money+ pointsName));
        code = ConstantValue.QR_CODE_HEAD + ConstantValue.QR_CODE_WALLET_OC_FLAG +ConstantValue.QR_CODE_VALUE_FLAG + money + "_" + address;
        Bitmap bitmap = SavePhoto.createQrCodeImg(code, 220);
        if(bitmap!=null)ivQrCode.setImageBitmap(bitmap);
    }

    /**
     * 显示收款码分享弹框
     */
    private void showQRDialog() {
        checkPermissionsRequest(this, isAllow -> {
            if(isAllow) {
                CollectionCodeDialog collectionCodeDialog = new CollectionCodeDialog.Builder()
                        .setName(getString(R.string.app_name))
                        .setTitle(getString(R.string.receive_points_code_title_str))
                        .setContent(getResources().getString(R.string.receipt_qrcode_hint,((setMoney.equals("0")) ? "" : setMoney) + "积分"))
                        .setCode(code)
                        .setAddress(walletMain.getAddress())
                        .setClickListener(imagePath -> toShare(imagePath)).build();
                collectionCodeDialog.show(mFragmentManager, CollectionCodeDialog.TAG);
            } else {
                ToastUtil.showCenterToast(getResources().getString(R.string.scan_to_open_read_write_permission));
            }
        },Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected ReceiveQrCodePresenter createPresenter() {
        return new ReceiveQrCodePresenter(this);
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
        tvSetNum.setText(getString(R.string.receipt_qrcode_set_num));
        tvSubTitle.setText(getResources().getString(R.string.receipt_qrcode_hint,pointsName));
        isClear = !isClear;
        setMoney = "0";
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
