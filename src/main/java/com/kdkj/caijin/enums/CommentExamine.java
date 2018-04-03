package com.kdkj.caijin.enums;

/**
 * 用户是否认证
 *
 * @author lin
 * @create 2018-04-02 15:04
 **/
public enum CommentExamine {
    NOT_EXAMINE(1, "审核通过"), EXAMINE(0, "待审核");
    private Integer code;

    private String message;

    CommentExamine(Integer code, String message) {
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
