package com.xm6leefun.block_browser.address_detail.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/19
 */
public class BlockBrowserAddressDetailPresenter extends BasePresenter<BlockBrowserAddressDetailContract.IView> {

    private BlockBrowserAddressDetailContract.IModel model;

    public BlockBrowserAddressDetailPresenter(BlockBrowserAddressDetailContract.IView baseView) {
        super(baseView);
        model = new BlockBrowserAddressDetailModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }



    public void getScoreList(String address){
        addDisposableObserveOnMain(model.getScoreList(address), new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserSearchBean> o) {
                if (o.result!=null){
                    baseView.getScoreListSuccess(o.result);
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

    public void getNftList(String address){
        addDisposableObserveOnMain(model.getNftList(address), new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>() {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserSearchBean> o) {
                if (o.result!=null){
                    baseView.getNftListSuccess(o.result);
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
