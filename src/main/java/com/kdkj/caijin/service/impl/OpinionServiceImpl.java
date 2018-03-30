package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.OpinionDao;
import com.kdkj.caijin.entity.Opinion;
import com.kdkj.caijin.service.OpinionService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OpinionServiceImpl implements OpinionService {
    @Autowired
    private OpinionDao opinionDao;

    @Override
    public Page<Opinion> findAll(PageRequest pageRequest) {
        return opinionDao.findAll(pageRequest);
    }

    @Override
    public int insert(Opinion opinion) {
        if (opinion != null) {
            opinionDao.save(opinion);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Opinion opinion) throws IllegalAccessException, InstantiationException {
        if (opinion != null) {
            Opinion oldOpinion = opinionDao.findById(opinion.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(opinion, oldOpinion);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Opinion opinion) {
        if (opinion != null) {
            opinionDao.delete(opinion);
            return 1;
        }
        return 0;
    }
}
