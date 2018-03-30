package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author lin
 * @create 19:28 2018/3/29
 * @params * @param null
 **/
@Data
@Entity
@Table(name = "users")
public class Users implements Serializable {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    /**
     * 用户账号
     */
    @Column(length = 64)
    private String username;
    /**
     * 用户密码
     */
    @Column(length = 64)
    private String password;
    /**
     * 用户电话
     */
    @Column(length = 64)
    private String phone;
    /**
     * 用户昵称
     */
    @Column(length = 64)
    private String nickname;
    /**
     * 用户角色id
     */
    @Column(length = 64)
    private String role;
    /**
     * 头像图片地址fileid
     */
    @Column(length = 64)
    private String headurl;
    /**
     * 身份证图片地址fileid
     */
    @Column(length = 64)
    private String idcarurl;
    /**
     * 登录用的token
     */
    @Column(length = 64)
    private String token;
    /**
     * 是否封号
     */
    @Column(length = 3)
    private Integer prohibit;
    /**
     * 是否启用
     */
    @Column(length = 3)
    private Integer state;
    /**
     * 是否认证
     */
    @Column(length = 3)
    private Integer authentication;
    /**
     * 用户积分
     */
    @Column(length = 3)
    private Integer integral;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;
    /**
     * 最近登录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    /**
     * 投稿数
     */

    private Integer contributions;
}
