package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.CommentService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.CommentPraise;
import com.kdkj.caijin.vo.CommentVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 评论
 *
 * @author lin
 * @create 2018-04-03 13:43
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        Comment insert = null;
        try {
            insert = commentService.insert(comment);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok("成功", insert);
    }

    @PostMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        try {
            commentService.update(comment);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "informationid", value = "新闻id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)

    })
    /**
     * 根据一条新闻进行分页查询出来的数据中装有评论
     */
    @GetMapping("/findByInformation")
    public Result findByInformationidAndCommentid(String informationid, Pageinfo pageinfo) {
        Page<CommentVo> byInformationidAndCommentid = commentService.findByInformationidAndCommentid(informationid, PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(byInformationidAndCommentid));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)

    })
    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Comment> all = commentService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteByid(String id) {
        commentService.deleteByid(id);
        return Result.ok();
    }
    @PostMapping("/updateByPraise")
    public Result updateByPraiseAndUser(@RequestBody CommentPraise commentPraise){
        try {
            commentService.updateByPraiseAndUser(commentPraise);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "页长度", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderBy", value = "按什么排序", dataType = "int", paramType = "query", required = false)
    })
    @GetMapping("/findByContent")
    public Result findByContent(String content, Pageinfo pageinfo) {
        Page<Comment> byContent = commentService.findByContent(content, PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(byContent));
    }
}
