package com.kdkj.caijin.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class Pageinfo implements Serializable {
    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    //	@ApiModelProperty(value = "当前N页", dataType = "int", required = false)
    private int current = 1;
    //	@ApiModelProperty(value = "当前页", dataType = "int", required = false)
    private int pageNum;
    //	@ApiModelProperty(value = "每页显示多少条", dataType = "int", required = false)
    private int pageSize = 10;
    //	@ApiModelProperty(value = "以？排序的字符串", dataType = "string", required = false)
    private String orderBy;
    //总页数
    private Integer totalPages;
    //拥有的元素
    private Long totalElements;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


}
