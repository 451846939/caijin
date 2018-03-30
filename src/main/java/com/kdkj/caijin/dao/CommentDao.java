package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 评论列表
 *
 * @author lin
 * @create 19:17 2018/3/29
 * @params * @param null
 **/
@Repository
public interface CommentDao extends JpaRepository<Comment, String> {
}
