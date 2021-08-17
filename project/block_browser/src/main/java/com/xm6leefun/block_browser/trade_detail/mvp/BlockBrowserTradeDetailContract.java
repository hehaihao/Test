package com.xm6leefun.block_browser.trade_detail.mvp;


import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public interface BlockBrowserTradeDetailContract {

    interface IModel {
        Observable<?> getDetail(String hash);
        Observable<?> search(String search);
    }

    interface IView extends BaseView {
        void getDetail(BlockBrowserSearchBean detailBean);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }

}
