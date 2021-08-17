package com.xm6leefun.block_browser.trade_detail.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserTradeDetailBean {


    private String trade_hash;

    private String trade_time;

    private String trade_type;

    private int  height;

    private String currency;

    private int trade_total;

    private float service_charge;

    private String send_address;

    private String receiver_address;

    private String product_name;

    private String token_id;

    private String photo_url;

    private String product_introduce;

    public String getTrade_hash() {
        return trade_hash;
    }

    public void setTrade_hash(String trade_hash) {
        this.trade_hash = trade_hash;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTrade_total() {
        return trade_total;
    }

    public void setTrade_total(int trade_total) {
        this.trade_total = trade_total;
    }

    public float getService_charge() {
        return service_charge;
    }

    public void setService_charge(float service_charge) {
        this.service_charge = service_charge;
    }

    public String getSend_address() {
        return send_address;
    }

    public void setSend_address(String send_address) {
        this.send_address = send_address;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getProduct_introduce() {
        return product_introduce;
    }

    public void setProduct_introduce(String product_introduce) {
        this.product_introduce = product_introduce;
    }
}
