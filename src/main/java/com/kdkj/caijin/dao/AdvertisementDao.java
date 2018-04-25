package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 广告表dao
 *
 * @author lin
 * @create 2018-03-29 19:12
 **/
@Repository
public interface AdvertisementDao extends JpaRepository<Advertisement, String> {
    List<Advertisement> findByPositionAndState(String position,Integer state);
}
