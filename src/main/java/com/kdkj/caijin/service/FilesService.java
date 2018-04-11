package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Files;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesService {
    Page<Files> findAll(PageRequest pageRequest);

    int insert(Files files);

    int update(Files files) throws IllegalAccessException, InstantiationException;

    int delete(Files files);

    Files updateUpload(MultipartFile file) throws IOException;

    Files findById(String id);
    int deleteDirAndFilesByid(String id);
}
