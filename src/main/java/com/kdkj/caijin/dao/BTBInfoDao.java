package com.kdkj.caijin.dao;

import com.kdkj.caijin.entity.BTBInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * btb信息
 *
 * @author lin
 * @create 2018-04-11 13:45
 **/
@Repository
public interface BTBInfoDao extends JpaRepository<BTBInfo, String> {
    @Query("select c from BTBInfo c group by c.type order by c.date DESC ")
    List<BTBInfo> findAllByNew();

    List<BTBInfo> findAllByDateAfterAndDateBefore(Date dateAfter, Date dateBefore);

    List<BTBInfo> findByTypeAndDateAfterAndDateBefore(String type, Date dateAfter, Date dateBefore);
}
