package com.xm6leefun.block_browser.trade.bean;

import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserTradeBean {


    /**
     * recordList : [{"actionId":"608535b7d564563a8b73e27a","action":"474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615","sendAddress":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","receiveAddress":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","actionCoin":"ONLY","actionTotal":"0","createdTime":"2021-03-23 14:51:00","actionType":1,"cost":0}]
     * pageInfo : {"counts":5,"pages":1,"currentPage":1,"currentLimit":5}
     */

    private PageInfoBean pageInfo;
    private List<BlockBrowserTradeListBean> recordList;

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<BlockBrowserTradeListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BlockBrowserTradeListBean> recordList) {
        this.recordList = recordList;
    }

}
