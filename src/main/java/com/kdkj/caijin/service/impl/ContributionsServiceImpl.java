package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.ContributionsDao;
import com.kdkj.caijin.dao.FilesDao;
import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.enums.ContributionsAdopt;
import com.kdkj.caijin.service.ContributionsService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import com.kdkj.caijin.vo.ContributionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContributionsServiceImpl implements ContributionsService {
    @Autowired
    private ContributionsDao contributionsDao;
    @Autowired
    private FilesDao filesDao;
    @Autowired
    private UsersDao usersDao;
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
            Optional<Contributions> byId = contributionsDao.findById(contributions.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            Contributions oldContributions = byId.get();

            CopyObj.copyObjNotNullFieldsAsObj(contributions, oldContributions);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(String id) throws IOException {
        if (!StringUtils.isEmpty(id)) {
            Optional<Contributions> byId = contributionsDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("该id不存在");
            }
            Contributions contributions = byId.get();
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
    public Contributions insertContributionsAndFile(Contributions contributions, MultipartFile multipartFile, String path) throws IOException {
        if (contributions != null && multipartFile != null) {
            if (StringUtils.isEmpty(contributions.getUserid())) {
                throw new ErrMsgException("用户id不能为空");
            }
            if (!usersDao.findById(contributions.getUserid()).isPresent()) {
                throw new ErrMsgException("该用户不存在");
            }
            contributions.setAdopt(ContributionsAdopt.WAIT.getCode());
            contributions.setCreatetime(new Date());
//            FileUtils.forceMkdirParent();
            contributions.setCreatetime(new Date());
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
            Contributions save = contributionsDao.save(contributions);
            FileCopyUtils.
                    copy(multipartFile.getBytes(),
                            new File(path + filename));
            return save;
        }
        throw new ErrMsgException("文件不能为空");
    }

    @Override
    public int delete(Contributions contributions) {
        if (contributions != null) {
            if (!contributionsDao.findById(contributions.getId()).isPresent()) {
                throw new ErrMsgException("该id不存在");
            }
            contributionsDao.delete(contributions);
            return 1;
        }
        return 0;
    }

    @Override
    public Contributions findById(String id) {
        return contributionsDao.findById(id).get();
    }

    @Override
    public List<ContributionsVo> findByUserid(String userid, Pageable pageable) {
        if (StringUtils.isEmpty(userid)){
            throw new ErrMsgException("userid不能为空");
        }
        if (!usersDao.findById(userid).isPresent()){
            throw new ErrMsgException("没有该用户");
        }
        Page<Contributions> byUserid = contributionsDao.findByUserid(userid, pageable);
        List<Contributions> content = byUserid.getContent();
        List<ContributionsVo> contributions = new ArrayList<>();
        content.forEach(e->{
            ContributionsVo contributionsVo = new ContributionsVo();
            if (!StringUtils.isEmpty(e.getFilepath())){
                Optional<Files> byId = filesDao.findById(e.getFilepath());
                if (byId.isPresent()){
                    contributionsVo.setContributions(e);
                    contributionsVo.setFile(byId.get());
                }
            }
            contributions.add(contributionsVo);
        });
        return contributions;
    }
}
