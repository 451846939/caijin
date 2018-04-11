package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Contributions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ContributionsService {
    Page<Contributions> findAll(PageRequest pageRequest);

    int insert(Contributions contributions);

    int update(Contributions contributions) throws IllegalAccessException, InstantiationException;

    int deleteById(String id) throws IOException;

    Integer countByAdopt(Integer adopt);

    Contributions insertContributionsAndFile(Contributions contributions, MultipartFile multipartFile, String path) throws IOException;

    int delete(Contributions contributions);

    Contributions findById(String id);
}
