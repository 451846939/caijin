package com.kdkj.caijin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Information;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告表
 *
 * @author lin
 * @create 2018-03-29 19:00
 **/
@Data
public class AdvertisementVo extends Information implements Serializable {

    private String id;
    /**广告位名称*/
    private String name;
    /**广告图片*/
    private String fileid;
    /**广告链接*/
    private String url;
    /**
     * 状态
     */
    private Integer state;
    /**开始时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    /**结束时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    /**说明*/
    private String instruction;
    /**位置*/
    private String position;
    private Files files;
    /**类型表明这是广告*/
    private String advert;
}
