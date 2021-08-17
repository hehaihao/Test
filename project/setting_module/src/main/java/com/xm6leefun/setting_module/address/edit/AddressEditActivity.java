package com.xm6leefun.setting_module.address.edit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.AddressBook;
import com.xm6leefun.common.dialog.HintDialog;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.R2;
import com.xm6leefun.setting_module.address.edit.mvp.AddressEditContract;
import com.xm6leefun.setting_module.address.edit.mvp.AddressEditPresenter;
import com.xm6leefun.setting_module.address.edit.mvp.RefreshAddressBusBean;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/25 11:12
 */
@Route(path = ModuleRouterTable.EDIT_ADDRESS_PAGE)
public class AddressEditActivity extends BaseToolbarPresenterActivity<AddressEditPresenter> implements AddressEditContract.IView {
    @BindView(R2.id.address_et)
    EditText address_et;
    @BindView(R2.id.name_et)
    EditText name_et;
    @BindView(R2.id.desc_et)
    EditText desc_et;
    @BindView(R2.id.save)
    TextView save;

    private AddressBook addressListBean;
    private String address;
    private boolean isAdd = false;

    @Override
    protected AddressEditPresenter createPresenter() {
        return new AddressEditPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address_layout;
    }

    @Override
    protected void initView() {
        topBarIvRight.setImageResource(R.mipmap.home_scaner_icon);
        addOnClickListeners(R.id.base_topBar_iv_right,R.id.base_topBar_tv_right,R.id.save);
    }

    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null) {
            addressListBean = args.getParcelable(ModuleServiceUtils.DATA);
            isAdd = args.getBoolean(ModuleServiceUtils.IS_ADD,false);
            address = args.getString(ModuleServiceUtils.ADDRESS,"");
        }
        if(addressListBean != null){
            address_et.setText(addressListBean.getAddress());
            address_et.setSelection(address_et.getText().toString().trim().length());
            name_et.setText(TextUtils.isEmpty(addressListBean.getPerson()) ? "" : addressListBean.getPerson());
            desc_et.setText(TextUtils.isEmpty(addressListBean.getRemark()) ? "" : addressListBean.getRemark());
        }
        if(!StrUtils.isEmpty(address)){
            address_et.setText(address);
            address_et.setSelection(address_et.getText().toString().trim().length());
        }
        if(isAdd){
            topBarTvTitle.setText(R.string.address_add_title_str);
            topBarTvRight.setText(R.string.address_save);
            save.setVisibility(View.GONE);
        }else{
            topBarTvTitle.setText(R.string.address_edit_title_str);
            topBarTvRight.setText(R.string.address_list_delete);
            save.setVisibility(View.VISIBLE);
        }
    }

    private HintDialog deleteDialog;
    private void showDeleteDialog() {
        if(deleteDialog == null)
            deleteDialog = new HintDialog.Builder()
                    .setTitle(getResources().getString(R.string.address_delete_title_str))
                    .setContent(getResources().getString(R.string.address_delete_content_str))
                    .setClickListener(() -> presenter.deleteAddress(address_et.getText().toString().trim()))
                    .build();
        if(!deleteDialog.isAdded())
            deleteDialog.show(mFragmentManager, HintDialog.TAG);
    }

    @Override
    public void deleteAddressSuccess() {
        ToastUtil.showCenterToast("删除成功");
        EventBus.getDefault().post(new RefreshAddressBusBean());
        ActivityUtil.finishActivity(this);
    }

    @Override
    public void editAddressSuccess() {
        EventBus.getDefault().post(new RefreshAddressBusBean());
        ActivityUtil.finishActivity(this);
    }

    @Override
    public void addAddressSuccess() {
        EventBus.getDefault().post(new RefreshAddressBusBean());
        ActivityUtil.finishActivity(this);
    }

    @Override
    public void onLoadFail(String errorMessage) {
        ToastUtil.showCenterToast(errorMessage);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.base_topBar_tv_right) {
            if(isAdd){
                addAddress();
            }else {
                showDeleteDialog();
            }
        }else if (id == R.id.base_topBar_iv_right) {//扫一扫
            checkPermissionRequest(this, Manifest.permission.CAMERA, isAllow -> {
                if (isAllow) {
                    ARouter.getInstance()
                            .build(ModuleRouterTable.SCAN_PAGE)
                            .navigation(this,ModuleServiceUtils.REQ_QR_CODE);
                } else {
                    ToastUtil.showCenterToast(getResources().getString(R.string.scan_to_open_camera_permission));
                }
            });
        }else if (id == R.id.save) {
            editaddress();
        }
    }

    /**
     * 提交编辑
     */
    private void editaddress() {
        String address = address_et.getText().toString().trim();
        String name = name_et.getText().toString().trim();
        String desc = desc_et.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showCenterToast(getString(R.string.address_null_tip_str));
            return;
        }
        if (!StrUtils.checkCurrAddress(address)) {
            ToastUtil.showCenterToast(getString(R.string.address_err_tip_str));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showCenterToast(getString(R.string.address_name_null_tip_str));
            return;
        }
        //修改地址
        presenter.editAddress(addressListBean.getAddress(), address, name, desc);
    }

    /**
     * 提交添加地址
     */
    private void addAddress() {
        String address = address_et.getText().toString().trim();
        String name = name_et.getText().toString().trim();
        String desc = desc_et.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showCenterToast(getString(R.string.address_null_tip_str));
            return;
        }
        if (!StrUtils.checkCurrAddress(address)) {
            ToastUtil.showCenterToast(getString(R.string.address_err_tip_str));
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showCenterToast(getString(R.string.address_name_null_tip_str));
            return;
        }
        //添加地址
        presenter.addAddress(address, name, desc,getString(R.string.address_exists_str));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_QR_CODE://处理扫码结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_QR_SCAN){
                    if(data != null){
                        String result = data.getStringExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN);
                        address_et.setText(result);
                    }
                }
                break;
        }
    }
}
