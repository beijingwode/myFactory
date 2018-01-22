/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.dao;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryBaseDao;
import com.wode.factory.model.UserSignRecord;

public interface UserSignRecordDao extends FactoryBaseDao<UserSignRecord>{

	UserSignRecord getRecordByMap(Map<String, Object> map);

	Integer findMaxLuckyNumber(Map<String, Object> map);

	List<UserSignRecord> getRecordPhoneByMap(Map<String, Object> map);

}
