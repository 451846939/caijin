package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Files;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface FilesService {
    Page<Files> findAll(PageRequest pageRequest);

    int insert(Files files);

    int update(Files files) throws IllegalAccessException, InstantiationException;

    int delete(Files files);
}
