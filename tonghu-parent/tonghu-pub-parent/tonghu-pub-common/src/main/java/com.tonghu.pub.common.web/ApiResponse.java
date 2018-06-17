package com.tonghu.pub.common.web;

import java.io.Serializable;

public class ApiResponse<B, String, Integer> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private B result;

    public ApiResponse() {
        super();
    }

    public ApiResponse(B result, String message, Integer code) {
        this();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public B getResult() {
        return result;
    }

    public void setResult(B result) {
        this.result = result;
    }
}
