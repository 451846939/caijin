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
        information.setSource(null);
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
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "String", paramType = "query", required = false)
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
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            informationService.deleteById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
