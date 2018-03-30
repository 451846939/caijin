package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.InformationTypeDao;
import com.kdkj.caijin.entity.InformationType;
import com.kdkj.caijin.service.InformationTypeService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InformationTypeServiceImpl implements InformationTypeService {
    @Autowired
    private InformationTypeDao informationTypeDao;

    @Override
    public Page<InformationType> findAll(PageRequest pageRequest) {
        return informationTypeDao.findAll(pageRequest);
    }

    @Override
    public int insert(InformationType informationType) {
        if (informationType != null) {
            informationTypeDao.save(informationType);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(InformationType informationType) throws IllegalAccessException, InstantiationException {
        if (informationType != null) {
            InformationType oldInformationType = informationTypeDao.findById(informationType.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(informationType, oldInformationType);
            return 1;
        }

        return 0;
    }

    @Override
    public int delete(InformationType informationType) {
        if (informationType != null) {
            informationTypeDao.delete(informationType);
            return 1;
        }
        return 0;
    }
}
