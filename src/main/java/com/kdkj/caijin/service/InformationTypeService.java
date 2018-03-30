package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.InformationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface InformationTypeService {
    Page<InformationType> findAll(PageRequest pageRequest);

    int insert(InformationType informationType);

    int update(InformationType informationType) throws IllegalAccessException, InstantiationException;

    int delete(InformationType informationType);
}
