package com.atguigu.common.constant.ware;

public enum PurchaseEnum {

    CREATED(0, "新建"),
    ASSIGNED(1, "已分配"),
    RECEIVED(2, "已领取"),
    FINISHED(3, "已完成"),
    ERROR(4, "有异常")
    ;

    private int code;

    private String msg;

    PurchaseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

}
