package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 收藏表dao
 *
 * @author lin
 * @create 19:16 2018/3/29
 * @params * @param null
 **/
@Repository
public interface CollectionDao extends JpaRepository<Collection, String> {
    Page<Collection> findByUserid(String userid, Pageable pageable);
}
