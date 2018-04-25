package com.kdkj.caijin.vo;

import com.kdkj.caijin.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户vo
 *
 * @author lin
 * @create 2018-04-02 14:09
 **/
@ApiModel("用户")
@Data
public class UsersVo {
    @ApiModelProperty(value = "ID", dataType = "string", required = false)
    private String id;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", dataType = "string", required = false)
    private String nickname;

    /**
     * 用户角色id
     */
    @ApiModelProperty(value = "用户角色id", dataType = "string", required = false)
    private String role;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", dataType = "int", required = false)
    private Integer sex;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", dataType = "int", required = false)
    private String password;
    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话", dataType = "int", required = false)
    private String phone;
}
