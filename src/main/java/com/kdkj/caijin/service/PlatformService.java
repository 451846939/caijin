package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PlatformService {
    Page<Platform> findAll(PageRequest pageRequest);

    int insert(Platform platform);

    int update(Platform platform) throws IllegalAccessException, InstantiationException;

    int delete(Platform platform);

    List<Platform> findAll();

    Platform updateUploadPlatform(MultipartFile file) throws IOException;
}
