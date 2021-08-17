package com.xm6leefun.block_browser.block_detail.mvp;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block_detail.bean.BlockBrowserBlockDetailBean;
import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public interface BlockBrowserBlockDetailContract {


    interface IModel {
        Observable<?> getDetail(int height);
        Observable<?> search(String Search);

    }

    interface IView extends BaseView {
        void getDetail(BlockBrowserSearchBean detailBean);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }


}
