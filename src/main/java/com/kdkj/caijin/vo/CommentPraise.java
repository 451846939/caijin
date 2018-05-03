package com.kdkj.caijin.vo;

import lombok.Data;

/**
 * @author lin
 * @create 2018-04-26 14:32
 **/
@Data
public class CommentPraise {
    private String id;
    private Integer praise;
    /**加或者减*/
    private String action;
}
