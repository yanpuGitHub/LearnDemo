package com.yp.networklib.bean;

import com.yp.baseframworklib.bean.BaseBean;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class ExtDataBean extends BaseBean {

    private int code;
    private String msg;
    private String data;

    public ExtDataBean() {
    }

    public ExtDataBean(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
