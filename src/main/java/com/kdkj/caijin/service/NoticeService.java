package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface NoticeService {
    Page<Notice> findAll(PageRequest pageRequest);

    int insert(Notice notice);

    int update(Notice notice) throws IllegalAccessException, InstantiationException;

    int delete(Notice notice);
}
