/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.CommissionSetting;
import com.wode.factory.supplier.dao.CommissionSettingDao;
import com.wode.factory.supplier.query.CommissionSettingQuery;
import com.wode.factory.supplier.service.CommissionSettingService;

@Service("commissionSettingService")
public class CommissionSettingServiceImpl extends BaseService<CommissionSetting,java.lang.Long> implements  CommissionSettingService{
	@Autowired
	@Qualifier("commissionSettingDao")
	private CommissionSettingDao commissionSettingDao;
	
	public EntityDao getEntityDao() {
		return this.commissionSettingDao;
	}
	
	public Page findPage(CommissionSettingQuery query) {
		return commissionSettingDao.findPage(query);
	}

	@Override
	public List<CommissionSetting> getBySupplerId(Map<String, Object> map) {
		return commissionSettingDao.getBySupplerId(map);
	}

	@Override
	public Integer getBySupplerIdCount(Long supplerId) {
		return commissionSettingDao.getBySupplerIdCount(supplerId);
	}
	
}
