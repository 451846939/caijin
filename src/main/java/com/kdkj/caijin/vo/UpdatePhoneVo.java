package com.kdkj.caijin.vo;

import lombok.Data;

/**
 * 修改手机号VO
 *
 * @author lin
 * @create 2018-04-09 21:45
 **/
@Data
public class UpdatePhoneVo {
    private String id;
    /**
     * 用户电话
     */
    private String phone;
    private String yzm;
}
