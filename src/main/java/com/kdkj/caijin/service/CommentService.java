package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
    Page<Comment> findAll(PageRequest pageRequest);

    int insert(Comment comment);

    int update(Comment comment) throws IllegalAccessException, InstantiationException;

    int delete(Comment comment);
}
