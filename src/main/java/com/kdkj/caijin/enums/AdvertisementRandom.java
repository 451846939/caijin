package com.kdkj.caijin.enums;

public enum AdvertisementRandom {
    ONE("1","第一个"),TWO("2","第2个")
    ,THREE("3","第一个"),FOUR("4","第4个"),
    FIVE("5","第5个"),SIX("6","第6个"),
    SEVEN("7","第7个");
    private String code;

    private String message;

    AdvertisementRandom(String code, String message) {
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
