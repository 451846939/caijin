package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AdvertisementService {
    Page<Advertisement> findAll(PageRequest pageRequest);

    int insert(Advertisement advertisement);

    int update(Advertisement advertisement) throws IllegalAccessException, InstantiationException;

    int delete(Advertisement advertisement);

}
