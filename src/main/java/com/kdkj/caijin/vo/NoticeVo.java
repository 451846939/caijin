package com.kdkj.caijin.vo;

import com.kdkj.caijin.entity.Notice;
import com.kdkj.caijin.entity.Opinion;
import lombok.Data;

import java.util.List;

/**
 * 消息Vo
 *
 * @author lin
 * @create 2018-04-21 13:48
 **/
@Data
public class NoticeVo {
    private List<Notice> notice;
    private List<Opinion> opinion;
}
