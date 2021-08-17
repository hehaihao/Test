package com.xm6leefun.block_browser.address_detail.score.mvp;


import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;


public interface BlockBrowserScoreContract {


    interface IModel {
        Observable<?> getList(String address,int limit,int page);

        Observable<?> search(String Search);
    }


    interface IView extends BaseView {

        void getListSuccess(BlockBrowserSearchBean bean,boolean isLoadMore);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }
}
