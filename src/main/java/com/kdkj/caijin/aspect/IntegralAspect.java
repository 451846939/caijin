package com.kdkj.caijin.aspect;

import com.kdkj.caijin.controller.LoginController;
import com.kdkj.caijin.entity.Platform;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.service.PlatformService;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.service.impl.CommentServiceImpl;
import com.kdkj.caijin.service.impl.ContributionsServiceImpl;
import com.kdkj.caijin.util.ErrMsgException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 积分增加切面
 *
 * @author lin
 * @create 2018-04-03 19:53
 **/
@Aspect
@Component
@Slf4j
public class IntegralAspect {
    @Autowired
    private PlatformService platformService;
    @Autowired
    private UsersService usersService;

    @Pointcut("execution(* com.kdkj.caijin.service..CommentService.insert*(..))||" +
            "execution(* com.kdkj.caijin.service..ContributionsService.insert*(..))||" +
            "execution(* com.kdkj.caijin.controller..LoginController.login(..))")
    public void intergralPointcut() {

    }

    @After("intergralPointcut()")
    public void after(JoinPoint joinPoint) {
        //todo
        log.info("这里有个积分增加还没有测试");
        Class<?> aClass = joinPoint.getTarget().getClass();
        List<Platform> all = platformService.findAll();
        if (all.isEmpty()) {
            return;
        }
        Platform platform = all.get(0);
        Users users = (Users) SecurityUtils.getSubject().getPrincipal();
        if (users == null) {
            return;
        }
        Integer integral = users.getIntegral();
        if (aClass == CommentServiceImpl.class) {
            //评论
            Integer commentIntegral = platform.getCommentIntegral();
            users.setIntegral(integral + commentIntegral);
            try {
                usersService.update(users);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (aClass == ContributionsServiceImpl.class) {
            //投稿
            Integer submitIntegral = platform.getSubmitIntegral();
            users.setIntegral(integral + submitIntegral);
            users.setContributions(users.getContributions() + 1);
            try {
                usersService.update(users);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (aClass == LoginController.class) {
            //登录
            Integer logonIntegral = platform.getLogonIntegral();
            users.setIntegral(logonIntegral + integral);
            try {
                usersService.update(users);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
