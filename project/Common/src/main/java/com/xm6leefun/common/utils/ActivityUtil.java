package com.xm6leefun.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.Iterator;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Activity管理类
 */
public class ActivityUtil {
    private static Stack<Activity> activityStack = new Stack<>();
    private static final ActivityUtil.MyActivityLifeCycleCallBack INSTANCE = new ActivityUtil.MyActivityLifeCycleCallBack();

    public static MyActivityLifeCycleCallBack getMyActivityLifeCycleCallBack(){
        return INSTANCE;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static void finishActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            // 使用迭代器安全删除
            for (Iterator<Activity> it = activityStack.iterator(); it.hasNext(); ) {
                Activity activity = it.next();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    public static void startBrowser(String url){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        currentActivity.startActivity(intent);
    }

    public static void startActivity(Class c, boolean isFinish){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity,c);
        currentActivity.startActivity(intent);
        if(isFinish){
            finishActivity(currentActivity);
        }
    }

    public static void startActivity(Class c, boolean isFinish, Bundle bundle){
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(currentActivity,c);
        intent.putExtras(bundle);
        currentActivity.startActivity(intent);
        if(isFinish){
            finishActivity(currentActivity);
        }
    }


    public static void closeAllActivity(){
        while (true){
            Activity activity = getCurrentActivity();
            if(activity == null){
                return;
            }
            finishActivity(activity);
        }
    }

    public static Activity getCurrentActivity(){
        Activity activity = null;
        if(!activityStack.isEmpty()){
            activity = activityStack.peek();
        }
        return activity;
    }

    /**
     * 将fragment添加进activity中的framelayout
     * @param fragmentManager
     * @param fragment
     * @param frameId
     * @param tag
     */
    public static void addFragmentTOActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId,fragment,tag).commitAllowingStateLoss();
    }

    private static class MyActivityLifeCycleCallBack implements Application.ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ActivityUtil.activityStack.remove(activity);
            ActivityUtil.activityStack.push(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityUtil.activityStack.remove(activity);
        }
    }
}
