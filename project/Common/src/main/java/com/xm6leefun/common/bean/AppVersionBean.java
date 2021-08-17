package com.xm6leefun.common.bean;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/5/14
 */
public class AppVersionBean {


    /**
     * version_id : 1
     * version_name : 1.1版本
     * version_type : android
     * version_num : 1.1
     * version_url : http://www.baidu.com/xx
     * is_force : 0
     * remark : 1、优化升级弹框；
     2、修复已知bug、优化部分功能。
     * is_del : 1
     * create_date : 2021-05-13 11:27:20
     */

    private int version_id;
    private String version_name;
    private String version_type;
    private String version_num;
    private String version_url;
    private int is_force;
    private String remark;
    private int is_del;
    private String create_date;

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_type() {
        return version_type;
    }

    public void setVersion_type(String version_type) {
        this.version_type = version_type;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "AppVersionBean{" +
                "version_id=" + version_id +
                ", version_name='" + version_name + '\'' +
                ", version_type='" + version_type + '\'' +
                ", version_num='" + version_num + '\'' +
                ", version_url='" + version_url + '\'' +
                ", is_force=" + is_force +
                ", remark='" + remark + '\'' +
                ", is_del=" + is_del +
                ", create_date='" + create_date + '\'' +
                '}';
    }
}
