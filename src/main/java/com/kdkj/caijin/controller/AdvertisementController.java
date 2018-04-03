package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Advertisement;
import com.kdkj.caijin.entity.Pageinfo;
import com.kdkj.caijin.service.AdvertisementService;
import com.kdkj.caijin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 广告
 *
 * @author lin
 * @create 2018-04-03 9:54
 **/
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<Advertisement> advertisementAll = advertisementService.findAdvertisementAll();
        return Result.ok("成功", advertisementAll);
    }

    @GetMapping("/findByPosition")
    public Result findByPosition(String position) {
        List<Advertisement> byPosition = advertisementService.findByPosition(position);
        return Result.ok("成功", byPosition);
    }

    @PostMapping("/add")
    public Result addAdvertisement(Advertisement advertisement) {
        advertisementService.insert(advertisement);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateAdvertisement(Advertisement advertisement, MultipartFile file) {
        try {
            advertisementService.updateHaveFiles(advertisement, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}
