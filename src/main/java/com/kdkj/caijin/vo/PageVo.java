package com.kdkj.caijin.vo;

import com.kdkj.caijin.entity.Pageinfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一返回带页数的对象
 *
 * @author lin
 * @create 2018-03-30 18:57
 **/
@Data
public class PageVo {
    private Pageinfo pageinfo = new Pageinfo();
    private List<Object> content;
}
