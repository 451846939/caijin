package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Contributions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 投稿表dao
 *
 * @author lin
 * @create 19:19 2018/3/29
 * @params * @param null
 **/
@Repository
public interface ContributionsDao extends JpaRepository<Contributions, String> {
}
