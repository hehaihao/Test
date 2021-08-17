package com.xm6leefun.points_module.points_intro.mvp;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 16:17
 */
public class PointsIntroBean {
    private String company_name;
    private String contract_address;
    private String token_introduce;
    private String status;
    private String create_time;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContract_address() {
        return contract_address;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }

    public String getToken_introduce() {
        return token_introduce;
    }

    public void setToken_introduce(String token_introduce) {
        this.token_introduce = token_introduce;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
