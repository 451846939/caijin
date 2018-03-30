package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 信息表（新闻表）
 *
 * @author lin
 * @create 18:14 2018/3/29
 * @params * @param null
 **/
@Data
@Entity
@Table(name = "information")
public class Information implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 标题
     */
    @Column(length = 30)
    private String title;
    /**
     * 类型
     */
    @Column(length = 64)
    private String type;
    /**
     * 作者
     */
    @Column(length = 30)
    private String author;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    /**
     * 来源
     */
    @Column(length = 255)
    private String source;
    /**
     * 摘要
     */
    @Column(length = 32)
    private String tabloid;
    /**
     * 关键词
     */
    @Column(length = 255)
    private String keyword;
    /**
     * 编辑
     */
    @Column(length = 30)
    private String editor;
    /**
     * 内容
     */
    @Column(columnDefinition = "text")
    private String content;
    /**
     * 是否草稿
     */
    private Integer draft;
    /**
     * 是否推荐
     */
    private Integer recommend;
}
