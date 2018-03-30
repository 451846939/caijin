package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 平台规则表
 *
 * @author lin
 * @create 19:27 2018/3/29
 * @params * @param null
 **/
@Repository
public interface PlatformDao extends JpaRepository<Platform, String> {
}
