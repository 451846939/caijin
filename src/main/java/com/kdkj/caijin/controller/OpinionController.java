package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Opinion;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.enums.OpinionInfo;
import com.kdkj.caijin.service.OpinionService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 意见回复
 *
 * @author lin
 * @create 2018-04-03 11:57
 **/
@RestController
@RequestMapping("/opinion")
public class OpinionController {
    @Autowired
    private OpinionService opinionService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Opinion> all = opinionService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findByUseridAndAnswer")
    public Result findByUseridAndAnswer(String userid, Pageinfo pageinfo) {
        Page<Opinion> byUseridAndAnswer =
                opinionService.
                        findByUseridAndAnswer
                                (userid,
                                        OpinionInfo.ANSWER.getCode(),
                                        PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));

        return Result.ok("成功", PageUtis.getInfoInPageinfo(byUseridAndAnswer));
    }

    @PostMapping("/add")
    public Result add(@RequestBody Opinion opinion) {
        opinionService.insert(opinion);
        return Result.ok("成功", opinion);
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        opinionService.deleteById(id);
        return Result.ok();
    }
}
