package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface InformationService {
    Page<Information> findAll(PageRequest pageRequest);

    int insert(Information information);

    int update(Information information) throws IllegalAccessException, InstantiationException;

    int delete(Information information);
}
