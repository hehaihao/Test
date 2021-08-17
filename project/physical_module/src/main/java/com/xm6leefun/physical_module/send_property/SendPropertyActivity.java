package com.xm6leefun.physical_module.send_property;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.dialog.InputPswDialog;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.bean.SelectAddressBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;
import com.xm6leefun.physical_module.send_property.mvp.SendPropertyContract;
import com.xm6leefun.physical_module.send_property.mvp.SendPropertyPresenter;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.android.schedulers.AndroidSchedulers;

@Route(path = ModuleRouterTable.SEND_PROPERTY_PAGE)
public class SendPropertyActivity extends BaseToolbarPresenterActivity<SendPropertyPresenter> implements SendPropertyContract.IView {

    @BindView(R2.id.to_address_et)
    EditText to_address_et;
    @BindView(R2.id.select_address_iv)
    ImageView select_address_iv;
    @BindView(R2.id.points_et)
    EditText points_et;
    @BindView(R2.id.background_iv)
    ImageView background_iv;

    @Override
    protected SendPropertyPresenter createPresenter() {
        return new SendPropertyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_property;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.send_property_title);
        topBarTvRight.setVisibility(View.GONE);
        topBarIvRight.setVisibility(View.VISIBLE);
        topBarIvRight.setImageResource(R.mipmap.home_scaner_icon);
        addOnClickListeners(R.id.base_topBar_iv_right);
    }

    private String mCurrAddress = "";
    private String toAddress = "";
    private String nftImg="";
    private String txId="";
    private String contract_address="";


    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            mCurrAddress = args.getString(ModuleServiceUtils.ADDRESS,"");
            toAddress = args.getString(ModuleServiceUtils.TO_ADDRESS,"");
            nftImg = args.getString(ModuleServiceUtils.NFT_IMG);
            txId = args.getString(ModuleServiceUtils.TX_ID);
            contract_address = args.getString(ModuleServiceUtils.CONTRACT_ADDRESS);
            to_address_et.setText(toAddress);
            to_address_et.setSelection(to_address_et.getText().toString().trim().length());
            ImageLoader.loadRadiusCenterInsideImage(this,nftImg,18,background_iv);
        }
    }

    /**
     * 开始转移资产
     * @param view
     */
    public void transfer(View view){
        String toAddress = to_address_et.getText().toString().trim();
        String pointsStr = points_et.getText().toString().trim();
        if (!StrUtils.checkCurrAddress(toAddress)) {
            ToastUtil.showCenterToast(getString(R.string.wrong_psw_tip));
            return;
        }
        if (mCurrAddress.equals(toAddress)) {
            ToastUtil.showCenterToast(getString(R.string.same_address_tip));
            return;
        }
        if (StrUtils.isEmpty(pointsStr)) {
            pointsStr = "0";
        }
        long points = Long.parseLong(pointsStr);
        this.toAddress  = toAddress;
        if (DoubleClickUtils.isNoDoubleClick()) {
            inputDialog = getBuilder(toAddress,points).build();
            inputDialog.show(mFragmentManager,InputPswDialog.TAG);
        }

    }

    private InputPswDialog inputDialog;
    /**
     * 取消弹窗
     */
    private void dismissInputDialog() {
        if(inputDialog != null && inputDialog.isAdded())
            inputDialog.dismiss();
    }

    private InputPswDialog.CallbackListener callbackListener;
    private InputPswDialog.Builder builder;
    private InputPswDialog.Builder getBuilder(String toAddress,long points) {
        builder = new InputPswDialog.Builder();
        builder.setClickListener((content, callbackListener) -> {
            SendPropertyActivity.this.callbackListener = callbackListener;
            presenter.checkPsw(txId, contract_address, toAddress, points, content, SendPropertyActivity.this.getString(R.string.password_wrong));
        });
        return builder;
    }

    /**
     * 选择地址
     * @param view
     */
    public void selectAddress(View view){
        ARouter.getInstance()
                .build(ModuleRouterTable.ADDRESS_LIST_PAGE)
                .withBoolean(ModuleServiceUtils.IS_SELECT_ADDRESS,true)
                .navigation(this,ModuleServiceUtils.REQ_SELECT_ADDRESS);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.base_topBar_iv_right){//扫一扫
            ARouter.getInstance().build(ModuleRouterTable.SCAN_PAGE).navigation(this,ModuleServiceUtils.REQ_QR_CODE);
        }
    }

    /**
     * 校验密码结果
     * @param isPass
     * @param toAddress
     * @param psw
     * @param keysInfo
     */
    @Override
    public void checkPswResult(boolean isPass,String txId, String contract_address,String toAddress, long points,String psw, KeysInfo keysInfo) {
        if(callbackListener != null)callbackListener.pswCheckResult(isPass);
        if(isPass){//验证通过
            dismissInputDialog();
            //资产转移
            presenter.physicalTran(keysInfo.getPrivateKey(),txId,contract_address,toAddress,points);
        }else{
            ToastUtil.showCenterToast(getString(R.string.physical_tran_psw_err));
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void physicalTranSuccess() {
        ToastUtil.showCenterToast("转移提交成功");
        ActivityUtil.finishActivity(this);
    }

    private String selectAddress = "";
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_QR_CODE://处理扫码结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_QR_SCAN){
                    if(data != null){
                        String result = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN);
                        to_address_et.setText(result);
                    }
                }
                break;
            case ModuleServiceUtils.REQ_SELECT_ADDRESS://处理选择地址结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_SELECT_ADDRESS){
                    if(data != null){
                        SelectAddressBean addressBean = data.getParcelableExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_ADDRESS);
                        selectAddress = addressBean.getAddress();
                        to_address_et.setText(selectAddress);
                        to_address_et.setSelection(selectAddress.trim().length());
                    }
                }
                break;
        }
    }
}
