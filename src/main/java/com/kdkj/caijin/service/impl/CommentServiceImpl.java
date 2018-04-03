package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.CommentDao;
import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.enums.CommentExamine;
import com.kdkj.caijin.enums.CommentInfo;
import com.kdkj.caijin.service.CommentService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import com.kdkj.caijin.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

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
            comment.setCreatetime(new Date());
            if (comment.getExamine() == null) {
                comment.setExamine(CommentExamine.EXAMINE.getCode());
            }
            commentDao.save(comment);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Comment comment) throws IllegalAccessException, InstantiationException {
        if (comment != null) {
            comment.setUpdatetime(new Date());
            if (comment.getExamine() == null) {
                comment.setExamine(CommentExamine.NOT_EXAMINE.getCode());
            }
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
            return 1;
        }
        return 0;
    }

    @Override
    public Page<CommentVo> findByInformationidAndCommentid(String informationid, Pageable pageable) {
        Page<CommentVo> commentsPage = commentDao.findByInformationidAndAndExamineAndCommentidIsNullOrCommentid(informationid, CommentExamine.NOT_EXAMINE.getCode(), CommentInfo.PARENT_NODE.getCode(), pageable);
        List<CommentVo> content = commentsPage.getContent();
        //首先循环查出来的comment然后把id进行查询看是否有子节点
        setCommentVo(content);
        return commentsPage;
    }

    @Override
    public int deleteByid(String id) {
        if (!StringUtils.isEmpty(id)) {
            commentDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }

    @Override
    public Page<Comment> findByContent(String content, Pageable pageable) {
        return commentDao.findByContentContaining(content, pageable);
    }

    private void setCommentVo(List<CommentVo> content) {
        content.forEach(e -> {
            List<CommentVo> byCommentid = commentDao.findByCommentid(e.getId());
            if (!byCommentid.isEmpty()) {
                e.setChildrenComment(byCommentid);
                setCommentVo(byCommentid);
            }
        });
    }

//    @Override
//    public List<CommentVo> findByCommentid(String commentid) {
//        return null;
//    }
}
