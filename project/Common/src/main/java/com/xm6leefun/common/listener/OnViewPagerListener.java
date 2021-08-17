package com.xm6leefun.common.listener;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/16 17:23
 */
public interface OnViewPagerListener {
    /**
     * 选中的监听以及判断是否滑动到最后
     * @param position
     * @param isBottom
     */
    void onPageSelected(int position, boolean isBottom);

}
