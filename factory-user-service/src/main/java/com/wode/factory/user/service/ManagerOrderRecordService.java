/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.FactoryEntityService;
import com.wode.factory.model.ManagerOrderRecord;
import com.wode.factory.model.Orders;
import com.wode.factory.model.UserFactory;
import com.wode.factory.user.vo.SubOrderVo;

public interface ManagerOrderRecordService extends FactoryEntityService<ManagerOrderRecord>{

	List<ManagerOrderRecord> getManagerOrderRecordList(Map<String, Object> query);

	void addManagerOrderRecord(ManagerOrderRecord managerOrderRecord, UserFactory loginUser, Integer flag);

}
