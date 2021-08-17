package com.xm6leefun.common.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.base.BaseView;
import com.xm6leefun.common.refresh_view.UltraPullRefreshView;
import com.xm6leefun.common.refresh_view.loadmore.RefreshListener;
import com.xm6leefun.common.widget.svprogress.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:刷新list基类
 * @Author: hhh
 * @CreateDate: 2020/9/23 11:35
 */
public abstract class BaseRefreshFragment<T,P extends BasePresenter> extends Fragment implements RefreshListener, BaseView {
    public static final int FIRST_PAGE = 1;

    protected View rootView;
    protected UltraPullRefreshView ultraPullRefreshView;
    protected List<T> dataList;
    protected SVProgressHUD svProgressHUD;

    /**
     * 0标识下拉刷新。1标识上拉加载
     */
    protected int loadType = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        svProgressHUD = new SVProgressHUD(getActivity());
        if(rootView == null){
            initListView();
            initRecyclerView();
        }
        return rootView;
    }

    public void initRecyclerView() {
        rootView = ultraPullRefreshView.getRootView();
    }

    public void initListView() {
        UltraPullRefreshView.Builder builder = new UltraPullRefreshView.Builder();
        builder.setEnableRefresh(enableRefresh());
        builder.setEnableLoadMore(enableLoadMore());
        builder.setContext(getContext());
        builder.setListener(this);
        builder.setLayout(getListLayoutId());

        ultraPullRefreshView = builder.builder();
    }

    @Override
    public void onRefresh(RecyclerView recyclerView) {
        onRefreshView();
    }

    @Override
    public void onLoadMore(RecyclerView recyclerView) {
        onLoadView(dataList.size() - 1);//在list中的哪个位置插入数据
    }

    public void onEnd(){
        ultraPullRefreshView.endRefresh();
        ultraPullRefreshView.endLoadMore();
        ultraPullRefreshView.getPtrFrameLayout().setEnableOverScrollDrag(true);
    }

    protected void setIsEmptyData(boolean isEmptyData, View.OnClickListener listener){
        ultraPullRefreshView.setIsEmptyData(isEmptyData,listener);
        onEnd();
    }

    /**
     * 显示加载中
     */
    @Override
    public void showLoading(String s) {
        show(s);
    }

    /**
     * 隐藏加载中
     */
    @Override
    public void hideLoading() {
        dismiss();
        onEnd();
    }

    /**
     * token过期重新登录
     */
    @Override
    public void reLogin() {

    }

    /**
     * 显示加载框
     * @param showMessage
     */
    protected void show(String showMessage){
        if(!svProgressHUD.isShowing()){
            svProgressHUD.showWithStatus(showMessage);
        }
    }

    /**
     * 关闭加载框
     */
    protected void dismiss(){
        if(svProgressHUD.isShowing()){
            svProgressHUD.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected int getListLayoutId(){
        return R.layout.ultra_pull_refresh_recy_view;
    }

    protected abstract boolean enableLoadMore();

    protected abstract boolean enableRefresh();

    protected abstract void onRefreshView();

    protected abstract void onLoadView(int position);
}
