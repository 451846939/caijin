package com.kdkj.caijin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 意见回复
 *
 * @author lin
 * @create 2018-03-29 18:44
 **/
@Data
@Entity
@Table(name = "opinion")
public class Opinion implements Serializable {
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
     * 意见内容
     */
    @Column(length = 255)
    private String content;
    /**
     * 回复内容
     */
    @Column(length = 255)
    private String reply;
    /**
     * 提交时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    /**
     * 处理时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    /**
     * 是否被查看SEE(1, "被查看"), NOT_SEE(0, "未被查看"),
     */
    private Integer see;
    /**
     * 是否回复ANSWER(1, "被回复"), NOT_ANSWER(0, "未被回复");
     */
    private Integer answer;
}
