package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.InformationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface InformationTypeService {
    Page<InformationType> findAll(PageRequest pageRequest);

    int insert(InformationType informationType);

    List<InformationType> findAll();

    int update(InformationType informationType) throws IllegalAccessException, InstantiationException;

    int deleteById(String id);
    int delete(InformationType informationType);
}
