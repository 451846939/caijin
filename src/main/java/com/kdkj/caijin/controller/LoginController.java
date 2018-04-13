package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.util.ShiroEncryptionUtils;
import com.kdkj.caijin.vo.LoginVo;
import com.kdkj.caijin.vo.UsersVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录注册
 *
 * @author lin
 * @create 2018-04-08 10:17
 **/
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String yzm = (String) request.getSession().getAttribute("yzm");
        if (loginVo.getYzm().equals(yzm)) {
            Users byStateAndPhone = usersService.findByStateAndPhone(UsersInfo.STATE.getCode(), loginVo.getPhone());
            if (byStateAndPhone == null) {
                addUsers(loginVo, request);
            }

            String token = ShiroEncryptionUtils.getBase64Encryption(loginVo.getPhone());
            //修改token使得其只要验证码正确绝对可以登录成功
            Users users = usersService.findByStateAndPhone(UsersInfo.STATE.getCode(), loginVo.getPhone());
            if (users == null) {
                return Result.error("没有此账号");
            }
            usersService.updateByToken(token, users.getId());
            SecurityUtils.getSubject().login(new UsernamePasswordToken(loginVo.getPhone(), token, "1"));
            request.getSession().removeAttribute("yzm");
            log.info(request.getSession().getId());
            return Result.ok("登录成功", token).put("uid", users.getId());
        }
        return Result.error("验证码错误");
    }

    /**
     * 注册
     */
    public Result addUsers(LoginVo loginVo, HttpServletRequest request) {
        String yzm = (String) request.getSession().getAttribute("yzm");
        if (yzm.equals(loginVo.getYzm())) {
            Users users = new Users();
            try {
                if (StringUtils.isEmpty(loginVo.getPhone())) {
                    return Result.error("电话不能为空");
                }
                users.setPhone(loginVo.getPhone());
                users.setProhibit(UsersInfo.NOT_PROHIBIT.getCode());
                users.setState(UsersInfo.STATE.getCode());
                users.setAuthentication(UsersInfo.NOT_AUTHENTICATION.getCode());
                users.setIntegral(0);
                users.setCreatetime(new Date());
                users.setContributions(0);
                users.setSex(UsersInfo.MAN.getCode());
                usersService.insert(users);
                return Result.ok("成功", users);
            } catch (Exception e) {
                return Result.error(e.getMessage());
            }
        }
        return Result.error("验证码错误");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "电话号码", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getYzm")
    public Result getYzm(String phone, HttpServletRequest request) {
        String s = "";
        try {
            if (StringUtils.isEmpty(phone)) {
                return Result.error("手机号不能为空");
            }
            s = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//            SendSms.sendSms(phone, s);
            request.getSession().setAttribute("yzm", s);
            return Result.ok().put("JSESSIONID", request.getSession().getId()).put("yzm", s);
        } catch (Exception e) {
            return Result.ok("通讯出现了一些小问题请重新发送验证码");
        }
    }

    @GetMapping("/loginout")
    public Result loginout() {
        try {
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.ok("退出成功");
    }

    @PostMapping("/adminLogin")
    public Result adminLogin(@RequestBody UsersVo usersVo) {
        try {
            String simpleHash = new SimpleHash("SHA-1", usersVo.getPassword()).toString();
            SecurityUtils.getSubject().login(new UsernamePasswordToken(usersVo.getPhone(), simpleHash, "2"));
            return Result.ok("登录成功", SecurityUtils.getSubject().getPrincipal());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
