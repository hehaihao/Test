package com.xm6leefun.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目：ocToken
 * 文件描述：TextView工具类
 * 作者：ljj
 * 创建时间：2020/9/23
 */
public class TextViewUtil {

    public static void setDINProBoldTypeface(Context context, TextView textView){
        Typeface numFont = Typeface.createFromAsset(context.getAssets(), "fonts/DINPro-Bold.otf");
        textView.setTypeface(numFont);
    }

    public static void setDINMediumTypeface(Context context, TextView textView){
        Typeface numFont = Typeface.createFromAsset(context.getAssets(), "fonts/DIN-Medium.otf");
        textView.setTypeface(numFont);
    }

}
