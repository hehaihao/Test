package com.xm6leefun.block_browser.home.mvp;


import com.xm6leefun.block_browser.bean.BlockBrowserSearchBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;
import com.xm6leefun.block_browser.home.bean.BlockBrowserHomeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeListBean;
import com.xm6leefun.common.base.BaseView;


import java.util.List;

import io.reactivex.Observable;

/**
 * @Description: 区块浏览器首页
 * @Author: cyh
 * @CreateDate: 2021/4/14
 */
public interface BlockBrowserHomeContract {


    interface IModel {
        Observable<?> getStatics();
        Observable<?> getBlockList();
        Observable<?> getTradeList();
        Observable<?> search(String search);

    }

    interface IView extends BaseView {
        void getStaticsSuccess(BlockBrowserHomeBean homeBean);
        void getBlockListSuccess(List<BlockBrowserBlockListBean> blockListBeans);
        void getTradeListSuccess(List<BlockBrowserTradeListBean> tradeListBeans);

        void getSearchResult(BlockBrowserSearchBean searchBean);
        void getSearchResultEmpty();

        void onLoadFail(String msg);
    }

}
