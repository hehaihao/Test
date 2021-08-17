package com.xm6leefun.common.refresh_view.loadmore;


import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 10:42
 */
public interface RefreshListener {
    void onRefresh(RecyclerView recyclerView);
    void onLoadMore(RecyclerView recyclerView);
}
