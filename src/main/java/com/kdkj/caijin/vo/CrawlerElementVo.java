package com.kdkj.caijin.vo;

import lombok.Data;

/**
 * 爬虫元素对应的实体对象
 *
 * @author lin
 * @create 2018-04-04 16:11
 **/
@Data
public class CrawlerElementVo {
    /**
     * 开始页面url
     */
    private String startUrl;
    /**
     * 喜好页面url
     */
    private String likeUrl;
    /**
     * 来源
     */
    private String source;
    /**
     * 匹配的url
     */
    private String matchUrl;
    /**
     * 标题元素
     */
    private String titleElement;
    /**
     * 内容元素
     */
    private String contentElement;
    /**
     * 作者元素
     */
    private String authorElement;
    /**
     * 摘要
     */
    private String tabloid;
    /**
     * 爬取数量
     */
    private Integer climbNum;
    /**
     * 类型
     */
    private String type;
    /**
     * 对应网站
     */
    private Integer crawlerWebsite;
}
