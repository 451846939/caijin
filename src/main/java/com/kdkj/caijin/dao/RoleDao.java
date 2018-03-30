package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色表
 *
 * @author lin
 * @create 19:28 2018/3/29
 * @params * @param null
 **/
@Repository
public interface RoleDao extends JpaRepository<Role, String> {
}
