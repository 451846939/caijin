package com.kdkj.caijin.enums;

/**
 * 用户是否认证
 *
 * @author lin
 * @create 2018-04-02 15:04
 **/
public enum UsersInfo {
    AUTHENTICATION(1, "已经认证"), NOT_AUTHENTICATION(0, "没有认证"),
    STATE(11, "已经启用"), NOT_STATE(10, "没有启用"),
    PROHIBIT(21, "已经封号"), NOT_PROHIBIT(20, "没有封号"),
    MAN(31, "男性"), WOMAN(30, "女性");
    private Integer code;

    private String message;

    UsersInfo(Integer code, String message) {
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
