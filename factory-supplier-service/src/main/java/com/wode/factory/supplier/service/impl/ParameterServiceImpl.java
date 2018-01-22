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

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Parameter;
import com.wode.factory.supplier.dao.ParameterDao;
import com.wode.factory.supplier.query.ParameterQuery;
import com.wode.factory.supplier.service.ParameterService;

@Service("parameterService")
public class ParameterServiceImpl extends BaseService<Parameter,java.lang.Long> implements  ParameterService{
	@Autowired
	@Qualifier("parameterDao")
	private ParameterDao parameterDao;
	
	public EntityDao getEntityDao() {
		return this.parameterDao;
	}
	
	public Page findPage(ParameterQuery query) {
		return parameterDao.findPage(query);
	}
	
}
