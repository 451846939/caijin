package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface HistoryService {
    Page<History> findAll(PageRequest pageRequest);

    int insert(History history);

    int update(History history) throws IllegalAccessException, InstantiationException;

    int delete(History history);
}
