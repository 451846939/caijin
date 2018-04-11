package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RoleService {
    Page<Role> findAll(PageRequest pageRequest);

    Role insert(Role role);

    int update(Role role) throws IllegalAccessException, InstantiationException;

    int delete(Role role);

    List<Role> findAll();

    int deleteById(String id);

    Role findById(String id);
}
