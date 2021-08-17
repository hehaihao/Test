package com.xm6leefun.block_browser.product_detail.mvp;


import com.xm6leefun.block_browser.product_detail.bean.ProductDetailBean;
import com.xm6leefun.block_browser.product_detail.bean.ProductTradeBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class ProductDetailModelImp  implements ProductDetailContract.IModel {
    @Override
    public Observable<?> getDetail() {
        return Observable.create((ObservableOnSubscribe<ProductDetailBean>) emitter -> {
            ProductDetailBean detailBean = new ProductDetailBean();
            detailBean.setProduct_name("古董一号");
            detailBean.setAll_address("C9e3dd2***3c53946");
            detailBean.setRuler_address("C9e3dd2***3c53946");
            detailBean.setToken_id("200");
            detailBean.setPhoto_url("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3416177271,254995497&fm=26&gp=0.jpg");
            detailBean.setProduct_introduce("古董为唐代三彩，宽20CM，高：30CM。古董为唐代三彩，宽20CM，高：30CM。古董为唐代三彩，宽20CM，高：30CM。高：30CM。古董为唐代三彩，宽20CM，高：30CM");


            List<ProductTradeBean> list = new ArrayList<>();

            for (int i = 0 ; i <3 ; i++){
                list.add(new ProductTradeBean("转移","0x8806***sffd","0x8806***sffd","2020/01/01 05:00:00"));
            }


            detailBean.setTradeBeanList(list);


            emitter.onNext(detailBean);
            emitter.onComplete();
        });
    }
}
