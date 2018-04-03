package com.kdkj.caijin.controller;

import com.kdkj.caijin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色
 *
 * @author lin
 * @create 2018-04-02 17:51
 **/
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

}
