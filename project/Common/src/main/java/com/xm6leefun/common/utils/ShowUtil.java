package com.xm6leefun.common.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 加载弹窗管理
 */
public class ShowUtil {
    private static Application mApplicationContext;

    public static void initialize(Application application){
        mApplicationContext = application;
    }

    public static Application getApplication(){
        return mApplicationContext;
    }

    public static void closeSoftKeyboard() {
        InputMethodManager inputManger = (InputMethodManager) ActivityUtil.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManger != null) {
            inputManger.hideSoftInputFromWindow(ActivityUtil.getCurrentActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static int dip2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context,float pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static int[] getScreenSize(Context context){
        try{
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrisc = new DisplayMetrics();
            wm.getDefaultDisplay().getRealMetrics(outMetrisc);
            return new int[]{outMetrisc.widthPixels,outMetrisc.heightPixels};
        }catch (Exception e){
            return new int[]{1080,1920};
        }
    }
}
