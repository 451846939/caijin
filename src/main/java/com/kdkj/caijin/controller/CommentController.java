package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.CommentService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.CommentVo;
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
        commentService.insert(comment);
        return Result.ok("成功", comment);
    }

    @PostMapping("/update")
    public Result updateComment(@RequestBody Comment comment) {
        try {
            commentService.update(comment);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 根据一条新闻进行分页查询出来的数据中装有评论
     */
    @GetMapping("/findByInformation")
    public Result findByInformationidAndCommentid(String informationid, Pageinfo pageinfo) {
        Page<CommentVo> byInformationidAndCommentid = commentService.findByInformationidAndCommentid(informationid, PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(byInformationidAndCommentid));
    }

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

    @GetMapping("/findByContent")
    public Result findByContent(String content, Pageinfo pageinfo) {
        Page<Comment> byContent = commentService.findByContent(content, PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(byContent));
    }
}
