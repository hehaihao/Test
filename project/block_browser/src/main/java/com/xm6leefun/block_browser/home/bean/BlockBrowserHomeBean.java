package com.xm6leefun.block_browser.home.bean;

import java.io.Serializable;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserHomeBean implements Serializable {

    private int nftTotal;

    private int ftTotal;

    private int blockTotal;

    private int actionTotal;


    public int getNftTotal() {
        return nftTotal;
    }

    public void setNftTotal(int nftTotal) {
        this.nftTotal = nftTotal;
    }

    public int getFtTotal() {
        return ftTotal;
    }

    public void setFtTotal(int ftTotal) {
        this.ftTotal = ftTotal;
    }

    public int getBlockTotal() {
        return blockTotal;
    }

    public void setBlockTotal(int blockTotal) {
        this.blockTotal = blockTotal;
    }

    public int getActionTotal() {
        return actionTotal;
    }

    public void setActionTotal(int actionTotal) {
        this.actionTotal = actionTotal;
    }
}
