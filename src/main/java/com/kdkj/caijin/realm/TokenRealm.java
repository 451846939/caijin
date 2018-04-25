package com.kdkj.caijin.realm;

import com.kdkj.caijin.entity.Role;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.service.RoleService;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.ErrMsgException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TokenRealm extends AuthorizingRealm {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleService roleService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		StoresList user = (StoresList) principals.getPrimaryPrincipal();
        Users users = (Users) principals.getPrimaryPrincipal();
        String role = users.getRole();
        if (StringUtils.isEmpty(role)) {
            throw new ErrMsgException("role不能为空");
        }
        try {
            Role byId = roleService.findById(role);
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> roles = new HashSet<>();
            roles.add(byId.getName());
            Set<String> powers = new HashSet<>();
            powers.add(byId.getPower());
            info.setRoles(roles);
            info.setStringPermissions(powers);
            return info;
        } catch (Exception e) {
            throw new ErrMsgException("没有此角色");
        }
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String phone = (String) token.getPrincipal();
        Users byStateAndPhone = usersService.findByStateAndPhone(UsersInfo.STATE.getCode(), phone);
        String usersToken = byStateAndPhone.getToken();
        SimpleAuthenticationInfo info = null;
        if (byStateAndPhone == null) {
            //是否为第三方登录的
            Users byId = usersService.findById(phone);
            if (byId == null) {
                throw new ErrMsgException("没有该用户");
            }
            //第三方登录因为可能会没有手机号，所以第三方就直接放行
            info = new SimpleAuthenticationInfo(byId, "1", getName());
            return info;
        }
        info = new SimpleAuthenticationInfo(byStateAndPhone, usersToken, getName());
        return info;
    }

}
