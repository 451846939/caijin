package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.PlatformDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Platform;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.PlatformService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlatformServiceImpl implements PlatformService {
    @Autowired
    private PlatformDao platformDao;
    @Autowired
    private FilesService filesService;
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
            if (StringUtils.isEmpty(platform.getId())) {
                throw new ErrMsgException("id不能为空");
            }
            Optional<Platform> byId = platformDao.findById(platform.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            Platform oldPlatform = byId.get();
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

    @Override
    public List<Platform> findAll() {
        return platformDao.findAll();
    }

    @Override
    public Platform updateUploadPlatform(MultipartFile file) throws IOException {
        Files files = filesService.updateUpload(file);
        List<Platform> all = findAll();
        if (all.size() == 1) {
            Platform platform = platformDao.findById(all.get(0).getId()).get();
            platform.setFileurl(files.getId());
            return platform;
        }
        throw new ErrMsgException("数据异常");
    }
}
