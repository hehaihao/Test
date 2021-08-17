package com.xm6leefun.common.refresh_view.loadmore;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 10:11
 */
public class LoadMoreListener extends RecyclerView.OnScrollListener{
    private boolean mIsLoadingMore = false;
    private boolean mRefresh = false;
    private OnLoadMoreListener mOnLoadListener;
    private int dx,dy;

    public boolean isLoadingMore(){
        return mIsLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore){
        this.mIsLoadingMore = loadingMore;
    }

    public void setOnLoadListener(OnLoadMoreListener loadMoreListener){
        this.mOnLoadListener = loadMoreListener;
    }

    public boolean isRefresh(){
        return mRefresh;
    }

    public void setRefresh(boolean refresh){
        this.mRefresh = refresh;
    }

    public void loadMoreComplete(){
        setLoadingMore(false);//恢复初始状态
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        this.dx = dx;
        this.dy = dy;
        super.onScrolled(recyclerView,dx,dy);
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition;
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE://处于空闲状态
                if(layoutManager instanceof StaggeredGridLayoutManager){
                    int into[] = new int[((StaggeredGridLayoutManager)layoutManager).getSpanCount()];//获取有多少列元素
                    ((StaggeredGridLayoutManager)layoutManager).findLastCompletelyVisibleItemPositions(into);
                    lastVisibleItemPosition = findMax(into);
                }else{
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                }
                //不能同时刷新数据和加载数据
                if((dx > 0 || dy > 0) && !isRefresh() && layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > 3){
                    if(!isLoadingMore()){
                        setLoadingMore(true);//说明正在上拉加载
                        mOnLoadListener.onLoadMore(recyclerView);
                    }
                }
                break;
        }
    }

    private int findMax(int[] into) {
        int max = into[0];
        for(int value : into){
            if(value > max){
                max = value;
            }
        }
        return max;
    }
}
