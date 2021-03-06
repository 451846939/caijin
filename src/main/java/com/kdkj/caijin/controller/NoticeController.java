package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Notice;
import com.kdkj.caijin.entity.Opinion;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.enums.OpinionInfo;
import com.kdkj.caijin.service.NoticeService;
import com.kdkj.caijin.service.OpinionService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.NoticeVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 通知
 *
 * @author lin
 * @create 2018-04-02 10:28
 **/
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private OpinionService opinionService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Notice> all = noticeService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @GetMapping("/findByMyNotice")
    public Result findByMyNotionce(String userid, Pageinfo pageinfo) {
        try {
//            Page<Notice> all = noticeService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
            Pageinfo opinionPageinfo = new Pageinfo();
            CopyObj.copyObjNotNullFieldsAsObj(pageinfo, opinionPageinfo);
            opinionPageinfo.setOrderBy("updateTime");
            Page<Opinion> byUseridAndAnswer = opinionService.findByUseridAndAnswer(userid, OpinionInfo.ANSWER.getCode(), PageUtis.getPageRequest(opinionPageinfo, Sort.Direction.DESC));
            NoticeVo noticeVo = new NoticeVo();
//            noticeVo.setNotice(all.getContent());
            noticeVo.setOpinion(byUseridAndAnswer.getContent());
            return Result.ok("成功", noticeVo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result addNotice(@RequestBody Notice notice) {
        try {
            Notice insert = noticeService.insert(notice);
            return Result.ok("成功", insert);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result updateNotice(@RequestBody Notice notice) {
        try {
            noticeService.update(notice);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteNotice(@PathVariable("id") String id) {

        try {
            noticeService.deleteByid(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
