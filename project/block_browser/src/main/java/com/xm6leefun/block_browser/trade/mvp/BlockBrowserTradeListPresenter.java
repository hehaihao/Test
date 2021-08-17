package com.xm6leefun.block_browser.trade.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeTypeBean;
import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserTradeListPresenter extends BasePresenter<BlockBrowserTradeListContract.IView> {

    private BlockBrowserTradeListContract.IModel model;

    public BlockBrowserTradeListPresenter(BlockBrowserTradeListContract.IView baseView) {
        super(baseView);
        model = new BlockBrowserTradeListModelImp(retrofitManager.createRs(BlockBrowserApiService.class));
    }

    public void getList(int type,long limit,long page){
        addDisposableObserveOnMain(model.getList(type,limit,page), new BaseObserver<BaseNftDataBean<BlockBrowserTradeBean>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<BlockBrowserTradeBean> o) {
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

    public void getType(){
        addDisposableObserveOnMain(model.getTypes(), new BaseObserver<List<BlockBrowserTradeTypeBean>>() {
            @Override
            public void onSuccess(List<BlockBrowserTradeTypeBean> listbean) {
                baseView.getTypeSuccess(listbean);
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
