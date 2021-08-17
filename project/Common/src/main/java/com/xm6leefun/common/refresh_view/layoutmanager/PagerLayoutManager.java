package com.xm6leefun.common.refresh_view.layoutmanager;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 视频播放的 LayoutManager
 */
public class PagerLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {

    private PagerSnapHelper mPagerSnapHelper;
    private OnPageChangeListener mOnPageChangeListener;
    private int currentPosition;
    private int extraSpace; //额外缓存的空间
    private boolean haveSelect;

    public PagerLayoutManager(Context context) {
        super(context);
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        view.addOnChildAttachStateChangeListener(this);
        mPagerSnapHelper.attachToRecyclerView(view);
        super.onAttachedToWindow(view);
    }

    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        if (!haveSelect) {
            haveSelect = true;
            currentPosition = getPosition(view);
            mOnPageChangeListener.onPageSelected(currentPosition, view);
        }
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (extraSpace == 0) return super.getExtraLayoutSpace(state);
        return extraSpace;
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            View view = getSnapView();
            if (view != null && mOnPageChangeListener != null) {
                int position = getPosition(view);
                if (currentPosition != position) {
                    currentPosition = position;
                    mOnPageChangeListener.onPageSelected(currentPosition, view);
                }
            }
        }
        super.onScrollStateChanged(state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    public View getSnapView() {
        return mPagerSnapHelper.findSnapView(this);
    }

    public void setExtraSpace(int extraSpace) {
        this.extraSpace = extraSpace;
    }

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }

    public interface OnPageChangeListener {
        void onPageSelected(int itemPosition, View itemView);
    }

}