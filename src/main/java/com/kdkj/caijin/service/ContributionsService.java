package com.kdkj.caijin.service;

import com.kdkj.caijin.entity.Contributions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ContributionsService {
    Page<Contributions> findAll(PageRequest pageRequest);

    int insert(Contributions contributions);

    int update(Contributions contributions) throws IllegalAccessException, InstantiationException;

    int delete(Contributions contributions);
}
