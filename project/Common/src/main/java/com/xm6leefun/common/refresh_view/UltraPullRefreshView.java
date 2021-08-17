package com.xm6leefun.common.refresh_view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xm6leefun.common.R;
import com.xm6leefun.common.refresh_view.head.NomalCircleRefreshHeader;
import com.xm6leefun.common.refresh_view.loadmore.LoadMoreListener;
import com.xm6leefun.common.refresh_view.loadmore.OnLoadMoreListener;
import com.xm6leefun.common.refresh_view.loadmore.RefreshListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 10:09
 */
@SuppressWarnings("unused")
public class UltraPullRefreshView implements OnLoadMoreListener {
    private View view, mHeadView;
    private LinearLayout noDataLayout;
    private SmartRefreshLayout frameLayout;
    private RecyclerView recyclerView;
    private RefreshListener listener;

    private LoadMoreListener onLoadListener = new LoadMoreListener();
    private Context context;
    private boolean over = false; //数据是否加载完全
    private Builder mBuilder;

    UltraPullRefreshView(Context context, Builder builder, int layout) {
        this.context = context;
        this.mBuilder = builder;
        initView(layout);
    }

    private void initView(int layout) {
        view = LayoutInflater.from(context).inflate(layout, null);
        noDataLayout = view.findViewById(R.id.no_data_layout);
        frameLayout = view.findViewById(R.id.ultra_pull);
        recyclerView = view.findViewById(R.id.all_order_ry);

        initSmartRefresh();
        setListener(mBuilder.listener);
        initHeadView(mBuilder.headView);
        enableRefresh(mBuilder.enableRefresh, mHeadView);
        enableLoadMore(mBuilder.enableLoadMore);
        loadOver(mBuilder.loadOver);
    }

    private void initSmartRefresh() {
        frameLayout.setEnableOverScrollDrag(true);
        frameLayout.setReboundDuration(500);
    }

    public void setIsEmptyData(boolean isEmptyData, View.OnClickListener listener){
        if (isEmptyData) {
            noDataLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            noDataLayout.setOnClickListener(listener);
        }else{
            noDataLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initHeadView(Object headView) {
        if (headView != null) {
            if (headView instanceof View) {
                this.mHeadView = (View) headView;
            } else if (headView instanceof Integer) {
                this.mHeadView = LayoutInflater.from(context).inflate((int) headView, null);
            }
        } else {
            NomalCircleRefreshHeader tempHead = new NomalCircleRefreshHeader(context);
            if(TextUtils.isEmpty(mBuilder.pullDownRefreshStr))
                tempHead.setPullDownRefreshText(context.getString(R.string.ultra_down_list_header_default_text));
            else//自定义加载文案
                tempHead.setPullDownRefreshText(mBuilder.pullDownRefreshStr);

            if(TextUtils.isEmpty(mBuilder.releaseRefreshStr))
                tempHead.setReleaseRefreshText(context.getString(R.string.ultra_down_list_header_release_text));
            else//自定义加载文案
                tempHead.setReleaseRefreshText(mBuilder.releaseRefreshStr);

            if(TextUtils.isEmpty(mBuilder.loadingStr))//默认加载文案
                tempHead.setRefreshingText(context.getString(R.string.ultra_down_list_header_refresh_text));
            else//自定义加载文案
                tempHead.setRefreshingText(mBuilder.loadingStr);

            if(TextUtils.isEmpty(mBuilder.refreshCompleteStr))//默认加载文案
                tempHead.setRefreshCompleteText(context.getString(R.string.ultra_down_list_header_complete_text));
            else//自定义加载文案
                tempHead.setRefreshCompleteText(mBuilder.refreshCompleteStr);
            this.mHeadView = tempHead;
        }
    }

    public View getRootView() {
        return view;
    }

    /**
     * 开发RecycleView的布局视图,供调用者设置参数
     * <p>
     * Date: 2016/4/8
     * Time: 14:21
     * FIXME
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SmartRefreshLayout getPtrFrameLayout() {
        return frameLayout;
    }

    private void setListener(RefreshListener listener) {
        this.listener = listener;
    }

    public void enableRefresh(final boolean enable, View headView) {
        frameLayout.setEnableRefresh(enable);
        if (enable) {
            if (headView instanceof RefreshHeader) {
                frameLayout.setRefreshHeader((RefreshHeader) headView);
            } else {
                throw new RuntimeException(headView
                        + " must implement RefreshHeader");
            }
            frameLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    onLoadListener.setRefresh(true);  //正在刷新
                    listener.onRefresh(getRecyclerView());
                }
            });
        }
    }

    @Override
    public void onLoadMore(RecyclerView recyclerView) {
        if (!over) {   //数据已经加载完了,不在访问服务器,减小压力
            listener.onLoadMore(recyclerView);
        }
    }

    public void enableLoadMore(boolean enableLoadMore) {
        if (enableLoadMore) {
            getRecyclerView().addOnScrollListener(onLoadListener);   //添加滚动事件判断
            onLoadListener.setOnLoadListener(this);
        }
    }

    public void enableAutoRefresh(boolean enable) {
        if (enable) {
            listener.onRefresh(getRecyclerView());
        }
    }

    public void endRefresh() {
        onLoadListener.setRefresh(false);
        frameLayout.finishRefresh();
    }

    public void endLoadMore() {
        onLoadListener.loadMoreComplete();
    }


    public void loadOver(boolean over) {
        this.over = over;
    }

    public void setRecyclerPaddingTop(int paddingTop) {
        if (recyclerView != null) {
            recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        }
    }

    public static class Builder {
        private String refreshCompleteStr;
        private String releaseRefreshStr;
        private String pullDownRefreshStr;
        private String loadingStr;
        private int layout;
        private boolean enableRefresh;
        private boolean enableLoadMore;
        private boolean loadOver;
        private RefreshListener listener;
        private Context context;
        private Object headView;

        public void setHeadView(Object headView) {
            this.headView = headView;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public Builder setListener(RefreshListener listener) {
            this.listener = listener;
            return this;
        }

        public String getRefreshCompleteStr() {
            return refreshCompleteStr;
        }

        public void setRefreshCompleteStr(String refreshCompleteStr) {
            this.refreshCompleteStr = refreshCompleteStr;
        }

        public String getReleaseRefreshStr() {
            return releaseRefreshStr;
        }

        public void setReleaseRefreshStr(String releaseRefreshStr) {
            this.releaseRefreshStr = releaseRefreshStr;
        }

        public String getPullDownRefreshStr() {
            return pullDownRefreshStr;
        }

        public void setPullDownRefreshStr(String pullDownRefreshStr) {
            this.pullDownRefreshStr = pullDownRefreshStr;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }

        public String getLoadingStr() {
            return loadingStr;
        }

        public void setLoadingStr(String loadingStr) {
            this.loadingStr = loadingStr;
        }

        public boolean isEnableRefresh() {
            return enableRefresh;
        }

        public Builder setEnableRefresh(boolean enableRefresh) {
            this.enableRefresh = enableRefresh;
            return this;
        }

        public boolean isEnableLoadMore() {
            return enableLoadMore;
        }

        public Builder setEnableLoadMore(boolean enableLoadMore) {
            this.enableLoadMore = enableLoadMore;
            return this;
        }

        public boolean isLoadOver() {
            return loadOver;
        }

        public Builder setLoadOver(boolean loadOver) {
            this.loadOver = loadOver;
            return this;
        }

        public UltraPullRefreshView builder() {
            return new UltraPullRefreshView(context, this, layout);
        }
    }
}
