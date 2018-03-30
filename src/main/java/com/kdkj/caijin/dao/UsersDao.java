package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户表dao
 *
 * @author lin
 * @create 19:29 2018/3/29
 * @params * @param null
 **/
@Repository
public interface UsersDao extends JpaRepository<Users, String> {
}
