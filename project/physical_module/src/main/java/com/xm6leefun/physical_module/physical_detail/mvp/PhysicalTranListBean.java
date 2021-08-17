package com.xm6leefun.physical_module.physical_detail.mvp;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/23 10:28
 */
public class PhysicalTranListBean {

    private String txId;
    private String from;
    private String to;
    private int event;
    private long time;
    private int status;
    private String delTime;
    private String delEvent;

    public PhysicalTranListBean(String txId, String from, String to, String delTime, String delEvent) {
        this.txId = txId;
        this.from = from;
        this.to = to;
        this.delTime = delTime;
        this.delEvent = delEvent;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public String getDelEvent() {
        return delEvent;
    }

    public void setDelEvent(String delEvent) {
        this.delEvent = delEvent;
    }
}
