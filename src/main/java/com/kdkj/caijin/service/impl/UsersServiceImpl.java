package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.service.UsersService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public Page<Users> findAll(PageRequest pageRequest) {
        return usersDao.findAll(pageRequest);
    }

    @Override
    public int insert(Users users) {
        if (users != null) {
            usersDao.save(users);
            return 1;
        }
        return 0;
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
}
