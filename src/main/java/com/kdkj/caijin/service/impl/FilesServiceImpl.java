package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FilesServiceImpl implements FilesService {
    @Autowired
    private FilesDao filesDao;

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
}
