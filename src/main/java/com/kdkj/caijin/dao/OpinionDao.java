package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 意见回复
 *
 * @author lin
 * @create 19:26 2018/3/29
 * @params * @param null
 **/
@Repository
public interface OpinionDao extends JpaRepository<Opinion, String> {
    Page<Opinion> findByUseridAndAnswer(String userid, Integer answer, Pageable pageable);
}
