package com.kdkj.caijin.enums;

public enum OrderStatus {
    WAIT("0", "未完结"), SUCCESS("1", "已完结"), CANCEL("2", "取消");

    private String code;

    private String message;

    OrderStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
