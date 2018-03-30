package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 快讯表
 *
 * @author lin
 * @create 19:25 2018/3/29
 * @params * @param null
 **/
@Repository
public interface NoticeDao extends JpaRepository<Notice, String> {
}
