package com.xm6leefun.block_browser.home.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.home.bean.BlockBrowserHomeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/14
 */
public class BlockBrowserHomePresenter extends BasePresenter<BlockBrowserHomeContract.IView> {


    private  BlockBrowserHomeContract.IModel iModel;

    public BlockBrowserHomePresenter(BlockBrowserHomeContract.IView baseView) {
        super(baseView);
        iModel = new  BlockBrowserHomeModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }


    public void getTradeList(){
        addDisposableObserveOnMain(iModel.getTradeList(), new BaseObserver<BaseNftDataBean<BlockBrowserTradeBean>>() {

            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserTradeBean> o) {
                if (o.result!=null && o.result.getRecordList()!=null){
                    baseView.getTradeListSuccess(o.result.getRecordList());
                }else {
                    baseView.onLoadFail("");
                }
            }
            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }


    public void getBlockList(){
        addDisposableObserveOnMain(iModel.getBlockList(), new BaseObserver<BaseNftDataBean<BlockBrowserBlockBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserBlockBean> o) {
                if (o.result!=null && o.result.getRecordList()!=null){
                    baseView.getBlockListSuccess(o.result.getRecordList());
                }else {
                    baseView.onLoadFail("");
                }
            }
            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }


    public void getStatics(){
        addDisposableObserveOnMain(iModel.getStatics(), new BaseObserver<BaseNftDataBean<BlockBrowserHomeBean>>() {

            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserHomeBean> o) {
                if (o.result!= null){
                    baseView.getStaticsSuccess(o.result);
                }else {
                    baseView.onLoadFail("");
                }
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }


    public void search(String search){
        addDisposableObserveOnMain(iModel.search(search),  new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>(baseView,true) {
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
