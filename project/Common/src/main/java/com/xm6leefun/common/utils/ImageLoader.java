package com.xm6leefun.common.utils;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/25
 */
public class ImageLoader {


    /**
     * 加载圆形图片
     * @param context
     * @param url
     * @param imageView
     */

    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .transforms(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 加载带圆角矩阵的图片
     * @param context
     * @param url
     * @param radius
     * @param imageView
     */

    public static void loadRadiusImage(Context context, String url, int radius,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(radius)))
                .into(imageView);
    }
    public static void loadRadiusCenterInsideImage(Context context, String url, int radius,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transforms(new CenterInside(), new RoundedCorners(radius)))
                .into(imageView);
    }


    public static void loadRadiusImage(Context context, String url,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(30)))
                .into(imageView);
    }
    public static void loadRadiusCenterInsideImage(Context context, String url,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().transforms(new CenterInside(), new RoundedCorners(30)))
                .into(imageView);
    }

}
