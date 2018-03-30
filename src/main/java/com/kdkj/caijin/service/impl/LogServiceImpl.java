package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.LogDao;
import com.kdkj.caijin.entity.Log;
import com.kdkj.caijin.service.LogService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logMapper;

    @Override
    public int logDeleteByPrimaryKey(String id) {
        logMapper.deleteById(id);
        return 1;
    }

    @Override
    public int logInsert(Log record) {
        logMapper.save(record);
        return 1;
    }

    @Override
    public Log logSelectByPrimaryKey(String id) {
        Optional<Log> byId = logMapper.findById(id);
        return byId.get();
    }

    @Override
    public Page<Log> logSelectAll(PageRequest pageRequest) {
        Page<Log> all = logMapper.findAll(pageRequest);
        return logMapper.findAll(pageRequest);
    }

    //	@Modifying
    @Override
    public int logUpdateByPrimaryKey(Log record) throws IllegalAccessException, InstantiationException {
        Optional<Log> byId = logMapper.findById(record.getId());
        Log log = byId.get();
        CopyObj.copyObjNotNullFieldsAsObj(record, log);
        logMapper.save(log);
        return 1;
    }

    @Override
    public Page<Log> logSelectAllByOpTypeLike(PageRequest pageRequest, String opType) {

        return logMapper.findAllByOpTypeContaining(opType, pageRequest);
    }


}
