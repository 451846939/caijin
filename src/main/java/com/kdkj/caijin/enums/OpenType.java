package com.kdkj.caijin.enums;

/**
 * 用户是否认证
 *
 * @author lin
 * @create 2018-04-02 15:04
 **/
public enum OpenType {
    WX_USER(0, "微信用户"), QQ_USER(1, "QQ用户"),WEIBO_USER(2,"微博用户");
    private Integer code;

    private String message;

    OpenType(Integer code, String message) {
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
