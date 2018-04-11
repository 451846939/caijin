package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Platform;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.PlatformService;
import com.kdkj.caijin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 平台管理
 *
 * @author lin
 * @create 2018-04-03 18:06
 **/
@RestController
@RequestMapping("/platform")
public class PlatformController {
    @Autowired
    private PlatformService platformService;

    @GetMapping("/find")
    public Result findAll() {
        List<Platform> all = platformService.findAll();
        if (all.size() == 1) {
            return Result.ok("成功", all);
        }
        return Result.error("数据异常");
    }

    @PostMapping("/add")
    public Result add(@RequestBody Platform platform) {
        platformService.insert(platform);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Platform platform) {
        try {
            platform.setFileurl(null);
            platformService.update(platform);
            return Result.ok("成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/uploadPlatfrom")
    public Result uploadPlatfrom(MultipartFile file) {
        try {
            Platform platform = platformService.updateUploadPlatform(file);
            return Result.ok("成功", platform);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
