package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.InformationDao;
import com.kdkj.caijin.dao.InformationTypeDao;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.entity.InformationType;
import com.kdkj.caijin.service.InformationTypeService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InformationTypeServiceImpl implements InformationTypeService {
    @Autowired
    private InformationTypeDao informationTypeDao;
    @Autowired
    private InformationDao informationDao;
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
    public List<InformationType> findAll() {
        return informationTypeDao.findAll();
    }

    @Override
    public int update(InformationType informationType) throws IllegalAccessException, InstantiationException {
        if (informationType != null) {
            Optional<InformationType> byId = informationTypeDao.findById(informationType.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            InformationType oldInformationType = byId.get();
            CopyObj.copyObjNotNullFieldsAsObj(informationType, oldInformationType);
            return 1;
        }

        return 0;
    }

    @Override
    public int deleteById(String id) {
        if (!StringUtils.isEmpty(id)) {
            Optional<InformationType> byId = informationTypeDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            informationTypeDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }

    @Override
    public int delete(InformationType informationType) {
        if (informationType != null) {
            List<Information> byType = informationDao.findByType(informationType.getId());
            if (byType.isEmpty()) {
                informationTypeDao.delete(informationType);
                return 1;
            }
            throw new ErrMsgException("对不起还存在该类型的资讯,无法删除");
        }
        return 0;
    }
}
