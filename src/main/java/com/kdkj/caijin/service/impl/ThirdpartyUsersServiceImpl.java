package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.ThirdpartyUsersDao;
import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.ThirdpartyUsers;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.OpenType;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 第三方登录
 *
 * @author lin
 * @create 2018-04-20 17:17
 **/
@Service
@Slf4j
public class ThirdpartyUsersServiceImpl {
    @Autowired
    private ThirdpartyUsersDao thirdpartyUsersDao;
    @Autowired
    private UsersDao usersDao;
    public ThirdpartyUsers insert(ThirdpartyUsers thirdpartyUsers) throws IllegalAccessException, InstantiationException {
        ThirdpartyUsers byUnionid = checkHaveThirdpartyUsers(thirdpartyUsers);
        if (byUnionid == null) {
            Users users = new Users();
            users.setProhibit(UsersInfo.NOT_PROHIBIT.getCode());
            users.setState(UsersInfo.STATE.getCode());
            users.setAuthentication(UsersInfo.NOT_AUTHENTICATION.getCode());
            users.setIntegral(0);
            users.setCreatetime(new Date());
            users.setContributions(0);
            users.setSex(UsersInfo.MAN.getCode());
            CopyObj.copyObjNotNullFieldsAsObj(thirdpartyUsers,users);
            Users userPersisent = usersDao.save(users);
            thirdpartyUsers.setUserid(userPersisent.getId());
            ThirdpartyUsers save = thirdpartyUsersDao.save(thirdpartyUsers);
            return save;
        }
         return byUnionid;
    }

    public ThirdpartyUsers checkHaveThirdpartyUsers(ThirdpartyUsers thirdpartyUsers) {
        if (thirdpartyUsers != null) {
            if (thirdpartyUsers.getOpenType() == null) {
                throw new ErrMsgException("请填写登录来源");
            }
            Integer openType = thirdpartyUsers.getOpenType();
            if (openType == OpenType.WX_USER.getCode()) {
                if (StringUtils.isEmpty(thirdpartyUsers.getUnionid())) {
                    throw new ErrMsgException("微信用户必须填写unionid");
                }
                ThirdpartyUsers byUnionid = thirdpartyUsersDao.findByUnionid(thirdpartyUsers.getUnionid());
                if (byUnionid != null) {
                    return byUnionid;
                }
            }
            if (openType == OpenType.QQ_USER.getCode()) {
                if (StringUtils.isEmpty(thirdpartyUsers.getOpenid())) {
                    throw new ErrMsgException("QQ用户必须填写openid");
                }
                ThirdpartyUsers byOpenid = thirdpartyUsersDao.findByOpenid(thirdpartyUsers.getOpenid());
                if (byOpenid != null) {
                    return byOpenid;
                }
            }
            if (openType == OpenType.WEIBO_USER.getCode()) {
                if (StringUtils.isEmpty(thirdpartyUsers.getAccessToken()) || StringUtils.isEmpty(thirdpartyUsers.getUid())) {
                    throw new ErrMsgException("微博用户必须有accesstoken");
                }
                ThirdpartyUsers byAndAccessToken = thirdpartyUsersDao.findByAccessToken(thirdpartyUsers.getAccessToken());
                if (byAndAccessToken != null) {
                    return byAndAccessToken;
                }
            }
        }
        return null;
    }
}
