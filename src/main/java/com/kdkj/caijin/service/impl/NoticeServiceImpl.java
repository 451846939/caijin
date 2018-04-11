package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.NoticeDao;
import com.kdkj.caijin.entity.Notice;
import com.kdkj.caijin.service.NoticeService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Page<Notice> findAll(PageRequest pageRequest) {
        return noticeDao.findAll(pageRequest);
    }

    @Override
    public Notice insert(Notice notice) {
        if (notice != null) {
            Notice save = noticeDao.save(notice);
            return save;
        }
        return null;
    }

    @Override
    public int update(Notice notice) throws IllegalAccessException, InstantiationException {
        if (notice != null) {
            Optional<Notice> byId = noticeDao.findById(notice.getId());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id的对象");
            }
            Notice oldNotice = byId.get();
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

    @Override
    public int deleteByid(String id) {
        if (!StringUtils.isEmpty(id)) {
            Optional<Notice> byId = noticeDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("该id不存在");
            }
            noticeDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }
}
