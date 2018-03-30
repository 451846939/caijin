package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 日志存储
 *
 * @author lin
 * @create 2018-03-30 10:17
 **/
@Repository
public interface LogDao extends JpaRepository<Log, String> {
    Page<Log> findAllByOpTypeContaining(String opType, Pageable pageRequest);
}
