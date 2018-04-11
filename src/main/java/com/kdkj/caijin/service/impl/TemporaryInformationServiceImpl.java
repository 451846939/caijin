package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.TemporaryInformationDao;
import com.kdkj.caijin.entity.TemporaryInformation;
import com.kdkj.caijin.service.TemporaryInformationService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TemporaryInformationServiceImpl implements TemporaryInformationService {
    @Autowired
    private TemporaryInformationDao temporaryInformationDao;

    @Override
    public Page<TemporaryInformation> findAll(PageRequest pageRequest) {
        return temporaryInformationDao.findAll(pageRequest);
    }

    @Override
    public int insert(TemporaryInformation information) {
        if (information != null) {
            temporaryInformationDao.save(information);
            return 1;
        }
        return 0;
    }

    @Override
    public List<TemporaryInformation> findAll() {
        return temporaryInformationDao.findAll();
    }

    @Override
    public int update(TemporaryInformation information) throws IllegalAccessException, InstantiationException {
        if (information != null) {
            TemporaryInformation oldInformation = temporaryInformationDao.findById(information.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(information, oldInformation);
            return 1;
        }
        return 0;
    }

    @Override
    public Page<TemporaryInformation> findAllByTitleContaining(String title, Pageable pageable) {
        return temporaryInformationDao.findAllByTitleContaining(title, pageable);
    }

    @Override
    public int deleteById(String id) {
        if (!StringUtils.isEmpty(id)) {
            temporaryInformationDao.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(TemporaryInformation information) {
        if (information != null) {
            temporaryInformationDao.delete(information);
            return 1;
        }
        return 0;
    }

    @Override
    public TemporaryInformation findById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ErrMsgException("id不能为空");
        }
        Optional<TemporaryInformation> byId = temporaryInformationDao.findById(id);
        if (!byId.isPresent()) {
            throw new ErrMsgException("id");
        }
        return byId.get();
    }

    @Override
    public void deleteAll() {
        temporaryInformationDao.deleteAll();
    }
}
