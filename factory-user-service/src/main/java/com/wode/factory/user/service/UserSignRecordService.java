
/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.UserSignRecord;
public interface UserSignRecordService extends FactoryEntityService<UserSignRecord>{

	UserSignRecord getRecordByMap(Map<String, Object> map);

	Integer findMaxLuckyNumber(Map<String, Object> map);

	List<UserSignRecord> getRecordPhoneByMap(Map<String, Object> map);

}
