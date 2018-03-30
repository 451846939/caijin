package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.PlatformDao;
import com.kdkj.caijin.entity.Platform;
import com.kdkj.caijin.service.PlatformService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatformServiceImpl implements PlatformService {
    @Autowired
    private PlatformDao platformDao;

    @Override
    public Page<Platform> findAll(PageRequest pageRequest) {
        return platformDao.findAll(pageRequest);
    }

    @Override
    public int insert(Platform platform) {
        if (platform != null) {
            platformDao.save(platform);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Platform platform) throws IllegalAccessException, InstantiationException {
        if (platform != null) {
            Platform oldPlatform = platformDao.findById(platform.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(platform, oldPlatform);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Platform platform) {
        if (platform != null) {
            platformDao.delete(platform);
            return 1;
        }
        return 0;
    }
}
