/*
 * Powered By [wo-de.com]
 * Web Site: http://www.wo-de.com
 * Since 2018 - 2020
 */
package com.wode.factory.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.wode.factory.model.SupplierEvent;
import com.wode.factory.model.UserPrizeRecord;
import com.wode.factory.model.UserSignRecord;

public interface SupplierEventService extends FactoryEntityService<SupplierEvent>{

	PageInfo<SupplierEvent> findInfoPageList(Map<String, Object> params);

	List<SupplierEvent> getUserManagerGroup();

	List<UserSignRecord> getUserSignMsg(Map<String, Object> map);

	List<UserPrizeRecord> getUserPrizeMsg(Map<String, Object> map);

}