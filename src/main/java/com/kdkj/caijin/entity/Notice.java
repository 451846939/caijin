package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 快讯表
 *
 * @author lin
 * @create 2018-03-29 18:53
 **/
@Data
@Entity
@Table(name = "notice")
public class Notice implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 快讯内容
     */
    @Column(length = 255)
    private String content;
    /**
     * 文字颜色
     */
    private Integer colour;
    @Temporal(TemporalType.TIMESTAMP)
    /**时间*/
    private Date time;
    /**
     * 星级
     */
    private Integer star;
}
