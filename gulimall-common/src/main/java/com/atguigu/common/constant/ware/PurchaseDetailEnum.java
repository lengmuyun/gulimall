package com.atguigu.common.constant.ware;

public enum PurchaseDetailEnum {

    CREATED(0, "新建"),
    ASSIGNED(1, "已分配"),
    PURCHASING(2, "正在采购"),
    FINISHED(3, "已完成"),
    FAILED(4, "采购失败")
    ;

    private int code;

    private String msg;

    PurchaseDetailEnum(int code, String msg) {
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
