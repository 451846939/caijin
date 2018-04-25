package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.InformationType;
import com.kdkj.caijin.service.InformationTypeService;
import com.kdkj.caijin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 新闻（资讯）分类，类别
 *
 * @author lin
 * @create 2018-03-31 1:00
 **/
@RestController
@RequestMapping("/informationType")
public class InformationTypeController {
    @Autowired
    private InformationTypeService informationTypeService;

    @GetMapping("/findAll")
    public Result finAll() {
        return Result.ok("成功", informationTypeService.findAll());
    }

    @PostMapping("/addInformationType")
    public Result addInformationType(@RequestBody String name) {
        try {
            if (!StringUtils.isEmpty(name)) {
                InformationType informationType = new InformationType();
                informationType.setType(name);
                informationTypeService.insert(informationType);
                return Result.ok("成功", informationType);
            }
            return Result.error(500,"类型名字不能为空");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/updateInformationType")
    public Result update(@RequestBody InformationType informationType) {
        if (informationType != null) {
            try {
                informationTypeService.update(informationType);
                return Result.ok();
            } catch (Exception e) {
                return Result.error(e.getMessage());
            }
        }
        return Result.error();
    }

    @DeleteMapping("/deleteInformateionType/{id}")
    public Result delete(@PathVariable("id") String id) {
        try {
            informationTypeService.deleteById(id);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}