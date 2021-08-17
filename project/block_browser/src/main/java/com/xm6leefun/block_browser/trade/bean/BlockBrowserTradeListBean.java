package com.xm6leefun.block_browser.trade.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserTradeListBean {


    /**
     * actionId : 608535b7d564563a8b73e27a
     * action : 474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615
     * sendAddress : oc89cafe4937db20221c8ccd1ed1573638a27f661e
     * receiveAddress : oc89cafe4937db20221c8ccd1ed1573638a27f661e
     * actionCoin : ONLY
     * actionTotal : 0
     * createdTime : 2021-03-23 14:51:00
     * actionType : 1
     * cost : 0
     */

    private String actionId;
    private String action;
    private String sendAddress;
    private String receiveAddress;
    private String actionCoin;
    private String actionTotal;
    private String createdTime;
    private int actionType;
    private int cost;
    private String actionTypeName;

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getActionCoin() {
        return actionCoin;
    }

    public void setActionCoin(String actionCoin) {
        this.actionCoin = actionCoin;
    }

    public String getActionTotal() {
        return actionTotal;
    }

    public void setActionTotal(String actionTotal) {
        this.actionTotal = actionTotal;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
