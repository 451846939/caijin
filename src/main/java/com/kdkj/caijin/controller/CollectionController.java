package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Collection;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.CollectionService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏
 *
 * @author lin
 * @create 2018-04-02 13:45
 **/
@RestController
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public Result addCollection(@RequestBody Collection collection) {
        try {
            Collection insert = collectionService.insert(collection);
            return Result.ok("成功", insert);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userid", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)

    })
    @GetMapping("/findAllByUserid")
    public Result findAll(Pageinfo pageinfo, String userid) {
        Page<Collection> all = collectionService.findAllById(userid, PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            collectionService.deleteByid(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
