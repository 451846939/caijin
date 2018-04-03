package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.ContributionsDao;
import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.service.ContributionsService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ContributionsServiceImpl implements ContributionsService {
    @Autowired
    private ContributionsDao contributionsDao;
    @Autowired
    private FilesDao filesDao;

    @Override
    public Page<Contributions> findAll(PageRequest pageRequest) {
        return contributionsDao.findAll(pageRequest);
    }

    @Override
    public int insert(Contributions contributions) {
        if (contributions != null) {
            contributionsDao.save(contributions);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Contributions contributions) throws IllegalAccessException, InstantiationException {
        if (contributions != null) {
            Contributions oldContributions = contributionsDao.findById(contributions.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(contributions, oldContributions);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(String id) throws IOException {
        if (!StringUtils.isEmpty(id)) {
            Contributions contributions = contributionsDao.findById(id).get();
            String filepath = contributions.getFilepath();
            Files files = filesDao.findById(filepath).get();
            new File(files.getPath()).delete();
            filesDao.delete(files);
            contributionsDao.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer countByAdopt(Integer adopt) {
        List<Contributions> byAdopt = contributionsDao.findByAdopt(adopt);
        return byAdopt.size();
    }

    @Override
    public int insertContributionsAndFile(Contributions contributions, MultipartFile multipartFile, String path) throws IOException {
        if (contributions != null && multipartFile != null) {
//            FileUtils.forceMkdirParent();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String filename = new SimpleDateFormat("yyyy-MM-ddHHmmss")
                    .format(new Date()) + multipartFile.getOriginalFilename();
            Files files = new Files();
            files.setName(multipartFile.getName());
            files.setNewname(filename);
            files.setPath(path);
            filesDao.save(files);
            contributions.setFilepath(files.getId());
            contributionsDao.save(contributions);
            FileCopyUtils.
                    copy(multipartFile.getBytes(),
                            new File(path + filename));
            return 1;
        }
        throw new ErrMsgException("文件不能为空");
    }

    @Override
    public int delete(Contributions contributions) {
        if (contributions != null) {
            contributionsDao.delete(contributions);
            return 1;
        }
        return 0;
    }
}
