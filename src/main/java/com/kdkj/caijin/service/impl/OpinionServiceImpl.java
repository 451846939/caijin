package com.kdkj.caijin.service.impl;

import com.kdkj.caijin.dao.OpinionDao;
import com.kdkj.caijin.dao.UsersDao;
import com.kdkj.caijin.entity.Opinion;
import com.kdkj.caijin.entity.Users;
import com.kdkj.caijin.enums.OpinionInfo;
import com.kdkj.caijin.service.OpinionService;
import com.kdkj.caijin.util.CopyObj;
import com.kdkj.caijin.util.ErrMsgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional
public class OpinionServiceImpl implements OpinionService {
    @Autowired
    private OpinionDao opinionDao;
    @Autowired
    private UsersDao usersDao;
    @Override
    public Page<Opinion> findAll(PageRequest pageRequest) {
        return opinionDao.findAll(pageRequest);
    }

    @Override
    public Opinion insert(Opinion opinion) {
        if (opinion != null) {
            if (StringUtils.isEmpty(opinion.getUserid())) {
                throw new ErrMsgException("用户id不能为空");
            }
            Optional<Users> byId = usersDao.findById(opinion.getUserid());
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id的用户");
            }
            opinion.setAnswer(OpinionInfo.NOT_ANSWER.getCode());
            opinion.setSee(OpinionInfo.NOT_SEE.getCode());
            Opinion save = opinionDao.save(opinion);
            return save;
        }
        return null;
    }

    @Override
    public int update(Opinion opinion) throws IllegalAccessException, InstantiationException {
        if (opinion != null) {
            Opinion oldOpinion = opinionDao.findById(opinion.getId()).get();
            CopyObj.copyObjNotNullFieldsAsObj(opinion, oldOpinion);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(Opinion opinion) {
        if (opinion != null) {
            opinionDao.delete(opinion);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(String id) {
        if (!StringUtils.isEmpty(id)) {
            Optional<Opinion> byId = opinionDao.findById(id);
            if (!byId.isPresent()) {
                throw new ErrMsgException("没有该id");
            }
            opinionDao.deleteById(id);
            return 1;
        }
        throw new ErrMsgException("id不能为空");
    }

    @Override
    public Page<Opinion> findByUseridAndAnswer(String userid, Integer answer, Pageable pageable) {
        return opinionDao.findByUseridAndAnswer(userid, answer, pageable);
    }
}
