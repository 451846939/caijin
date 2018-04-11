package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Files;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.vo.UsersVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsersService {
    Page<Users> findAll(PageRequest pageRequest);

    int insert(Users users);

    int update(Users users) throws IllegalAccessException, InstantiationException;

    int delete(Users users);

    Users findById(String id);

    Files updateUploadIdcar(String userid, MultipartFile file) throws IOException;

    Files updateUploadHeadurl(String userid, MultipartFile file) throws IOException;

    int updateUsersByVo(UsersVo usersVo) throws IllegalAccessException, InstantiationException;

    int updateByPhone(String phone, String userid);

    int updateByPwd(String password, String userid);

    int updateByToken(String token, String userid);

    List<Users> findAll();

    Users findByStateAndPhone(Integer state, String phone);
}
