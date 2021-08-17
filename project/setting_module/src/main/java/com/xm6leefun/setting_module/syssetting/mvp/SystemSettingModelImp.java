package com.xm6leefun.setting_module.syssetting.mvp;

import android.app.Activity;
import com.hailong.biometricprompt.fingerprint.FingerprintCallback;
import com.hailong.biometricprompt.fingerprint.FingerprintVerifyManager;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.setting_module.api.SystemSettingsApiService;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class SystemSettingModelImp implements SystemSettingContract.IModel{

    private SystemSettingsApiService apiService;

    public SystemSettingModelImp(SystemSettingsApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<?> getTouchIDStatus() {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            emitter.onNext(SharePreferenceUtil.getBoolean(AppAllKey.TOUCH_ID_STATUS));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<?> OpenTouchID(Activity activity) {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

            FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(activity);
            builder.callback(new FingerprintCallback() {
                @Override
                public void onHwUnavailable() {
                    SharePreferenceUtil.setBoolean(AppAllKey.TOUCH_ID_STATUS,false);
                    emitter.onNext(false);
                    emitter.onComplete();
                }

                @Override
                public void onNoneEnrolled() {
                    emitter.onNext(false);
                    emitter.onComplete();
                }

                @Override
                public void onSucceeded() {
                    emitter.onNext(true);
                    emitter.onComplete();
                }

                @Override
                public void onFailed() {
                    emitter.onNext(false);
                    emitter.onComplete();
                }

                @Override
                public void onUsepwd() {
                    emitter.onNext(false);
                    emitter.onComplete();
                }

                @Override
                public void onCancel() {
                    emitter.onNext(false);
                    emitter.onComplete();
                }
            });
            builder.build();
        });
    }

    @Override
    public Observable<?> CloseTouchID() {
        return Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            emitter.onNext(false);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<?> getAppVersion() {
        return apiService.getAppVersionInfo("android");
    }
}
