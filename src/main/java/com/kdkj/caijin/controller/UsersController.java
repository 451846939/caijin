package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.UsersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 用户
 *
 * @author lin
 * @create 2018-04-02 14:03
 **/
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/findAll")
    public Result findAll(Pageinfo pageinfo) {
        Page<Users> all = usersService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result addUsers(@RequestBody String phone) {
        Users users = new Users();
        try {
            if (StringUtils.isEmpty(phone)) {
                return Result.error("电话不能为空");
            }
            users.setPhone(phone);
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
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传身份证
     */
    @PostMapping("/uploadIdcar")
    public Result uploadIdcar(String userid, MultipartFile file) {
        try {
            Files files = usersService.uploadIdcar(userid, file);
            return Result.ok("成功", files);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }

    }

    /**
     * 上传头像
     */
    @PostMapping("/uploadHeadurl")
    public Result uploadHeadurl(String userid, MultipartFile file) {
        try {
            Files files = usersService.uploadHeadurl(userid, file);
            return Result.ok("成功", files);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/update")
    public Result updateUsers(@RequestBody UsersVo usersVo) {
        //
        try {
            usersService.updateUsersByVo(usersVo);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestBody UsersVo usersVo) {
        usersService.updateByPhone(usersVo.getPhone(), usersVo.getId());
        return Result.ok();
    }

    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody UsersVo usersVo) {
        usersService.updateByPwd(usersVo.getPassword(), usersVo.getId());
        return Result.ok();
    }
}
