package com.xm6leefun.block_browser.product_detail.mvp;

import com.xm6leefun.block_browser.block_detail.bean.BlockBrowserBlockDetailBean;
import com.xm6leefun.block_browser.product_detail.bean.ProductDetailBean;
import com.xm6leefun.common.base.BaseView;

import io.reactivex.Observable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public interface ProductDetailContract {


    interface IModel {
        Observable<?> getDetail();
    }

    interface IView extends BaseView {
        void getDetail(ProductDetailBean detailBean);
        void onLoadFail(String msg);
    }
}
