package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdvertisementService {
    Page<Advertisement> findAll(PageRequest pageRequest);

    List<Advertisement> findAdvertisementAll();
    int insert(Advertisement advertisement);

    List<Advertisement> findByPosition(String position);
    int update(Advertisement advertisement) throws IllegalAccessException, InstantiationException;

    int delete(Advertisement advertisement);

    int updateHaveFiles(Advertisement advertisement, MultipartFile file) throws IllegalAccessException, InstantiationException, IOException;
}
