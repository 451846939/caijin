package com.kdkj.caijin.enums;

public enum ContributionsAdopt {
    WAIT(0, "待审核"), SUCCESS(1, "审核通过"), CANCEL(2, "退稿");

    private Integer code;

    private String message;

    ContributionsAdopt(Integer code, String message) {
        this.code = code;
        this.message = message;
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

}
