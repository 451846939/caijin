package com.kdkj.caijin.enums;

/**
 * 意见回复enums
 *
 * @author lin
 * @create 2018-04-03 11:51
 **/
public enum OpinionInfo {
    SEE(1, "被查看"), NOT_SEE(0, "未被查看"),
    ANSWER(1, "被回复"), NOT_ANSWER(0, "未被回复");
    private Integer code;

    private String message;

    OpinionInfo(Integer code, String message) {
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
