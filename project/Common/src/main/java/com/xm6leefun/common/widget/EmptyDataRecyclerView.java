package com.xm6leefun.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.R;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (C), 2019-2020, 成都链智慧科技有限公司
 * FileName: ZFEmptyView
 * Author: ZhaoFeng
 * Date: 2020/9/14 14:52
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class EmptyDataRecyclerView extends RelativeLayout {
    private ImageView iconView;
    private TextView titleView;
    private View emptyView;
    private RecyclerView mRecyclerView;

    public EmptyDataRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public EmptyDataRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyDataRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public EmptyDataRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    private void initView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_empty_recyclerview, this, true);
        iconView = v.findViewById(R.id.iv_icon);
        titleView = (TextView) v.findViewById(R.id.tv_content);
        emptyView = (View) v.findViewById(R.id.emptyView);
        if (v.findViewById(R.id.zf_empty_RecyclerView) instanceof RecyclerView)
            mRecyclerView = (RecyclerView) findViewById(R.id.zf_empty_RecyclerView);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }


    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void dismissEmptyView(){
        emptyView.setVisibility(GONE);
        mRecyclerView.setVisibility(VISIBLE);
    }

    public void showEmptyView(){
        emptyView.setVisibility(VISIBLE);
        mRecyclerView.setVisibility(GONE);
    }



    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     * @param darkMode 是否黑暗模式
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum, @NonNull boolean darkMode) {
        titleView.setTextColor(ContextCompat.getColor(getContext(),darkMode ? R.color.white:R.color.black_40a));
        iconView.setImageResource(darkMode ? R.mipmap.no_data_icon : R.mipmap.no_data_icon);
        if (pageNum == 1) {
            if (dataSize == 0) {
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }

    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     * @param content  内容
     * @param darkMode 是否黑暗模式
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum, String content, @NonNull boolean darkMode) {
        titleView.setTextColor(ContextCompat.getColor(getContext(),darkMode ? R.color.white:R.color.grey666));
        iconView.setImageResource(darkMode ? R.mipmap.no_data_icon : R.mipmap.no_data_icon);
        titleView.setText(content);
        if (pageNum == 1) {
            if (dataSize == 0) {
                titleView.setTextColor(ContextCompat.getColor(getContext(),darkMode ? R.color.white:R.color.grey666));
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }

    /**
     * @param dataSize 列表数据长度
     * @param pageNum  当前页码
     * @param icon     logo
     * @param content  内容
     */
    public void showEmptyView(@NonNull int dataSize, @NonNull int pageNum, int icon, String content) {
        iconView.setImageResource(icon);
        titleView.setText(content);
        if (pageNum == 1) {
            if (dataSize == 0) {
                emptyView.setVisibility(VISIBLE);
                mRecyclerView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                mRecyclerView.setVisibility(VISIBLE);
            }
        } else {
            emptyView.setVisibility(GONE);
            mRecyclerView.setVisibility(VISIBLE);
        }
    }
}
