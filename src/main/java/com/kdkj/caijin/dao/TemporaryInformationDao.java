package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.TemporaryInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 信息表
 *
 * @author lin
 * @create 19:23 2018/3/29
 * @params * @param null
 **/
@Repository
public interface TemporaryInformationDao extends JpaRepository<TemporaryInformation, String> {
    Page<TemporaryInformation> findAllByTitleContaining(String title, Pageable pageable);

    List<TemporaryInformation> findByType(String type);

    Page<TemporaryInformation> findByRecommend(Integer recommend, Pageable pageable);
}
