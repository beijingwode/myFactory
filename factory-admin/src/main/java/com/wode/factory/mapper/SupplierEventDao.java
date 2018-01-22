/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.mapper;

import java.util.List;
import java.util.Map;

import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.model.UserSignRecord;

public interface SupplierEventDao extends FactoryBaseDao<SupplierEvent>{

	List<SupplierEvent> findInfoPageList(Map<String, Object> params);

	Integer getSignCnt(Map<String, Object> map);

	List<SupplierEvent> getUserManagerGroup();

	List<UserSignRecord> getUserSignMsg(Map<String, Object> map);

	List<UserPrizeRecord> getUserPrizeMsg(Map<String, Object> map);

}