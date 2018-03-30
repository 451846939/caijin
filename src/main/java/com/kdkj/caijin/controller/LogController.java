package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Log;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.LogService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 日志
 *
 * @author lin
 * @create 2018-03-30 11:31
 **/
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Log> logs = logService.logSelectAll(new PageRequest(pageinfo.getPageNum(),
                pageinfo.getPageSize(),
                Sort.Direction.ASC,
                pageinfo.getOrderBy() == null ? "id" : pageinfo.getOrderBy()));
        return Result.ok("成功", CopyObj.getInfoInPageinfo(logs));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Log log) {
        try {
            logService.logUpdateByPrimaryKey(log);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "opType", value = "错误类型", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findByOpType")
    public Result findByOpType(Pageinfo pageinfo, String opType) {
        Page<Log> logs = logService.logSelectAllByOpTypeLike(new PageRequest(pageinfo.getPageNum(),
                pageinfo.getPageSize(),
                Sort.Direction.ASC,
                pageinfo.getOrderBy() == null ? "id" : pageinfo.getOrderBy()), opType);
        return Result.ok("成功", CopyObj.getInfoInPageinfo(logs));
    }
}
