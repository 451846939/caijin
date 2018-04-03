package com.kdkj.caijin.vo;

import lombok.Data;

/**
 * 用户vo
 *
 * @author lin
 * @create 2018-04-02 14:09
 **/
@Data
public class UsersVo {
    private String id;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户角色id
     */
    private String role;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户电话
     */
    private String phone;
}
