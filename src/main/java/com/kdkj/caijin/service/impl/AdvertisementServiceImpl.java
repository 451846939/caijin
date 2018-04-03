package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.AdvertisementDao;
import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.entity.Advertisement;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.service.AdvertisementService;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementDao advertisementDao;
    @Autowired
    private FilesService filesService;
    @Override
    public Page<Advertisement> findAll(PageRequest pageRequest) {
        return advertisementDao.findAll(pageRequest);
    }

    @Override
    public List<Advertisement> findAdvertisementAll() {
        return advertisementDao.findAll();
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
    public List<Advertisement> findByPosition(String position) {
        return advertisementDao.findByPosition(position);
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

        if (advertisement != null) {
            advertisementDao.delete(advertisement);
            return 1;
        }

        return 0;
    }

    @Override
    public int updateHaveFiles(Advertisement advertisement, MultipartFile file) throws IllegalAccessException, InstantiationException, IOException {
        if (advertisement != null) {
            Advertisement oldadvertisement = advertisementDao.findById(advertisement.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(advertisement, oldadvertisement);
            Files upload = filesService.upload(file);
            oldadvertisement.setFileid(upload.getId());
            return 1;
        }
        return 0;
    }

}
