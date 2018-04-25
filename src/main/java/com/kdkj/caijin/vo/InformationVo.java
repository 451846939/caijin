package com.kdkj.caijin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdkj.caijin.entity.Collection;
import com.kdkj.caijin.entity.Files;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * informationVo
 *
 * @author lin
 * @create 2018-04-16 17:53
 **/
@Data
public class InformationVo {
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 作者
     */
    private String author;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    /**
     * 来源
     */
    private String source;
    /**
     * 摘要
     */
    private String tabloid;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 编辑
     */
    private String editor;
    /**
     * 内容
     */
    private String content;
    /**
     * 爬取的内容html
     */
    private String html;
    /**
     * 是否草稿
     */
    private Integer draft;
    /**
     * 是否推荐
     */
    private Integer recommend;
    /**表明这是不是新闻*/
    private String advert;
    /**展示的图片id*/
    private String showpicture;
    /**具体的图片*/
    private Files picture;
}
