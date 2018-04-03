package com.kdkj.caijin.enums;

public enum InformationRecommend {
    NOT_RECOMMEND(0, "不推荐"), RECOMMEND(1, "推荐");

    private Integer code;

    private String message;

    InformationRecommend(Integer code, String message) {
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
