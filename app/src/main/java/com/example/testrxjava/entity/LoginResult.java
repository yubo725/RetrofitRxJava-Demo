package com.example.testrxjava.entity;

/**
 * Created by yubo on 2017/6/29.
 */

public class LoginResult {

    private int code;
    private String msg;

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

    @Override
    public String toString() {
        return "LoginResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
