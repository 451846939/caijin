package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 平台规则表
 *
 * @author lin
 * @create 2018-03-29 18:57
 **/
@Data
@Entity
@Table(name = "platform")
public class Platform implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 平台名称
     */
    @Column(length = 64)
    private String platformName;
    /**
     * app版本
     */
    @Column(length = 20)
    private String appVersion;
    /**
     * 文件上传
     */
    @Column(length = 64)
    private String fileurl;
    /**
     * 投稿积分
     */
    private Integer submitIntegral;
    /**
     * 评论积分
     */
    private Integer commentIntegral;
    /**
     * 登录积分
     */
    private Integer logonIntegral;
    /**
     * 规则
     */
    @Column(columnDefinition = "text")
    private String rule;

}
