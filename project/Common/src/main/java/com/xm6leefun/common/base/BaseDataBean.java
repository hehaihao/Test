package com.xm6leefun.common.base;

import java.io.Serializable;

/**
 * @Description:实体类的基类，
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public class BaseDataBean<T> implements Serializable {
    /**
     * data :
     * code : 0
     * msg :
     */

    public int code;
    public String msg;
    public T record;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }
}
