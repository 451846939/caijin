package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 信息表
 *
 * @author lin
 * @create 19:23 2018/3/29
 * @params * @param null
 **/
@Repository
public interface InformationDao extends JpaRepository<Information, String> {
}
