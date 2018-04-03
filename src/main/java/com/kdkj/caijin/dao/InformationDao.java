package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Information;
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
public interface InformationDao extends JpaRepository<Information, String> {
    Page<Information> findAllByTitleContaining(String title, Pageable pageable);

    List<Information> findByType(String type);

    Page<Information> findByRecommend(Integer recommend, Pageable pageable);
}
