package com.kdkj.caijin.enums;

public enum CommentInfo {
    PARENT_NODE("0", "父节点");
    private String code;

    private String message;

    CommentInfo(String code, String message) {
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
