package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.InformationDao;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;

    @Override
    public Page<Information> findAll(PageRequest pageRequest) {
        return informationDao.findAll(pageRequest);
    }

    @Override
    public int insert(Information information) {
        if (information != null) {
            informationDao.save(information);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Information> findAll() {
        return informationDao.findAll();
    }

    @Override
    public int update(Information information) throws IllegalAccessException, InstantiationException {
        if (information != null) {
            Information oldInformation = informationDao.findById(information.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(information, oldInformation);
            return 1;
        }
        return 0;
    }

    @Override
    public Page<Information> findAllByTitleContaining(String title, Pageable pageable) {
        return informationDao.findAllByTitleContaining(title, pageable);
    }

    @Override
    public int deleteById(String id) {
        if (!StringUtils.isEmpty(id)) {
            informationDao.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Information information) {
        if (information != null) {
            informationDao.delete(information);
            return 1;
        }
        return 0;
    }
}
