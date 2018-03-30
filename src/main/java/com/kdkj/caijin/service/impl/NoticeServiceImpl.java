package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.NoticeDao;
import com.kdkj.caijin.entity.Notice;
import com.kdkj.caijin.service.NoticeService;
import com.kdkj.caijin.util.CopyObj;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    private NoticeDao noticeDao;

    @Override
    public Page<Notice> findAll(PageRequest pageRequest) {
        return noticeDao.findAll(pageRequest);
    }

    @Override
    public int insert(Notice notice) {
        if (notice != null) {
            noticeDao.save(notice);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(Notice notice) throws IllegalAccessException, InstantiationException {
        if (notice != null) {
            Notice oldNotice = noticeDao.findById(notice.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(notice, oldNotice);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Notice notice) {
        if (notice != null) {
            noticeDao.delete(notice);
            return 1;
        }
        return 0;
    }
}
