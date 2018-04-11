package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.TemporaryInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TemporaryInformationService {
    Page<TemporaryInformation> findAll(PageRequest pageRequest);

    int insert(TemporaryInformation temporaryInformation);

    List<TemporaryInformation> findAll();

    int update(TemporaryInformation temporaryInformation) throws IllegalAccessException, InstantiationException;

    Page<TemporaryInformation> findAllByTitleContaining(String title, Pageable pageable);

    int deleteById(String id);

    int delete(TemporaryInformation temporaryInformation);

    TemporaryInformation findById(String id);

    void deleteAll();
}
