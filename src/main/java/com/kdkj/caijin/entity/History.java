package com.kdkj.caijin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 历史记录表
 *
 * @author lin
 * @create 2018-03-29 18:41
 **/
@Data
@Entity
@Table
public class History implements Serializable {
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
     * 信息id
     */
    @Column(length = 64)
    private String infoid;
    /**
     * 日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
