package com.xm6leefun.export_module.bean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/21 16:06
 */
public class HomeDataBusBean {
    private String requestAddress;
    private HomeDataResultBean homeDataResultBean;

    public HomeDataBusBean(String requestAddress, HomeDataResultBean homeDataResultBean) {
        this.requestAddress = requestAddress;
        this.homeDataResultBean = homeDataResultBean;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public HomeDataResultBean getHomeDataResultBean() {
        return homeDataResultBean;
    }

    public void setHomeDataResultBean(HomeDataResultBean homeDataResultBean) {
        this.homeDataResultBean = homeDataResultBean;
    }
}
