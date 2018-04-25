package com.kdkj.caijin.enums;

/**
 * 意见回复enums
 *
 * @author lin
 * @create 2018-04-03 11:51
 **/
public enum StateInfo {
    STATE(1, "被启用"), NOT_STATE(0, "未被启用");
    private Integer code;

    private String message;

    StateInfo(Integer code, String message) {
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
