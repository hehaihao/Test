package com.xm6leefun.block_browser.block.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;



/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserBlockListPresenter  extends BasePresenter<BlockBrowserBlockListContract.IView> {

    private BlockBrowserBlockListContract.IModel model;


    public BlockBrowserBlockListPresenter(BlockBrowserBlockListContract.IView baseView) {
        super(baseView);
        model = new BlockBrowserBlockListModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }


    public void getList(long limit,long page){
        addDisposableObserveOnMain(model.getList(limit,page), new BaseObserver<BaseNftDataBean<BlockBrowserBlockBean>>(baseView,true) {

            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserBlockBean> o) {
                if (o.result!=null){
                    baseView.getListSuccess(o.result);
                }else{
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
