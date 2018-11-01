package com.github.yealove.logeye.vo;

import lombok.Data;

import java.util.Collection;

/**
 * 请求结果
 * Created by Yealove on 2018-11-01.
 */
@Data
public class Result {

    private int code;
    private String msg;
    private int count;
    private Object data;

    public Result(int code, String msg, Object data, int count) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public Result(ResultCode resultCode, Object data, int count) {
        this(resultCode.code, resultCode.msg, data, count);
    }

    public Result(ResultCode resultCode, Object data) {
        this(resultCode, data, data instanceof Collection ? ((Collection) data).size() : 0);
    }


    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result failure(ResultCode resultCode, Object data) {
        return new Result(resultCode, data);
    }

    public static Result failure(String msg, Object data) {
        return new Result(999, msg, data, 0);
    }
}
