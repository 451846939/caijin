package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.util.CopyObj;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class FilesServiceImpl implements FilesService {
    @Autowired
    private FilesDao filesDao;
    @Value("${web.multipart-path}")
    private String path;
    @Override
    public Page<Files> findAll(PageRequest pageRequest) {

        return filesDao.findAll(pageRequest);
    }

    @Override
    public int insert(Files files) {
        if (files != null) {
            filesDao.save(files);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Files files) throws IllegalAccessException, InstantiationException {
        if (files != null) {
            Files oldFiles = filesDao.findById(files.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(files, oldFiles);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Files files) {
        if (files != null) {
            filesDao.delete(files);
        }
        return 0;
    }

    @Override
    public Files upload(MultipartFile file) throws IOException {
        File newfile = new File(path);
        if (!newfile.exists()) {
            newfile.mkdir();
        }
        String filename = new SimpleDateFormat("yyyy-MM-ddHHmmss")
                .format(new Date()) + file.getOriginalFilename();
        Files files = new Files();
        files.setName(file.getOriginalFilename());
        files.setNewname(filename);
        files.setPath(path);
        filesDao.save(files);
        FileCopyUtils.
                copy(file.getBytes(),
                        new File(path + filename));
        return files;
    }

    @Override
    public int deleteDirAndFilesByid(String id) {
        Files files = filesDao.findById(id).get();
        File file = new File(files.getPath() + files.getNewname());
        filesDao.deleteById(files.getId());
        file.delete();
        return 1;
    }
}
