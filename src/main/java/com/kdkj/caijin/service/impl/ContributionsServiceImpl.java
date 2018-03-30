package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.ContributionsDao;
import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.service.ContributionsService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContributionsServiceImpl implements ContributionsService {
    @Autowired
    private ContributionsDao contributionsDao;

    @Override
    public Page<Contributions> findAll(PageRequest pageRequest) {
        return contributionsDao.findAll(pageRequest);
    }

    @Override
    public int insert(Contributions contributions) {
        if (contributions != null) {
            contributionsDao.save(contributions);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Contributions contributions) throws IllegalAccessException, InstantiationException {
        if (contributions != null) {
            Contributions oldContributions = contributionsDao.findById(contributions.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(contributions, oldContributions);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Contributions contributions) {
        if (contributions != null) {
            contributionsDao.delete(contributions);
            return 1;
        }
        return 0;
    }
}
