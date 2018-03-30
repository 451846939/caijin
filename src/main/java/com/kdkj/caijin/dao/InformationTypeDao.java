package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.InformationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 新闻类型dao
 *
 * @author lin
 * @create 19:25 2018/3/29
 * @params * @param null
 **/
@Repository
public interface InformationTypeDao extends JpaRepository<InformationType, String> {
}
