package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.RoleDao;
import com.kdkj.caijin.entity.Role;
import com.kdkj.caijin.service.RoleService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public Page<Role> findAll(PageRequest pageRequest) {
        return roleDao.findAll(pageRequest);
    }

    @Override
    public int insert(Role role) {
        if (role != null) {
            roleDao.save(role);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Role role) throws IllegalAccessException, InstantiationException {
        if (role != null) {
            Role oldRole = roleDao.findById(role.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(role, oldRole);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Role role) {
        if (role != null) {
            roleDao.delete(role);
            return 1;
        }
        return 0;
    }
}
