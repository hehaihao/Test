package com.xm6leefun.block_browser.address_detail.mvp;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/19
 */
public interface BlockBrowserAddressDetailContract {

    interface IModel {
        Observable<?> getScoreList(String address);

        Observable<?> getNftList(String address);

        Observable<?> search(String Search);
    }


    interface IView extends BaseView {
        void getScoreListSuccess(BlockBrowserSearchBean detailBean);
        void getNftListSuccess(BlockBrowserSearchBean detailBean);


        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }
}
