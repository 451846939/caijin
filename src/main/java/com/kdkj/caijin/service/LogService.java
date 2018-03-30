package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface LogService {
    int logDeleteByPrimaryKey(String id);

    int logInsert(Log record);

    Log logSelectByPrimaryKey(String id);

    Page<Log> logSelectAll(PageRequest pageRequest);

    int logUpdateByPrimaryKey(Log record) throws IllegalAccessException, InstantiationException;

    Page<Log> logSelectAllByOpTypeLike(PageRequest pageRequest, String opType);

}