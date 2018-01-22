/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.mapper.UserPrizeRecordDao;
import com.wode.factory.service.UserPrizeRecordService;

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

}