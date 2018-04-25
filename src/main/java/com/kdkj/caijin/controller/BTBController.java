package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.BTBInfo;
import com.kdkj.caijin.service.impl.BTBInfoServiceImpl;
import com.kdkj.caijin.util.Result;
import com.kdkj.caijin.vo.BtbVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public Result findAllByDate(BtbVo btbVo) {
        if (btbVo.getStarttime() == null || btbVo.getEndtime() == null) {
            return Result.error("开始时间和结束时间不能为空");
        }
        return Result.ok("成功", btbInfoService.findAllBytime(btbVo.getStarttime(), btbVo.getEndtime()));
    }

    @GetMapping("/findAllByTypeAndDate")
    public Result findAllByTypeAndDate(BtbVo btbVo) {
        if (btbVo.getStarttime() == null || btbVo.getEndtime() == null || StringUtils.isEmpty(btbVo.getType())) {
            return Result.error("类型以及开始时间和结束时间不能为空");
        }
        List<BTBInfo> byTypeBytime = btbInfoService.findByTypeBytime(btbVo.getType(), btbVo.getStarttime(), btbVo.getEndtime());
        Optional<BTBInfo> max = byTypeBytime.stream().sorted((b1, b2) -> {
            String buy1 = b1.getBuy();
            String buy2 = b2.getBuy();
            Double v1 = Double.parseDouble(buy1);
            Double v2 = Double.parseDouble(buy2);
            return v2.compareTo(v1);
        }).findFirst();
        Optional<BTBInfo> min = byTypeBytime.stream().sorted((b1, b2) -> {
            String buy1 = b1.getBuy();
            String buy2 = b2.getBuy();
            Double v1 = Double.parseDouble(buy1);
            Double v2 = Double.parseDouble(buy2);
            return v1.compareTo(v2);
        }).findFirst();
        if (max.isPresent()&&min.isPresent()){
            return Result.ok("成功", byTypeBytime).put("max",max.get().getBuy()).put("min",min.get().getBuy());
        }
        return Result.ok("成功",byTypeBytime).put("max",10000);
    }
}
