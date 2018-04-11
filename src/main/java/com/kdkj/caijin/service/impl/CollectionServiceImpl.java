package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.CollectionDao;
import com.kdkj.caijin.dao.InformationDao;
import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Collection;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.service.CollectionService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private InformationDao informationDao;

    @Override
    public Page<Collection> findAll(PageRequest pageRequest) {
        return collectionDao.findAll(pageRequest);
    }

    @Override
    public Collection insert(Collection collection) {
        if (collection != null) {
            if (StringUtils.isEmpty(collection.getUserid())) {
                throw new ErrMsgException("用户id不能为空");
            }
            if (StringUtils.isEmpty(collection.getInformationid())) {
                throw new ErrMsgException("信息id不能为空");
            }
            if (usersDao.findById(collection.getUserid()).isPresent() && informationDao.findById(collection.getInformationid()).isPresent()) {
                //此处有一个防止重复添加未做
                collection.setTime(new Date());
                Collection save = collectionDao.save(collection);
                return save;
            } else {
                throw new ErrMsgException("请检查传入参数用户id或者信息id是否存在");
            }
        }
        return null;
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
            if (collectionDao.findById(id).isPresent()) {
                collectionDao.deleteById(id);
                return 1;
            }
            throw new ErrMsgException("该id不存在");
        }
        throw new ErrMsgException("id不能为空");
    }

    @Override
    public Page<Collection> findAllById(String userid, PageRequest pageRequest) {
        return collectionDao.findByUserid(userid, pageRequest);
    }
}
