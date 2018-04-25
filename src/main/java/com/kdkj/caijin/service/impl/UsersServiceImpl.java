package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.RoleDao;
import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Role;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
    @Autowired
    private RoleDao roleDao;
    @Override
    public Page<Users> findAll(PageRequest pageRequest) {
        return usersDao.findByState(UsersInfo.STATE.getCode(),pageRequest);
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
        if (!byId.isPresent()) {
            throw new ErrMsgException("该用户不存在");
        }
        return byId.get();
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
        if (StringUtils.isEmpty(userid)) {
            throw new ErrMsgException("用户id不能为空");
        }
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
            Users users = checkHaveUser(usersVo.getId());
            CopyObj.copyObjNotNullFieldsAsObj(usersVo, users);
            return 1;
        }
        throw new ErrMsgException("用户信息不能为空");
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
            Users byStateAndPhone = usersDao.findByStateAndPhone(UsersInfo.STATE.getCode(), phone);
            if (byStateAndPhone != null) {
                throw new ErrMsgException("该用户已经存在");
            }
            Users users = byId.get();
            users.setPhone(phone);
            return 1;
        }
        throw new ErrMsgException("电话号码不能为空");
    }

    /**
     * 修改密码
     */
    @Override
    public int updateByPwd(String password, String userid) {
        if (!StringUtils.isEmpty(password)&&!StringUtils.isEmpty(userid)) {
            Users users = checkHaveUser(userid);
            String simpleHash = new SimpleHash("SHA-1", password).toString();
            users.setPassword(password);
            return 1;
        }
        throw new ErrMsgException("密码或者用户id不能为空");
    }

    @Override
    public int updateByToken(String token, String userid) {
        if (!StringUtils.isEmpty(userid)) {
            Users users = checkHaveUser(userid);
            users.setToken(token);
            return 1;
        }
        throw new ErrMsgException("用户id不能为空");
    }

    @Override
    public List<Users> findAll() {
        return usersDao.findAll();
    }

    @Override
    public int updateByUpdatetime(Date date, String userid) {
        if (!StringUtils.isEmpty(userid)) {
            Users users = checkHaveUser(userid);
            users.setUpdatetime(date);
            return 1;
        }
        throw new ErrMsgException("用户id不能为空");
    }

    private Users checkHaveUser(String userid) {
        Optional<Users> byId = usersDao.findById(userid);
        if (!byId.isPresent()) {
            throw new ErrMsgException("id不存在");
        }
        return byId.get();
    }

    @Override
    public int updateByUsersRole(String userid, String roleid) {
        if (StringUtils.isEmpty(userid)||StringUtils.isEmpty(roleid)){
            throw new ErrMsgException("用户id或角色id不能为空");
        }
        Users users = checkHaveUser(userid);
        Optional<Role> byId = roleDao.findById(roleid);
        if (byId.isPresent()){
            users.setRole(byId.get().getId());
            return 1;
        }
        throw new ErrMsgException("没有该角色添加失败");
    }

    @Override
    public Users findByStateAndPhone(Integer state, String phone) {
        return usersDao.findByStateAndPhone(state, phone);
    }

    @Override
    public Users updateByProhibit(String userid,Integer prohibit) {
        Users users = checkHaveUser(userid);
        users.setProhibit(prohibit);
        return users;
    }

    @Override
    public Users updateByAuthentication(String userid) {
        Users users = checkHaveUser(userid);
        Integer authentication = users.getAuthentication();
        if (authentication !=null){
            if (authentication==UsersInfo.NOT_AUTHENTICATION.getCode()){
                users.setAuthentication(UsersInfo.AUTHENTICATION.getCode());
            }
            if (authentication==UsersInfo.AUTHENTICATION.getCode()){
                users.setAuthentication(UsersInfo.NOT_AUTHENTICATION.getCode());
            }
        }
        return users;
    }

    @Override
    public Users updateByState(String userid, Integer state) {
        Users users = checkHaveUser(userid);
        users.setState(state);
        return users;
    }

    @Override
    public Page<Users> findAllByPhoneOrNickname(String phone, String nickname, Pageable pageable) {
        return usersDao.findByPhoneContainingOrNicknameContainingAndState(phone,nickname,UsersInfo.STATE.getCode(),pageable);
    }


}
