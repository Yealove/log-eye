package com.github.yealove.logeye.vo;

/**
 * 返回结果编码
 * Created by Yealove on 2018-11-01.
 */
public enum ResultCode {

    SUCCESS(0, "success"),
    INVALID_DATA(1, "invalid data");

    int code;
    String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
