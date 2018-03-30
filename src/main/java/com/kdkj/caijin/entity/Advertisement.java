package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 广告表
 *
 * @author lin
 * @create 2018-03-29 19:00
 **/
@Data
@Entity
@Table(name = "advertisement")
public class Advertisement implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    @Column(length = 32)
    /**广告位名称*/
    private String name;
    @Column(length = 64)
    /**广告图片*/
    private String fileid;
    @Column(length = 255)
    /**广告链接*/
    private String url;
    /**
     * 状态
     */
    private Integer state;
    @Temporal(TemporalType.TIMESTAMP)
    /**开始时间*/
    private Date starttime;
    @Temporal(TemporalType.TIMESTAMP)
    /**结束时间*/
    private Date endtime;
    @Column(length = 255)
    /**说明*/
    private String instruction;
    @Column(length = 32)
    /**位置*/
    private String position;

}
