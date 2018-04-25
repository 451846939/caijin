package com.kdkj.caijin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 快讯表(通知)
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
     * 文字颜色0表示红色1表示黄色2表示蓝色3表示绿色
     */
    private Integer colour;
    @Temporal(TemporalType.TIMESTAMP)
    /**时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 星级按照数字表示星级
     */
    private Integer star;
}
