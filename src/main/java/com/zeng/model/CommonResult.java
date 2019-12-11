package com.zeng.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 1;
    public static final int FAILED = 0;

    // bi-service 10.0.102.21 add/delete/update userId method id condition param
    // bi-service 10.0.102.21 search  num param

    private int code = SUCCESS;
    private String msg = "操作成功";
    private Object data;

    public CommonResult success(T data) {
        this.data = data;
        return this;
    }

    public CommonResult failed(Throwable e) {
        this.code = FAILED;
        this.msg = e.toString();
        return this;
    }

    public CommonResult failed(String msg) {
        this.code = FAILED;
        this.msg = msg;
        return this;
    }
}
