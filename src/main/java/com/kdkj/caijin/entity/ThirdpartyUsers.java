package com.kdkj.caijin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 第三方登录用户表
 *
 * @author lin
 * @create 2018-04-20 15:34
 **/
@Data
@Entity
@Table(name = "thirdparty_users")
public class ThirdpartyUsers {
    @Id
    //jpa (hibernate实现)的UUID生成主键策略    //eclipse会提示错误，但程序可以执行
    @GenericGenerator(name = "idGenerator", strategy = "uuid") //这个是hibernate的注解
    @GeneratedValue(generator = "idGenerator") //使用uuid的生成策略
    @Column(length = 64)
    private String id;
    @Column(length = 64)
    private String userid;
    /**授权用户唯一标识*/
    /**	普通用户的标识，对当前开发者帐号唯一*/
    @Column(length = 64)
    private String openid;
    /**普通用户个人资料填写的省份*/
    @Column(length = 64)
    private String province;
    /**普通用户个人资料填写的城市*/
    @Column(length = 64)
    private String city;
    /**国家，如中国为CN*/
    @Column(length = 64)
    private String country;
    /**调用凭证 微博是唯一的*/
    @Column(length = 64)
    private String accessToken;
    /**接入类型*/
    private Integer openType;
    @Transient
    private String headimgurl;
    /**	用户统一标识。微信独有
     * 针对一个微信开放平台帐号下的应用，
     * 同一用户的unionid是唯一的。*/
    @Column(length = 64)
    private String unionid;
    /**微博独有。授权用户的UID，本字段只是为了方便开发者，
     * 减少一次user/show接口调用而返回的，第三方应用不能用此字段作为用户登录状态的识别，
     * 只有access_token才是用户授权的唯一票据。*/
    @Column(length = 64)
    private String uid;
}
