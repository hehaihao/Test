package com.xm6leefun.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/25
 */
public class BaseListDataBean<T> implements Serializable {


    /**
     * data :
     * code : 0
     * msg :
     */

    public int code;
    public String msg;
    public List<T> result;

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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
