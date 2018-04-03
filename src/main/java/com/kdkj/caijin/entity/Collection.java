package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 收藏
 *
 * @author lin
 * @create 2018-03-29 18:36
 **/
@Data
@Entity
@Table(name = "collection")
public class Collection implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    @Column(length = 64)
    /**用户id*/
    private String userid;
    @Column(length = 64)
    /**新闻id*/
    private String informationid;
    @Temporal(TemporalType.TIMESTAMP)
    /**创建时间*/
    private Date time;
}
