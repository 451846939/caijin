package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.enums.ContributionsAdopt;
import com.kdkj.caijin.service.ContributionsService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 投稿
 *
 * @author lin
 * @create 2018-03-31 14:58
 **/
@RestController
@RequestMapping("/ContributionsController")
public class ContributionsController {
    @Autowired
    private ContributionsService contributionsService;
    @Value("${web.multipart-path}")
    private String path;

    @PostMapping("/add")
    public Result add(Contributions contributions, MultipartFile file, HttpServletRequest request) {
//        String realPath = request.getSession().getServletContext().getRealPath("files/");

        try {
            Contributions save = contributionsService.insertContributionsAndFile(contributions, file, path);
            return Result.ok("成功", save);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Contributions> all = contributionsService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all)).put("adoptCount", getAdoptCount());
    }

    private Integer getAdoptCount() {
        return contributionsService.countByAdopt(ContributionsAdopt.WAIT.getCode());
    }

    @PostMapping("/update")
    public Result update(@RequestBody Contributions contributions) {
        try {
            contributionsService.update(contributions);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok().put("adoptCount", getAdoptCount());
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            contributionsService.deleteById(id);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok().put("adoptCount", getAdoptCount());
    }
}
