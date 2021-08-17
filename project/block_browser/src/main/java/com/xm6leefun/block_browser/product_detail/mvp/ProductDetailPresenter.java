package com.xm6leefun.block_browser.product_detail.mvp;


import com.xm6leefun.block_browser.product_detail.bean.ProductDetailBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class ProductDetailPresenter extends BasePresenter<ProductDetailContract.IView> {

    private ProductDetailContract.IModel model;

    public ProductDetailPresenter(ProductDetailContract.IView baseView) {
        super(baseView);
        model = new ProductDetailModelImp();
    }

    public void getDetail() {
        addDisposableObserveOnMain(model.getDetail(), new BaseObserver<ProductDetailBean>() {
            @Override
            public void onSuccess(ProductDetailBean detailBean) {
                baseView.getDetail(detailBean);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });

    }
}