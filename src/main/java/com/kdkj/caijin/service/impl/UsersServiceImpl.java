package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.UsersInfo;
import com.kdkj.caijin.service.FilesService;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import com.kdkj.caijin.vo.UsersVo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private FilesService filesService;
    @Override
    public Page<Users> findAll(PageRequest pageRequest) {
        return usersDao.findAll(pageRequest);
    }

    @Override
    public int insert(Users users) {
        if (users != null) {
            Users byStateAndPhone = usersDao.findByStateAndPhone(UsersInfo.STATE.getCode(), users.getPhone());
            if (byStateAndPhone == null) {
                usersDao.save(users);
                return 1;
            }
            throw new ErrMsgException("该用户已存在");
        }
        throw new ErrMsgException("用户不能为空");
    }

    @Override
    public int update(Users users) throws IllegalAccessException, InstantiationException {
        if (users != null) {
            if (!StringUtils.isEmpty(users.getPassword())) {
                String simpleHash = new SimpleHash("SHA-1", users.getPassword()).toString();
                users.setPassword(simpleHash);
            }
            Users oldUsers = usersDao.findById(users.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(users, oldUsers);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Users users) {
        if (users != null) {
            usersDao.delete(users);
            return 1;
        }
        return 0;
    }

    @Override
    public Users findById(String id) {
        Optional<Users> byId = usersDao.findById(id);
        try {
            return byId.get();
        } catch (NoSuchElementException e) {
            throw new ErrMsgException("该用户不存在");
        }
    }

    @Override
    public Files updateUploadIdcar(String userid, MultipartFile file) throws IOException {
        Files upload = filesService.updateUpload(file);
        Users byId = this.findById(userid);
        byId.setIdcarurl(upload.getId());
        return upload;
    }

    @Override
    public Files updateUploadHeadurl(String userid, MultipartFile file) throws IOException {
        Files upload = filesService.updateUpload(file);
        Users byId = this.findById(userid);
        byId.setHeadurl(upload.getId());
        return upload;
    }

    /**
     * 通过用户来修改
     */
    @Override
    public int updateUsersByVo(UsersVo usersVo) throws IllegalAccessException, InstantiationException {
        if (usersVo != null) {
            if (StringUtils.isEmpty(usersVo.getId())) {
                throw new ErrMsgException("id不能为空");
            }
            if (!StringUtils.isEmpty(usersVo.getPassword())) {
                String simpleHash = new SimpleHash("SHA-1", usersVo.getPassword()).toString();
                usersVo.setPassword(simpleHash);
            }
            Optional<Users> byId = usersDao.findById(usersVo.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("id不存在");
            }
            Users users = byId.get();
            CopyObj.copyObjNotNullFieldsAsObj(usersVo, users);
            return 1;
        }
        return 0;
    }

    /**
     * 修改账号（手机号）
     */
    @Override
    public int updateByPhone(String phone, String userid) {
        if (!StringUtils.isEmpty(phone)) {
            Optional<Users> byId = usersDao.findById(userid);
            if (!byId.isPresent()) {
                throw new ErrMsgException("id不存在");
            }
            Users users = byId.get();
            users.setPhone(phone);
            return 1;
        }
        return 0;
    }

    /**
     * 修改密码
     */
    @Override
    public int updateByPwd(String password, String userid) {
        if (!StringUtils.isEmpty(password)) {
            Optional<Users> byId = usersDao.findById(userid);
            if (!byId.isPresent()) {
                throw new ErrMsgException("id不存在");
            }
            Users users = byId.get();
            String simpleHash = new SimpleHash("SHA-1", password).toString();
            users.setPassword(password);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateByToken(String token, String userid) {
        if (!StringUtils.isEmpty(userid)) {
            Optional<Users> byId = usersDao.findById(userid);
            if (!byId.isPresent()) {
                throw new ErrMsgException("id不存在");
            }
            Users users = byId.get();
            users.setToken(token);
            return 1;
        }
        return 0;
    }

    @Override
    public List<Users> findAll() {
        return usersDao.findAll();
    }

    @Override
    public Users findByStateAndPhone(Integer state, String phone) {
        return usersDao.findByStateAndPhone(state, phone);
    }


}
