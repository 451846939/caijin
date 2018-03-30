package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 投稿表
 *
 * @author lin
 * @create 2018-03-29 18:32
 **/
@Data
@Entity
@Table(name = "contributions")
public class Contributions implements Serializable {
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
     * 分类（infoid）
     */
    @Column(length = 64)
    private String type;
    /**
     * 文件路径
     */
    @Column(length = 255)
    private String filepath;
    /**
     * 投稿时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    /**
     * 是否被采用
     */
    private Integer adopt;
    /**
     * 用户id
     */
    @Column(length = 64)
    private String userid;
}
