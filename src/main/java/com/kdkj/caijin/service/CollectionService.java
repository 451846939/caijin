package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CollectionService {
    Page<Collection> findAll(PageRequest pageRequest);

    Collection insert(Collection collection);

    int update(Collection collection) throws IllegalAccessException, InstantiationException;

    int delete(Collection collection);

    int deleteByid(String id);

    Page<Collection> findAllById(String userid, PageRequest pageRequest);
}
