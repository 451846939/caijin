package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.RoleDao;
import com.kdkj.caijin.entity.Role;
import com.kdkj.caijin.service.RoleService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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
    public Role insert(Role role) {
        if (role != null) {
            Role save = roleDao.save(role);
            return save;
        }
        return null;
    }

    @Override
    public int update(Role role) throws IllegalAccessException, InstantiationException {
        if (role != null) {
            if (StringUtils.isEmpty(role.getId())) {
                throw new ErrMsgException("id不能为空");
            }
            Optional<Role> byId = roleDao.findById(role.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("该id不存在");
            }
            Role oldRole = byId.get();
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

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public int deleteById(String id) {
        if (!StringUtils.isEmpty(id)) {
            Optional<Role> byId = roleDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("该id不存在");
            }
            roleDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }
}
