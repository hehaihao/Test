package com.xm6leefun.block_browser.block.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserBlockListBean {


    /**
     * blockHeight : 36
     * headHash : becac33fff6980ca1ebfae417ec6d6701ce43654d2232b35a51cc49412d9c958
     * tradingCoin : ONLY
     * tradingTotal : 0
     * tradingNum : 0
     * createdTime : 2021-04-11 01:26:39
     * tradingSize : 614
     */

    private int blockHeight;
    private String headHash;
    private String tradingCoin;
    private String tradingTotal;
    private int tradingNum;
    private String createdTime;
    private int tradingSize;


    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getHeadHash() {
        return headHash;
    }

    public void setHeadHash(String headHash) {
        this.headHash = headHash;
    }

    public String getTradingCoin() {
        return tradingCoin;
    }

    public void setTradingCoin(String tradingCoin) {
        this.tradingCoin = tradingCoin;
    }

    public String getTradingTotal() {
        return tradingTotal;
    }

    public void setTradingTotal(String tradingTotal) {
        this.tradingTotal = tradingTotal;
    }

    public int getTradingNum() {
        return tradingNum;
    }

    public void setTradingNum(int tradingNum) {
        this.tradingNum = tradingNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getTradingSize() {
        return tradingSize;
    }

    public void setTradingSize(int tradingSize) {
        this.tradingSize = tradingSize;
    }
}
