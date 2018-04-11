package com.kdkj.caijin.controller;

import com.kdkj.caijin.service.impl.BTBInfoServiceImpl;
import com.kdkj.caijin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * btb
 *
 * @author lin
 * @create 2018-04-11 15:04
 **/
@RestController
@RequestMapping("/btbinfo")
public class BTBController {
    @Autowired
    private BTBInfoServiceImpl btbInfoService;

    @GetMapping("/findNew")
    public Result findNew() {
        return Result.ok("成功", btbInfoService.findNewAll());
    }

    @GetMapping("/findAllByDate")
    public Result findAllByDate(Date starttime, Date endtime) {
        if (starttime == null || endtime == null) {
            return Result.error("开始时间和结束时间不能为空");
        }
        return Result.ok("成功", btbInfoService.findAllBytime(starttime, endtime));
    }

    @GetMapping("/findAllByTypeAndDate")
    public Result findAllByDate(String type, Date starttime, Date endtime) {
        if (starttime == null || endtime == null || StringUtils.isEmpty(type)) {
            return Result.error("类型以及开始时间和结束时间不能为空");
        }
        return Result.ok("成功", btbInfoService.findAllBytime(starttime, endtime));
    }
}
