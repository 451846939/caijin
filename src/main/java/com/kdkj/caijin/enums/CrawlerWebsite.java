package com.kdkj.caijin.enums;

/**
 * 爬虫网站enums
 *
 * @author lin
 * @create 2018-04-03 11:51
 **/
public enum CrawlerWebsite {
    BITKAN(0, "http://bitkan.com/news/"), BTC798(1, "http://www.btc798.com/"),
    W_8BTC(2, "http://8btc.com/"), BTC123(3, "http://news.btc123.com/news/");
    private Integer code;

    private String message;

    CrawlerWebsite(Integer code, String message) {
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
