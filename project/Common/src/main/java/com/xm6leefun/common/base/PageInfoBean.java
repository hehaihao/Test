package com.xm6leefun.common.base;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/27 14:20
 */
public class PageInfoBean {
    private long pageSize;
    private long currentNum;
    private long currentPage;
    private long totalNum;

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(long currentNum) {
        this.currentNum = currentNum;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }
}
