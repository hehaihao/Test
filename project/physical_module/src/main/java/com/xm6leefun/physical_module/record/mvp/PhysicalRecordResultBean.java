package com.xm6leefun.physical_module.record.mvp;

import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/29 14:15
 */
public class PhysicalRecordResultBean {
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
        private String name;
        private String logo;
        private String originatorAddress;//发起人地址
        private String reAddress;//接收人地址
        private int type;//5NFT转出 6NFT转入 7nft创建
        private String typeName;
        private String createTime;

        private String hash;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getOriginatorAddress() {
            return originatorAddress;
        }

        public void setOriginatorAddress(String originatorAddress) {
            this.originatorAddress = originatorAddress;
        }

        public String getReAddress() {
            return reAddress;
        }

        public void setReAddress(String reAddress) {
            this.reAddress = reAddress;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
