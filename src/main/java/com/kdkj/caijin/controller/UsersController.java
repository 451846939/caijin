package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.entity.Role;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.RoleService;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import com.kdkj.caijin.util.PageUtis;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.LoginVo;
import com.kdkj.caijin.vo.PageVo;
import com.kdkj.caijin.vo.UpdatePhoneVo;
import com.kdkj.caijin.vo.UsersVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author lin
 * @create 2018-04-02 14:03
 **/
@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private FilesService filesService;
    @Autowired
    private RoleService roleService;
    //    @RequiresAuthentication
    @GetMapping("/findAll")
//    @RequiresPermissions("")
    public Result findAll(Pageinfo pageinfo) {
        Page<Users> all = usersService.findAll(PageUtis.getPageRequest(pageinfo, Sort.Direction.ASC));
        getUsersResult(all);
        return Result.ok("成功", PageUtis.getInfoInPageinfo(all));
    }

    private void getUsersResult(Page<Users> all) {
        if (all.getContent()!=null){
            List<Users> content = all.getContent();
            content.forEach(e->{
                if (!StringUtils.isEmpty(e.getRole())){
                    Role byId = roleService.findById(e.getRole());
                    e.setRoleInfo(byId);
                }
                if (!StringUtils.isEmpty(e.getHeadurl())){
                    Files byId = null;
                    try {
                        byId = filesService.findById(e.getHeadurl());
                        e.setHeadFilesInfo(byId);
                    } catch (ErrMsgException e1) {
                        log.error(e1.getMsg());
                    }
                }
            });
        }
    }

    @GetMapping("/findByPhoneOrNickname")
    public Result findAllByPhoneOrNickname(String find,Pageinfo pageinfo){
        try {
            if (StringUtils.isEmpty(find)){
                return Result.error("查询不能为空");
            }
            Page<Users> allByPhoneOrNickname = usersService.findAllByPhoneOrNickname(find, find, PageUtis.getPageRequest(pageinfo, Sort.Direction.DESC));
            getUsersResult(allByPhoneOrNickname);
            return Result.ok("成功",PageUtis.getInfoInPageinfo(allByPhoneOrNickname));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    /**
     * 注册
     */
    @PostMapping("/register")
    public Result addUsers(@RequestBody LoginVo loginVo, HttpServletRequest request) {
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

    /**
     * 上传身份证
     */
    @PostMapping("/uploadIdcar")
    public Result uploadIdcar(String userid, MultipartFile file) {
        try {
            Files files = usersService.updateUploadIdcar(userid, file);
            return Result.ok("成功", files);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }

    }

    /**
     * 上传头像
     */
    @PostMapping("/uploadHeadurl")
    public Result uploadHeadurl(String userid, MultipartFile file) {
        try {
            Files files = usersService.updateUploadHeadurl(userid, file);
            return Result.ok("成功", files);
        } catch (Exception e) {
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
            return Result.error(e.getMessage());
        }
    }
    /**封号*/
    @PostMapping("/updateByProhibit")
    public Result updateByProhibit(String[] usersid){
        try {
            List<String> ids = Arrays.asList(usersid);
            ids.forEach(e->{
                usersService.updateByProhibit(e,UsersInfo.PROHIBIT.getCode());
            });
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    /**修改认证*/
    @PostMapping("/updateByAuthentication")
    public Result updateByAuthentication(String usersid){
        try {
            Users users = usersService.updateByAuthentication(usersid);
            return Result.ok("成功",users);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    /**解封*/
    @PostMapping("/updateByNotProhibit")
    public Result updateByNotProhibit(String[] usersid){
        try {
            List<String> ids = Arrays.asList(usersid);
            ids.forEach(e->{
                usersService.updateByProhibit(e,UsersInfo.NOT_PROHIBIT.getCode());
            });
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    /**修改角色*/
    @PostMapping("/updateUsersRole")
    public Result updateUsersRole(String[] usersid,String roleid){
        try {
            List<String> ids = Arrays.asList(usersid);
            ids.forEach(id->{
                usersService.updateByUsersRole(id,roleid);
            });
            return Result.ok("成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestBody UpdatePhoneVo updatePhoneVo, HttpServletRequest request) {
        String yzm = (String) request.getSession().getAttribute("yzm");
        if (!yzm.equals(updatePhoneVo.getYzm())) {
            return Result.error("验证码错误");
        }
        try {
            usersService.updateByPhone(updatePhoneVo.getPhone(), updatePhoneVo.getId());
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/findById")
    public Result findById(String id, HttpServletRequest request) {
        try {
            Users byId = usersService.findById(id);
            Files headurl = null;
            Files idcarurl = null;
            if (!StringUtils.isEmpty(byId.getHeadurl())) {
                headurl = filesService.findById(byId.getHeadurl());
            }
            if (!StringUtils.isEmpty(byId.getIdcarurl())) {
                idcarurl = filesService.findById(byId.getIdcarurl());
            }

            Result result = Result.ok("成功", byId);
            if (headurl!=null&&!StringUtils.isEmpty(headurl.getNewname())){
                result.put("headUrl", "/" + headurl.getNewname());
            }
            if (idcarurl!=null&&!StringUtils.isEmpty(idcarurl.getNewname())){
                result.put("idcarurl", "/" + idcarurl.getNewname());
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/updateByState")
    public Result updateByState(String[] userids){
        try {
            List<String> ids = Arrays.asList(userids);
            ids.forEach(e->{
                usersService.updateByState(e,UsersInfo.NOT_STATE.getCode());
            });
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody UsersVo usersVo) {
        try {
            usersService.updateByPwd(usersVo.getPassword(), usersVo.getId());
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
