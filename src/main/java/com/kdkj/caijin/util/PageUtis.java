package com.kdkj.caijin.util;

import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.vo.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 分页工具
 *
 * @author lin
 * @create 2018-03-31 10:55
 **/
public interface PageUtis {
    public static PageRequest getPageRequest(Pageinfo pageinfo, Sort.Direction direction) {
        return new PageRequest(pageinfo.getPageNum(),
                pageinfo.getPageSize(),
                direction,
                pageinfo.getOrderBy() == null ? "id" : pageinfo.getOrderBy());
    }

    public static PageVo getInfoInPageinfo(Page page) {
        PageVo pageVo = new PageVo();
        Pageinfo pageinfo = pageVo.getPageinfo();
        pageinfo.setPageNum(page.getNumber());
        pageinfo.setPageSize(page.getSize());
        pageinfo.setTotalElements(page.getTotalElements());
        pageinfo.setTotalPages(page.getTotalPages());
        pageVo.setContent(page.getContent());
        return pageVo;
    }
}
