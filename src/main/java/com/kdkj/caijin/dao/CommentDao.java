package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Comment;
import com.kdkj.caijin.vo.CommentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("select new com.kdkj.caijin.vo.CommentVo(c.id,c.userid,c.content,c.commentid,c.createtime,c.updatetime,c.praise,c.informationid,c.examine) from Comment c where c.informationid=?1 and c.examine=?2 and  c.commentid=?3")
//c.commentid is null or
//CommentidIsNullOr
    Page<CommentVo> findByInformationidAndExamineAndCommentid(String informationid, Integer examine, String Commentid, Pageable pageable);

    @Query("select new com.kdkj.caijin.vo.CommentVo(c.id,c.userid,c.content,c.commentid,c.createtime,c.updatetime,c.praise,c.informationid,c.examine) from Comment c where c.commentid=?1")
    List<CommentVo> findByCommentid(String commentid);

    Comment findByIdAndUserid(String id,String userid);

    Page<Comment> findByContentContaining(String content, Pageable pageable);
}
