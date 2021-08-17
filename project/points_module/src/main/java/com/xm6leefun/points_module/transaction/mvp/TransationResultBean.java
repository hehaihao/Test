package com.xm6leefun.points_module.transaction.mvp;

import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/5/6 14:52
 */
public class TransationResultBean {
    private List<TransactionListBean> list;
    private PageInfoBean pageInfo;

    public List<TransactionListBean> getList() {
        return list;
    }

    public void setList(List<TransactionListBean> list) {
        this.list = list;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }
}
