package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UsersService {
    Page<Users> findAll(PageRequest pageRequest);

    int insert(Users users);

    int update(Users users) throws IllegalAccessException, InstantiationException;

    int delete(Users users);
}
