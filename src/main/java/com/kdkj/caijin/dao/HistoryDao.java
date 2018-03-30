package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 历史记录表dao
 *
 * @author lin
 * @create 19:21 2018/3/29
 * @params * @param null
 **/
@Repository
public interface HistoryDao extends JpaRepository<History, String> {
}
