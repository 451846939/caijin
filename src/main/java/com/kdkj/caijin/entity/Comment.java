package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论列表
 *
 * @author lin
 * @create 2018-03-29 18:48
 **/
@Data
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 用户id
     */
    @Column(length = 64)
    private String userid;
    /**
     * 内容
     */
    @Column(length = 255)
    private String content;
    /**
     * 本表id表明上一条 为空表示没有上一条
     */
    @Column(length = 64)
    private String commentid;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    /**
     * 点赞数
     */
    private Integer praise;
    /**
     * 资讯id
     */
    @Column(length = 64)
    private String informationid;
    /**
     * 审核状态0表示未通过1表示通过
     */
    private Integer examine;
}
