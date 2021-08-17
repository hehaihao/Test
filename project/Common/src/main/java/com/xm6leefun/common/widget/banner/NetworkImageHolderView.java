package com.xm6leefun.common.widget.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.xm6leefun.common.utils.ImageLoader;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @Description: 设置广告轮播图片 加载网络图片
 * @Author: hhh
 * @CreateDate: 2021/4/15 14:32
 */
public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);// CENTER_CROP
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, String data) {
        ImageLoader.loadRadiusImage(context,data,imageView);
    }
}
