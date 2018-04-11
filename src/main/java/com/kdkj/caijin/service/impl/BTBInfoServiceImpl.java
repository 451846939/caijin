package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.BTBInfoDao;
import com.kdkj.caijin.entity.BTBInfo;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author lin
 * @create 2018-04-11 13:47
 **/
@Service
@Transactional
public class BTBInfoServiceImpl {
    @Autowired
    private BTBInfoDao btbInfoDao;

    public int insert(BTBInfo btbInfo) {
        if (btbInfo != null) {
            btbInfoDao.save(btbInfo);
            return 1;
        }
        return 0;
    }

    public int update(BTBInfo btbInfo) throws IllegalAccessException, InstantiationException {
        if (btbInfo != null) {
            if (StringUtils.isEmpty(btbInfo.getId())) {
                throw new ErrMsgException("id不能为空");
            }
            Optional<BTBInfo> byId = btbInfoDao.findById(btbInfo.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            BTBInfo oldBtbInfo = byId.get();
            CopyObj.copyObjNotNullFieldsAsObj(btbInfo, oldBtbInfo);

            return 1;
        }
        throw new ErrMsgException("对象不能为空");
    }

    public int saveAll(List<BTBInfo> list) {
        btbInfoDao.saveAll(list);
        return 1;
    }

    public Page<BTBInfo> findAll(Pageable pageable) {
        return findAll(pageable);
    }

    public List<BTBInfo> findNewAll() {
        return btbInfoDao.findAllByNew();
    }

    public List<BTBInfo> findAllBytime(Date startDate, Date endDate) {
        return btbInfoDao.findAllByDateAfterAndDateBefore(startDate, endDate);
    }

    public List<BTBInfo> findByTypeBytime(String type, Date startDate, Date entDate) {
        return btbInfoDao.findByTypeAndDateAfterAndDateBefore(type, startDate, entDate);
    }
}
