/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.GradeMsgVo;
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.user.dao.UserPrizeRecordDao;
import com.wode.factory.user.service.UserPrizeRecordService;

@Service("userPrizeRecordService")
public class UserPrizeRecordServiceImpl extends  FactoryEntityServiceImpl<UserPrizeRecord> implements UserPrizeRecordService{

    @Autowired
    private UserPrizeRecordDao dao;
    @Override
    public UserPrizeRecordDao getDao() {
        return dao;
    }

    @Override
    public Long getId(UserPrizeRecord entity) {
        return entity.getId();
    }

    @Override
    public void setId(UserPrizeRecord entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public List<GradeMsgVo> findGradeByGroup(Map<String, Object> map) {
		return dao.findGradeByGroup(map);
	}

}