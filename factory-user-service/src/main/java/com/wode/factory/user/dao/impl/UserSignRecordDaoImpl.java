/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wode.common.frame.base.FactoryBaseDaoImpl;
import com.wode.factory.model.UserSignRecord;
import com.wode.factory.user.dao.UserSignRecordDao;

@Repository("userSignRecordDao")
public class UserSignRecordDaoImpl extends  FactoryBaseDaoImpl<UserSignRecord> implements UserSignRecordDao{

    @Override
    public String getIbatisMapperNamesapce() {
        return "UserSignRecordMapper";
    }

	@Override
	public UserSignRecord getRecordByMap(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce() + ".getRecordByMap", map);
	}

	@Override
	public Integer findMaxLuckyNumber(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNamesapce()+".findMaxLuckyNumber", map);
	}

	@Override
	public List<UserSignRecord> getRecordPhoneByMap(Map<String, Object> map) {
		return getSqlSession().selectList(getIbatisMapperNamesapce() + ".getRecordPhoneByMap", map);
	}
    
    
}