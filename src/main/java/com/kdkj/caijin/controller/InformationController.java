package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 新闻（信息）
 *
 * @author lin
 * @create 2018-03-31 10:28
 **/
@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @PostMapping("/add")
    public Result addInfoemation(@RequestBody Information information) {
        informationService.insert(information);
        return Result.ok("成功", information);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAllPage(Pageinfo pageinfo) {
        Page<Information> all = informationService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "错误类型", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findByTitle")
    public Result findByTitle(String title, Pageinfo pageinfo) {
        Page<Information> allByTitleContaining = informationService.findAllByTitleContaining(title, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(allByTitleContaining));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Information information) {
        try {
            informationService.update(information);
            return Result.ok("成功");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        int i = informationService.deleteById(id);
        if (i == 1) {
            return Result.ok();
        }
        return Result.error();
    }

}
