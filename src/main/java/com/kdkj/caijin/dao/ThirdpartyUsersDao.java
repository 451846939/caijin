package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.ThirdpartyUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 第三方登录账号
 *
 * @author lin
 * @create 2018-04-20 17:14
 **/
@Repository
public interface ThirdpartyUsersDao extends JpaRepository<ThirdpartyUsers,String>{
    ThirdpartyUsers findByUnionid(String unionid);
    ThirdpartyUsers findByAccessToken(String accessToken);
    ThirdpartyUsers findByOpenid(String openid);
    ThirdpartyUsers findByOpenidAndAccessToken(String openid,String accessToken);
}
