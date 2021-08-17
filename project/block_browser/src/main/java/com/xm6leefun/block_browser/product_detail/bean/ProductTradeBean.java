package com.xm6leefun.block_browser.product_detail.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class ProductTradeBean {

    private String event;

    private String origin_address;

    private String to_address;

    private String time;

    public ProductTradeBean(String event, String origin_address, String to_address, String time) {
        this.event = event;
        this.origin_address = origin_address;
        this.to_address = to_address;
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOrigin_address() {
        return origin_address;
    }

    public void setOrigin_address(String origin_address) {
        this.origin_address = origin_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
