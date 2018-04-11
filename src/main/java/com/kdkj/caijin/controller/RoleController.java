package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Role;
import com.kdkj.caijin.service.RoleService;
import com.kdkj.caijin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public Result addRole(@RequestBody Role role) {
        Role insert = roleService.insert(role);
        return Result.ok("成功", insert);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Role role) {
        try {
            roleService.update(role);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public Result findAll() {
        return Result.ok("成功", roleService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            roleService.deleteById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
