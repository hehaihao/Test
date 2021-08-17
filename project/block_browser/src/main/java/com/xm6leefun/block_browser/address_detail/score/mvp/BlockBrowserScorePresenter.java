package com.xm6leefun.block_browser.address_detail.score.mvp;


import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;



public class BlockBrowserScorePresenter extends BasePresenter<BlockBrowserScoreContract.IView> {

    private  BlockBrowserScoreContract.IModel iModel;

    public BlockBrowserScorePresenter(BlockBrowserScoreContract.IView baseView) {
        super(baseView);
        iModel = new BlockBrowserScoreModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }


    /**
     * 获取列表
     */
    public void getList(boolean isLoadMore,String address,int limit,int page){
        addDisposableObserveOnMain(iModel.getList(address, limit, page), new BaseObserver<BaseNftDataBean<BlockBrowserSearchBean>>(baseView,true){
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserSearchBean> o) {
                if(o != null && o.getResult() != null) baseView.getListSuccess(o.getResult(),isLoadMore);
                else baseView.onLoadFail("data_null");
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