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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Files uploadIdcar(String userid, MultipartFile file) throws IOException {
        Files upload = filesService.upload(file);
        Users byId = this.findById(userid);
        byId.setIdcarurl(upload.getId());
        return upload;
    }

    @Override
    public Files uploadHeadurl(String userid, MultipartFile file) throws IOException {
        Files upload = filesService.upload(file);
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
            Users users = usersDao.findById(usersVo.getId()).get();
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
            Users users = usersDao.findById(userid).get();
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
            Users users = usersDao.findById(userid).get();
            users.setPassword(password);
            return 1;
        }
        return 0;
    }

}
