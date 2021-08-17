package com.xm6leefun.common.refresh_view.layoutmanager;

import android.content.Context;
import android.view.View;

import com.xm6leefun.common.listener.OnViewPagerListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/16 17:22
 */
public class SnapLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {

    private PagerSnapHelper mPagerSnapHelper;
    private OnViewPagerListener mOnViewPagerListener;

    public SnapLayoutManager(Context context) {
        super(context);
    }

    public SnapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
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

    }

    public void setOnViewPagerListener(OnViewPagerListener mOnViewPagerListener) {
        this.mOnViewPagerListener = mOnViewPagerListener;
    }

    private int mCurrPosition;
    /**
     * OnScrollListener.SCROLL_STATE_FLING; //屏幕处于甩动状态
     * OnScrollListener.SCROLL_STATE_IDLE; //停止滑动状态
     * OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;// 手指接触状态
     *
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                View view = mPagerSnapHelper.findSnapView(this);
                int position = getPosition(view);
                if (mOnViewPagerListener != null && mCurrPosition != position) {
                    mCurrPosition = position;
                    mOnViewPagerListener.onPageSelected(position, position == getItemCount() - 1);
                }
                break;
            default:
        }
        super.onScrollStateChanged(state);
    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {

    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }
}
