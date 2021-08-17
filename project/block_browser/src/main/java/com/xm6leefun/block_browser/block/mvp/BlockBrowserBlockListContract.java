package com.xm6leefun.block_browser.block.mvp;

import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.common.base.BaseView;




import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public interface BlockBrowserBlockListContract {

    interface IModel {
        Observable<?> getList(long page,long limit);
        Observable<?> search(String search);
    }

    interface IView extends BaseView {
        void getListSuccess(BlockBrowserBlockBean blockBean);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }

}
