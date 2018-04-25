package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InformationService {
    Page<Information> findAll(PageRequest pageRequest);

    int insert(Information information,MultipartFile file) throws IOException;

    int insert(Information information);

    List<Information> findAll();

    int update(Information information) throws IllegalAccessException, InstantiationException;

    Page<Information> findAllByTitleContaining(String title, Pageable pageable);

    int deleteById(String id);

    int delete(Information information);

    List<Information> findBySourceNotNull();

    Page<Information> findByType(String type,Pageable pageable);

    Page<Information> findByKeyword(String keyword,Pageable pageable);

    int insertAll(List<Information> list);

    Information findById(String id);

    Page<Information> findByRecommend(Integer recommend,Pageable pageable);
}
