package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.HistoryDao;
import com.kdkj.caijin.entity.History;
import com.kdkj.caijin.service.HistoryService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
    private HistoryDao historyDao;

    @Override
    public Page<History> findAll(PageRequest pageRequest) {
        return historyDao.findAll(pageRequest);
    }

    @Override
    public int insert(History history) {
        if (history != null) {
            historyDao.save(history);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(History history) throws IllegalAccessException, InstantiationException {
        if (history != null) {
            History oldHistory = historyDao.findById(history.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(history, oldHistory);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(History history) {
        if (history != null) {
            historyDao.delete(history);
            return 1;
        }
        return 0;
    }
}
