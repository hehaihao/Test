package com.xm6leefun.points_module.send_points;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.xm6leefun.common.app.activity.BaseComActivity;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
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
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.send_points.mvp.SendPointsContract;
import com.xm6leefun.points_module.send_points.mvp.SendPointsPresenter;

import androidx.annotation.Nullable;
import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 11:45
 */
@Route(path = ModuleRouterTable.SEND_POINTS_PAGE)
public class SendPointsActivity extends BaseToolbarPresenterActivity<SendPointsPresenter> implements SendPointsContract.IView {
    @BindView(R2.id.total_points_tv)
    TextView total_points_tv;
    @BindView(R2.id.to_address_et)
    EditText to_address_et;
    @BindView(R2.id.select_points_tv)
    TextView select_points_tv;
    @BindView(R2.id.select_address_iv)
    ImageView select_address_iv;
    @BindView(R2.id.points_et)
    EditText points_et;
    @BindView(R2.id.desc_et)
    EditText desc_et;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_points_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.transfer_title_str);
        topBarTvRight.setVisibility(View.GONE);
        topBarIvRight.setVisibility(View.VISIBLE);
        topBarIvRight.setImageResource(R.mipmap.home_scaner_icon);
        addOnClickListeners(R.id.base_topBar_iv_right);
        setAddressEditTextListener();
    }

    private HomeDataResultBean.FtBean ftBean;
    private String mCurrAddress = "";
    private String toAddress = "";
    private String points = "0";
    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            ftBean = args.getParcelable(ModuleServiceUtils.DATA);
            mCurrAddress = args.getString(ModuleServiceUtils.ADDRESS,"");
            toAddress = args.getString(ModuleServiceUtils.TO_ADDRESS,"");
            points = args.getString(ModuleServiceUtils.POINTS,"0");
            points_et.setText((StrUtils.isEmpty(points) || "0".equals(points)) ? "" : points);
            points_et.setEnabled(StrUtils.isEmpty(points) || "0".equals(points));
            to_address_et.setText(toAddress);
            to_address_et.setSelection(to_address_et.getText().toString().trim().length());
            dealData(ftBean);
        }
    }

    private void dealData(HomeDataResultBean.FtBean ftBean) {
        if(ftBean == null)return;
        total_points_tv.setText(ftBean.getNum()+"");
        select_points_tv.setText(ftBean.getToken_name());
    }

    public void transfer(View view){
        String toAddress = to_address_et.getText().toString().trim();
        String pointsStr = points_et.getText().toString().trim();
        if (!StrUtils.checkCurrAddress(toAddress)) {
            ToastUtil.showCenterToast(getString(R.string.points_wrong_psw_tip));
            return;
        }
        if (mCurrAddress.equals(toAddress)) {
            ToastUtil.showCenterToast(getString(R.string.points_same_address_tip));
            return;
        }
        if (StrUtils.isEmpty(pointsStr)) {
            ToastUtil.showCenterToast(getString(R.string.points_please_input_points_tip));
            return;
        }
        long points = Long.parseLong(pointsStr);
        if (points <= 0) {
            ToastUtil.showCenterToast(getString(R.string.points_please_input_points_tip));
            return;
        }
        this.toAddress  = toAddress;
        if (DoubleClickUtils.isNoDoubleClick()) {
            inputDialog = getBuilder(toAddress,points).build();
            inputDialog.show(mFragmentManager, InputPswDialog.TAG);
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
            SendPointsActivity.this.callbackListener = callbackListener;
            presenter.checkPsw(toAddress, points, content, SendPointsActivity.this.getString(R.string.password_wrong));
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

    /**
     * 选择地址
     * @param view
     */
    public void selectType(View view){
        ModuleServiceUtils.navigateSelectPointsTypePage(this,mCurrAddress);
    }

    @Override
    protected SendPointsPresenter createPresenter() {
        return new SendPointsPresenter(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.base_topBar_iv_right){//扫一扫
            ARouter.getInstance().build(ModuleRouterTable.SCAN_PAGE).navigation(this,ModuleServiceUtils.REQ_QR_CODE);
        }
    }

    @Override
    public void checkPswResult(boolean isPass, String toAddress, long points, String psw, KeysInfo keysInfo) {
        if(callbackListener != null)callbackListener.pswCheckResult(isPass);
        if(isPass){//验证通过
            dismissInputDialog();
            //资产转移
            if(ftBean != null)
                presenter.pointsTran(keysInfo.getPrivateKey(),ftBean,toAddress,points);
        }else{
            ToastUtil.showCenterToast(getString(R.string.password_wrong));
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void pointsTranSuccess() {
        ToastUtil.showCenterToast("提交成功");
        ActivityUtil.finishActivity(this);
    }

    private void setAddressEditTextListener() {
        RxTextView.textChanges(to_address_et)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    String address = charSequence.toString();
                    if(TextUtils.isEmpty(address))return;
//                    RxView.visibility(select_address_tv).call(address.equals(selectAddress));
                });
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
                        String points = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_POINTS_SCAN);
                        to_address_et.setText(result);
                        points_et.setText((StrUtils.isEmpty(points) || "0".equals(points)) ? "" : points);
                        points_et.setEnabled(StrUtils.isEmpty(points) || "0".equals(points));
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
            case ModuleServiceUtils.REQ_SELECT_POINTS://处理选择积分类型结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_SELECT_POINTS){
                    if(data != null){
                        HomeDataResultBean.FtBean ftBean = data.getParcelableExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_POINTS);
                        this.ftBean = ftBean;
                        dealData(ftBean);
                    }
                }
                break;
        }
    }
}
