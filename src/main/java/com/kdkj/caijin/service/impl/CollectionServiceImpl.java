package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.CollectionDao;
import com.kdkj.caijin.entity.Collection;
import com.kdkj.caijin.service.CollectionService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;

    @Override
    public Page<Collection> findAll(PageRequest pageRequest) {
        return collectionDao.findAll(pageRequest);
    }

    @Override
    public int insert(Collection collection) {
        if (collection != null) {
            collectionDao.save(collection);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Collection collection) throws IllegalAccessException, InstantiationException {
        if (collection != null) {
            Collection oldCollection = collectionDao.findById(collection.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(collection, oldCollection);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Collection collection) {
        if (collection != null) {
            collectionDao.delete(collection);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteByid(String id) {
        if (!StringUtils.isEmpty(id)) {
            collectionDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }
}
