package com.xm6leefun.block_browser.block.bean;


import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserBlockBean {

    /**
     * recordList : [{"blockHeight":40,"headHash":"a1fb05fdf0cc82bfaa62ed37d9935989b280a41af4377807f7164cb6ae85ad4b","tradingCoin":"ONLY","tradingTotal":"0","tradingNum":0,"createdTime":"2021-04-11 01:26:59","tradingSize":614},{"blockHeight":39,"headHash":"e8bea1fc7f73646ff45d104f6dc37751dbcf48e5c25e4abcdb304fd763c37281","tradingCoin":"ONLY","tradingTotal":"0","tradingNum":0,"createdTime":"2021-04-11 01:26:54","tradingSize":614},{"blockHeight":38,"headHash":"ff41ef109d7065086e8dc890f11159e4076ac1db4634f9fadff573e2be5b7ae8","tradingCoin":"ONLY","tradingTotal":"0","tradingNum":0,"createdTime":"2021-04-11 01:26:49","tradingSize":614},{"blockHeight":37,"headHash":"cea808faf543f9bc1aec025950d64824b9076694fda7458f8263ef144da89ec2","tradingCoin":"ONLY","tradingTotal":"0","tradingNum":0,"createdTime":"2021-04-11 01:26:44","tradingSize":614},{"blockHeight":36,"headHash":"becac33fff6980ca1ebfae417ec6d6701ce43654d2232b35a51cc49412d9c958","tradingCoin":"ONLY","tradingTotal":"0","tradingNum":0,"createdTime":"2021-04-11 01:26:39","tradingSize":614}]
     * pageInfo : {"counts":46,"pages":10,"currentPage":1,"currentLimit":5}
     */

    private PageInfoBean pageInfo;
    private List<BlockBrowserBlockListBean> recordList;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BlockBrowserBlockListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BlockBrowserBlockListBean> recordList) {
        this.recordList = recordList;
    }




}
