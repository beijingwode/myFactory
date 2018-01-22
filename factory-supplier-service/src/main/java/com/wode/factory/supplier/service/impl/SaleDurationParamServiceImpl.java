/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.BaseService;
import cn.org.rapid_framework.page.Page;

import com.wode.factory.model.SaleDurationParam;
import com.wode.factory.supplier.dao.SaleDurationParamDao;
import com.wode.factory.supplier.query.SaleDurationParamQuery;
import com.wode.factory.supplier.service.SaleDurationParamService;

@Service("saleDurationParamService")
public class SaleDurationParamServiceImpl extends BaseService<SaleDurationParam,java.lang.Long> implements  SaleDurationParamService{
	@Autowired
	@Qualifier("saleDurationParamDao")
	private SaleDurationParamDao saleDurationParamDao;
	
	public EntityDao getEntityDao() {
		return this.saleDurationParamDao;
	}
	
	public Page findPage(SaleDurationParamQuery query) {
		return saleDurationParamDao.findPage(query);
	}
	
	public SaleDurationParam findSaleDurationParamByKey(String key){
		return saleDurationParamDao.findSaleDurationParamByKey(key);
	}
	
}
