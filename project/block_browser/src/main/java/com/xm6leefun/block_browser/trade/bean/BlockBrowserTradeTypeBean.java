package com.xm6leefun.block_browser.trade.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/16
 */
public class BlockBrowserTradeTypeBean {

    private int type;
    private String type_name;
    private boolean isSelected;


    public BlockBrowserTradeTypeBean(int type, String type_name, boolean isSelected) {
        this.type = type;
        this.type_name = type_name;
        this.isSelected = isSelected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
