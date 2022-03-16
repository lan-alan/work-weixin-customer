package com.alanjgg.wwc.common;

/**
 * @author Alan
 * @Description
 * @date 2022/3/16
 */
public enum ResponseCode {

    OK(0, "ok"),

    SUCCESS(200, "success"),
    FAILED(110, "failed"),
    REQUEST_ERROR(400, "request error"),
    SERVER_ERROR(500, "internal server error");

    private Integer value;
    private String msg;

    ResponseCode(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
