package com.kdkj.caijin.util;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 相应头
 *
 * @author lin
 * @create 2018-04-13 15:36
 **/

public class ResponseHeader implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getSession() != null) {
//            response.setHeader("myCookie",request.getSession().getId());
            response.addHeader("myCookie", request.getSession().getId());
        }

//        if (SecurityUtils.getSubject().getPrincipal() == null) {
//            String token = request.getHeader("myCookie");
//            if (!StringUtils.isEmpty(token)) {
//                try {
//                    SecurityUtils.getSubject().login(new UsernamePasswordToken(ShiroEncryptionUtils.getBase64Decrypt(token), token, "0"));
//                    return true;
//                } catch (Exception e) {
//                    response.getWriter().write(JSON.toJSONString(Result.error(550, "你还没有登录或登录已过期，请重新登录！")));
//                }
//            }
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(JSON.toJSONString(Result.error(550, "你还没有登录或登录已过期，请重新登录！")));
//            return false;
//        }
        return true;
    }


//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        if (request.getSession()!=null){
//            response.setHeader("myCookie",request.getSession().getId());
//            response.addHeader("myCookie",request.getSession().getId());
//        }
//    }
}
