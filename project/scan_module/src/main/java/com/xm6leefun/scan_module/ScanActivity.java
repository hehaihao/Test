package com.xm6leefun.scan_module;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.xm6leefun.common.app.activity.BaseToolbarComActivity;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.scan_module.dialog.ScanAddressDialog;
import java.io.IOException;
import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * @Description:统一扫码界面
 * @Author: hhh
 * @CreateDate: 2021/3/31 15:51
 */
@Route(path = ModuleRouterTable.SCAN_PAGE)
public class ScanActivity extends BaseToolbarComActivity {
    @BindView(R2.id.rim)
    FrameLayout frameLayout;
    @BindView(R2.id.scan_area)
    ImageView scan_area;
    @BindView(R2.id.iv_line)
    ImageView iv_line;

    private RemoteView remoteView;

    int mScreenWidth;
    int mScreenHeight;
    //扫描视图测试器的宽度和高度均为210dp
    final int SCAN_FRAME_SIZE = 210;

    //声明用于获取扫描工具包返回码
    public static final String SCAN_RESULT = "scanResult";
    public static final String IS_BACK_CODE = "isBackCode";
    private static final int REQUEST_CODE_PHOTO = 0X1113;
    public static final int SCAN_CODE = 0X1114;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1. 获取屏幕密度以计算取景器的矩形
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        //2. 获取屏幕大小
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        int scanFrameSize = (int) (SCAN_FRAME_SIZE * density);
        //3. 计算取景器的矩形，它位于布局的中间。
        //设置扫描区域（可选，Rect可以为空，如果未指定任何设置，它将位于布局的中间）
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;

        //初始化RemoteView实例，并为扫描结果设置回调
        remoteView = new RemoteView.Builder().setContext(this).setBoundingBox(rect).setFormat(HmsScan.ALL_SCAN_TYPE).build();
        // 订阅扫描结果回调事件
        //处理扫描结果
        remoteView.setOnResultCallback(this::handleReulst);
        // 将自定义视图加载到activity
        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        frameLayout.addView(remoteView, params);

        animateLine();
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.scan_api_title);
    }

    private boolean isFromIndex = false;//首页
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            isFromIndex = extras.getBoolean(ModuleServiceUtils.FROM_INDEX,false);
        }
    }

    /**
     * 关闭当前界面
     * @param view
     */
    public void close(View view){
        ActivityUtil.finishActivity(this);
    }

    /**
     * 处理扫描结果
     * @param result
     */
    protected void handleReulst(HmsScan[] result){
        if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            String scanResult = result[0].getOriginalValue();
            if (!StrUtils.isEmpty(scanResult)) {
                if (scanResult.contains(ConstantValue.QR_CODE_HEAD+ConstantValue.QR_CODE_WALLET_OC_FLAG)) {
                    scanResult = scanResult.replace(ConstantValue.QR_CODE_HEAD+ConstantValue.QR_CODE_WALLET_OC_FLAG, "");
                    if (!StrUtils.isEmpty(scanResult)) {
                        if (scanResult.contains("value=")) {// 收款人地址
                            scanResult = scanResult.replace("value=","");
                            String[] split = scanResult.split("_");
                            String addr = split[1];
                            String points = split[0];
                            showAddressDialog(addr,points);
                        }else{//私钥和keystore处理
                            dealPriKeyAndKeyStore(scanResult);
                        }
                    } else {
                        ToastUtil.showCenterToast(getString(R.string.qr_code_dialog_tip));
                    }
                } else {
                    ToastUtil.showCenterToast(getString(R.string.qr_code_dialog_tip));
                }
            } else {
                ToastUtil.showCenterToast(getString(R.string.show_scan_result_null_result));
            }
        }else{
            ToastUtil.showCenterToast(getString(R.string.scan_api_fail));
        }
    }

    /**
     * 选择图片配置
     */
    protected void setPictureScanOperation() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ScanActivity.this.startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    /**
     * 调用remoteView活动的生命周期管理方法
     */
    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }

    /**
     * 处理相册的返回结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PHOTO) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(ScanActivity.this, bitmap, new HmsScanAnalyzerOptions.Creator().setPhotoMode(true).create());
                handleReulst(hmsScans);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 手电筒是否开启状态
    public void rightClick(View view) {
        remoteView.switchLight();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animateLine();
    }

    private void animateLine() {
        // 模拟的mPreviewView的左右上下坐标坐标
        int left = scan_area.getLeft() / 2;
        int top = scan_area.getTop() + 30;
        int bottom = scan_area.getBottom() - 50;

        TranslateAnimation tAnim = new TranslateAnimation(0, 0, top, bottom);//设置视图上下移动的位置
        tAnim .setDuration(1800);
        tAnim .setRepeatCount(Animation.INFINITE);
        tAnim .setRepeatMode(Animation.REVERSE);
        iv_line.setAnimation(tAnim);
        tAnim.startNow();
    }

    private ScanAddressDialog scanAddressDialog = null;
    private void showAddressDialog(String address,String points){
        if(!isFromIndex){//非首页路由，直接将地址返回
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN, address);
            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_POINTS_SCAN, points);
            setResult(ModuleServiceUtils.RESULT_CODE_QR_SCAN, resultIntent);
            ActivityUtil.finishActivity(ScanActivity.this);
            return;
        }
        //首页路由，弹窗处理
        remoteView.pauseContinuouslyScan();
        if(scanAddressDialog == null) {
            scanAddressDialog = new ScanAddressDialog.Builder()
                    .setAddress(address)
                    .setClickListener(new ScanAddressDialog.ClickListener() {
                        @Override
                        public void saveAddress(String address) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN, address);
                            resultIntent.putExtra(ModuleServiceUtils.ADDRESS_TYPE,1);//1表示存储地址
                            setResult(ModuleServiceUtils.RESULT_CODE_QR_SCAN, resultIntent);
                            ActivityUtil.finishActivity(ScanActivity.this);
                        }

                        @Override
                        public void transfer(String address) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN, address);
                            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_POINTS_SCAN, points);
                            resultIntent.putExtra(ModuleServiceUtils.ADDRESS_TYPE,2);//2表示转账
                            setResult(ModuleServiceUtils.RESULT_CODE_QR_SCAN, resultIntent);
                            ActivityUtil.finishActivity(ScanActivity.this);
                        }

                        @Override
                        public void onDissmiss() {
                            //重置扫描
                            remoteView.resumeContinuouslyScan();
                        }
                    }).build();
        }
        if(!scanAddressDialog.isAdded())
            scanAddressDialog.show(mFragmentManager,ScanAddressDialog.TAG);
    }

    /**
     * 处理私钥和keystore
     * @param scanResult
     */
    private void dealPriKeyAndKeyStore(String scanResult) {
        if(isFromIndex){//首页暂不处理私钥和keystore，后续可以引导导入钱包
            return;
        }
        if(scanResult.contains(ConstantValue.QR_CODE_PRIVATE_KEY_FLAG)){
            String[] split = scanResult.split(ConstantValue.QR_CODE_PRIVATE_KEY_FLAG);
            String privateKey = split[1];
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN, privateKey);
            setResult(ModuleServiceUtils.RESULT_CODE_QR_SCAN, resultIntent);
            ActivityUtil.finishActivity(ScanActivity.this);
        }else if(scanResult.contains(ConstantValue.QR_CODE_KEYSTORE_FLAG)){
            String[] split = scanResult.split(ConstantValue.QR_CODE_KEYSTORE_FLAG);
            String keystore = split[1];
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_QR_SCAN, keystore);
            setResult(ModuleServiceUtils.RESULT_CODE_QR_SCAN, resultIntent);
            ActivityUtil.finishActivity(ScanActivity.this);
        }
    }
}
