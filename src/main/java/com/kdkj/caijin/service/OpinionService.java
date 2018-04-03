package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface OpinionService {
    Page<Opinion> findAll(PageRequest pageRequest);

    int insert(Opinion opinion);

    int update(Opinion opinion) throws IllegalAccessException, InstantiationException;

    int delete(Opinion opinion);

    int deleteById(String id);

    Page<Opinion> findByUseridAndAnswer(String userid, Integer answer, Pageable pageable);
}
