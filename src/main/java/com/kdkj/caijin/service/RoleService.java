package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RoleService {
    Page<Role> findAll(PageRequest pageRequest);

    int insert(Role role);

    int update(Role role) throws IllegalAccessException, InstantiationException;

    int delete(Role role);
}
