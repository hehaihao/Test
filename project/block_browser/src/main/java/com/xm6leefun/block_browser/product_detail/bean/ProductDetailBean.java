package com.xm6leefun.block_browser.product_detail.bean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class ProductDetailBean {

    private String product_name;

    private String photo_url;

    private String all_address;

    private String ruler_address;

    private String token_id;

    private String product_introduce;

    private List<ProductTradeBean> tradeBeanList;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getAll_address() {
        return all_address;
    }

    public void setAll_address(String all_address) {
        this.all_address = all_address;
    }

    public String getRuler_address() {
        return ruler_address;
    }

    public void setRuler_address(String ruler_address) {
        this.ruler_address = ruler_address;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getProduct_introduce() {
        return product_introduce;
    }

    public void setProduct_introduce(String product_introduce) {
        this.product_introduce = product_introduce;
    }

    public List<ProductTradeBean> getTradeBeanList() {
        return tradeBeanList;
    }

    public void setTradeBeanList(List<ProductTradeBean> tradeBeanList) {
        this.tradeBeanList = tradeBeanList;
    }
}
