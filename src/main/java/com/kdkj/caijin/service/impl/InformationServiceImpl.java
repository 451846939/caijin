package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.dao.InformationDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Information;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.InformationService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InformationServiceImpl implements InformationService {
    @Autowired
    private InformationDao informationDao;
    @Autowired
    private FilesService filesService;
    @Override
    public Page<Information> findAll(PageRequest pageRequest) {
        return informationDao.findAll(pageRequest);
    }

    @Override
    public int insert(Information information,MultipartFile file) throws IOException {
        if (information != null&&file!=null) {
            Files files = filesService.updateUpload(file);
            information.setShowpicture(files.getId());
            informationDao.save(information);
            return 1;
        }
        throw new ErrMsgException("上传数据或者文件不能为空");
    }

    @Override
    public int insert(Information information) {
        if (information!=null){
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
            Optional<Information> byId = informationDao.findById(information.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("不存在该id");
            }
            Information oldInformation = byId.get();
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
            Optional<Information> byId = informationDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("不存在该id");
            }
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

    @Override
    public List<Information> findBySourceNotNull() {
        return informationDao.findBySourceNotNull();
    }

    @Override
    public Page<Information> findByType(String type, Pageable pageable) {
        return informationDao.findByType(type,pageable);
    }

    @Override
    public Page<Information> findByKeyword(String keyword, Pageable pageable) {
        if (StringUtils.isEmpty(keyword)){
            throw new ErrMsgException("关键字不能为空");
        }

        return  informationDao.findAllByKeywordContaining(keyword ,pageable);
    }

    @Override
    public int insertAll(List<Information> list) {
        informationDao.saveAll(list);
        return 1;
    }

    @Override
    public Information findById(String id) {
        Optional<Information> byId = informationDao.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    @Override
    public Page<Information> findByRecommend(Integer recommend, Pageable pageable) {
        if (recommend!=null){
            return informationDao.findByRecommend(recommend,pageable);
        }
        return null;
    }
}
