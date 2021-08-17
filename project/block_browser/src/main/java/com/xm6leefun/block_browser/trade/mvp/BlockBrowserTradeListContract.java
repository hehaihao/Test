package com.xm6leefun.block_browser.trade.mvp;


import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeTypeBean;
import com.xm6leefun.common.base.BaseView;
import java.util.List;
import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public interface BlockBrowserTradeListContract {

    interface IModel {
        Observable<?> getList(int type,long page,long limit);
        Observable<?> getTypes();
        Observable<?> search(String search);

    }

    interface IView extends BaseView {

        void getTypeSuccess(List<BlockBrowserTradeTypeBean> beanList);
        void getListSuccess(BlockBrowserTradeBean tradeBean);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();


        void onLoadFail(String msg);
    }
}
