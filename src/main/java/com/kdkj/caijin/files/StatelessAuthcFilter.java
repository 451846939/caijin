package com.kdkj.caijin.files;

import com.alibaba.fastjson.JSON;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.util.ShiroEncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据当前请求上下文信息每次请求时都要登录的认证过滤器
 *
 * @author lin
 * @create 2018-04-08 14:06
 **/
@Slf4j
public class StatelessAuthcFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("你需要登录");
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            if (!StringUtils.isEmpty(token)) {
                try {
                    getSubject(request, response).login(new UsernamePasswordToken(ShiroEncryptionUtils.getBase64Decrypt(token), token));
                    return true;
                } catch (Exception e) {
                    resp.getWriter().write(JSON.toJSONString(Result.error(550, "你还没有登录或登录已过期，请重新登录！")));
                }
            }
            return false;
        }
        return true;
    }
}
