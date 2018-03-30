package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.CommentDao;
import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.service.CommentService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<Comment> findAll(PageRequest pageRequest) {
        return commentDao.findAll(pageRequest);
    }

    @Override
    public int insert(Comment comment) {
        if (comment != null) {
            commentDao.save(comment);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Comment comment) throws IllegalAccessException, InstantiationException {
        if (comment != null) {
            Comment oldComment = commentDao.findById(comment.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(comment, oldComment);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Comment comment) {
        if (comment != null) {
            commentDao.delete(comment);
        }
        return 0;
    }
}
