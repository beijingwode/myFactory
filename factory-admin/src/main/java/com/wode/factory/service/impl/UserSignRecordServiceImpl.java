/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.UserSignRecord;
import com.wode.factory.mapper.UserSignRecordDao;
import com.wode.factory.service.UserSignRecordService;

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

}