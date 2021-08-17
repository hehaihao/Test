package com.xm6leefun.physical_module.photo_viewer;

import android.os.Bundle;
import android.widget.TextView;

import com.xm6leefun.common.app.activity.BaseComActivity;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;
import com.xm6leefun.physical_module.photo_viewer.adapter.ViewPhotosAdapter;
import com.xm6leefun.physical_module.photo_viewer.viewpager.PhotoViewPager;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * @Description: 预览图片
 * @Author: hhh
 * @CreateDate: 2021/4/15 15:05
 */
public class PhotosActivity extends BaseComActivity {
    public static final String DATA = "data";
    public static final String POSITION = "position";
    @BindView(R2.id.photoViewPager)
    PhotoViewPager photoViewPager;
    @BindView(R2.id.image_count_tv)
    TextView image_count_tv;

    private ViewPhotosAdapter adapter;
    private ArrayList<String> urls;
    private int currentPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photos_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightModeFull(this);
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            urls =  extras.getStringArrayList(DATA);
            currentPosition =  extras.getInt(POSITION,0);
            adapter = new ViewPhotosAdapter(urls, this);
            photoViewPager.setAdapter(adapter);
            photoViewPager.setCurrentItem(currentPosition, false);
            image_count_tv.setText(currentPosition + 1 + "/" + urls.size());
            photoViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentPosition = position;
                    image_count_tv.setText(currentPosition + 1 + "/" + urls.size());
                }
            });
        }
    }
}
