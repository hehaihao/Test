package com.xm6leefun.block_browser.block_detail.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;


import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserBlockDetailPresenter extends BasePresenter<BlockBrowserBlockDetailContract.IView> {

    private BlockBrowserBlockDetailContract.IModel model;

    public BlockBrowserBlockDetailPresenter(BlockBrowserBlockDetailContract.IView baseView) {
        super(baseView);
        model = new BlockBrowserBlockDetailModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }

    public void getDetail(int height){
        addDisposableObserveOnMain(model.getDetail(height), new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserSearchBean> o) {
                if (o.result!=null){
                    baseView.getDetail(o.result);
                }else{
                    baseView.onLoadFail(o.msg);
                }
            }
            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }

    public void search(String search){
        addDisposableObserveOnMain(model.search(search),  new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserSearchBean> o) {
                if (o.result!= null){
                    baseView.getSearchResult(o.result);
                }else {
                    baseView.getSearchResultEmpty();
                }
            }
            @Override
            public void onError(int code, String msg) {
                baseView.getSearchResultEmpty();
            }
        });

    }
}
