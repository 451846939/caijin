package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.vo.CommentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<Comment> findAll(PageRequest pageRequest);

    int insert(Comment comment);

    int update(Comment comment) throws IllegalAccessException, InstantiationException;

    int delete(Comment comment);

    Page<CommentVo> findByInformationidAndCommentid(String informationid, Pageable pageable);

    //    List<CommentVo> findByCommentid(String commentid);
    int deleteByid(String id);

    Page<Comment> findByContent(String content, Pageable pageable);
}
