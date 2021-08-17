package com.xm6leefun.common.db.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 文件描述：Points_Assets首页积分资产
 * 作者：hhh
 */
public class Points_Assets extends RealmObject {

    /** 唯一识别；钱包地址 + id */
    @PrimaryKey
    private String totalKey;
    private long id;
    private String token_name;
    private String token_logo;
    private String contract_address;
    private String tx_id;

    private int position;

    private long num;

    public Points_Assets() {
    }

    public Points_Assets(String totalKey) {
        this.totalKey = totalKey;
    }

    public String getTotalKey() {
        return totalKey;
    }

    public void setTotalKey(String totalKey) {
        this.totalKey = totalKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken_name() {
        return token_name;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }

    public String getToken_logo() {
        return token_logo;
    }

    public void setToken_logo(String token_logo) {
        this.token_logo = token_logo;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getContract_address() {
        return contract_address;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }



}
