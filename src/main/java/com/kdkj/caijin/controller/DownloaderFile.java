package com.kdkj.caijin.controller;

import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.service.ContributionsService;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.Download;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 下载
 *
 * @author lin
 * @create 2018-04-04 1:41
 **/
@Controller
@RequestMapping("/download")
@Slf4j
public class DownloaderFile {
    @Autowired
    private UsersService usersService;
    @Autowired
    private ContributionsService contributionsService;
    @Autowired
    private FilesService filesService;
    @Value("${web.multipart-path}")
    private String path;

    @GetMapping("/users")
    public void users(HttpServletRequest request, HttpServletResponse response) {
        List<Users> all = usersService.findAll();
        Download download = new Download();
        try {
            download.down(all, request, response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping("/getContributions")
    public void getContributions(String contributionsid, HttpServletResponse response) {
        try {
            Contributions contributions = contributionsService.findById(contributionsid);
            Files files = filesService.findById(contributions.getFilepath());
            Download.download(files.getPath(), files.getNewname(), response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test")
    public void test(HttpServletResponse response) {
        try {
            Download.download(path, "2018-03-3118340012.png", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
