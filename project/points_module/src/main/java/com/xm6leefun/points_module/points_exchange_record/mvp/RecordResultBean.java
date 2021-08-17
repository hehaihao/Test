package com.xm6leefun.points_module.points_exchange_record.mvp;

import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/27 14:18
 */
public class RecordResultBean {
    private List<ListBean> list;
    private PageInfoBean pageInfo;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class ListBean{
        private long id;
        private long exchangeId;
        private String companyUuid;
        private long num;
        private long state;
        private String created;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getExchangeId() {
            return exchangeId;
        }

        public void setExchangeId(long exchangeId) {
            this.exchangeId = exchangeId;
        }

        public String getCompanyUuid() {
            return companyUuid;
        }

        public void setCompanyUuid(String companyUuid) {
            this.companyUuid = companyUuid;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }

        public long getState() {
            return state;
        }

        public void setState(long state) {
            this.state = state;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
