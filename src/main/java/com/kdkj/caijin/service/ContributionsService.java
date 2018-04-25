package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Contributions;
import com.kdkj.caijin.vo.ContributionsVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContributionsService {
    Page<Contributions> findAll(PageRequest pageRequest);

    int insert(Contributions contributions);

    int update(Contributions contributions) throws IllegalAccessException, InstantiationException;

    int deleteById(String id) throws IOException;

    Integer countByAdopt(Integer adopt);

    Contributions insertContributionsAndFile(Contributions contributions, MultipartFile multipartFile, String path) throws IOException;

    int delete(Contributions contributions);

    Contributions findById(String id);

    List<ContributionsVo> findByUserid(String userid, Pageable pageable);
}
