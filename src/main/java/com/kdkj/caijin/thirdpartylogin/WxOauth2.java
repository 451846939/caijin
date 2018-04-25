package com.kdkj.caijin.thirdpartylogin;

import lombok.Data;

/**
 * 用于换取access_token的实体类
 *
 * @author lin
 * @create 2018-04-20 10:36
 **/
@Data
public class WxOauth2 {
    /**接口调用凭证*/
    private String access_token;
    /**access_token接口调用凭证超时时间，单位（秒）*/
    private String expires_in;
    /**用户刷新access_token*/
    private String refresh_token;
    /**授权用户唯一标识*/
    private String openid;
    /**用户授权的作用域，使用逗号（,）分隔*/
    private String scope;
}
