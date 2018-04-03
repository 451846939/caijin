package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.vo.CommentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论列表
 *
 * @author lin
 * @create 19:17 2018/3/29
 * @params * @param null
 **/
@Repository
public interface CommentDao extends JpaRepository<Comment, String> {
    Page<CommentVo> findByInformationidAndAndExamineAndCommentidIsNullOrCommentid(String informationid, Integer examine, String Commentid, Pageable pageable);

    List<CommentVo> findByCommentid(String commentid);

    Page<Comment> findByContentContaining(String content, Pageable pageable);
}
