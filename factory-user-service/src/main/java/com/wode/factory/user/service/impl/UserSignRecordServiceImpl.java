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
import com.wode.factory.model.UserSignRecord;
import com.wode.factory.user.dao.UserSignRecordDao;
import com.wode.factory.user.service.UserSignRecordService;

@Service("userSignRecordService")
public class UserSignRecordServiceImpl extends  FactoryEntityServiceImpl<UserSignRecord> implements UserSignRecordService{

    @Autowired
    private UserSignRecordDao dao;
    @Override
    public UserSignRecordDao getDao() {
        return dao;
    }

    @Override
    public Long getId(UserSignRecord entity) {
        return entity.getId();
    }

    @Override
    public void setId(UserSignRecord entity, Long id) {
        if(entity!=null) {
            entity.setId(id);
        }
    }

	@Override
	public UserSignRecord getRecordByMap(Map<String, Object> map) {
		return dao.getRecordByMap(map);
	}

	@Override
	public Integer findMaxLuckyNumber(Map<String, Object> map) {
		return dao.findMaxLuckyNumber(map);
	}

	@Override
	public List<UserSignRecord> getRecordPhoneByMap(Map<String, Object> map) {
		return dao.getRecordPhoneByMap(map);
	}

}