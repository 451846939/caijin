package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.AdvertisementDao;
import com.kdkj.caijin.entity.Advertisement;
import com.kdkj.caijin.service.AdvertisementService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementDao advertisementDao;

    @Override
    public Page<Advertisement> findAll(PageRequest pageRequest) {
        return advertisementDao.findAll(pageRequest);
    }

    @Override
    public int insert(Advertisement advertisement) {
        if (advertisement != null) {
            advertisementDao.save(advertisement);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Advertisement advertisement) throws IllegalAccessException, InstantiationException {
        if (advertisement != null) {
            Advertisement oldAdvertisement = advertisementDao.findById(advertisement.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(advertisement, oldAdvertisement);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Advertisement advertisement) {
        return 0;
    }
}
