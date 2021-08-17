package com.xm6leefun.setting_module.nodesetting.mvp;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */
public class NodeBean {
    private String nodeName;
    private String nodeUrl;
    private String contractAddress;

    public NodeBean(String nodeName, String nodeUrl, String contractAddress) {
        this.nodeName = nodeName;
        this.nodeUrl = nodeUrl;
        this.contractAddress = contractAddress;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
