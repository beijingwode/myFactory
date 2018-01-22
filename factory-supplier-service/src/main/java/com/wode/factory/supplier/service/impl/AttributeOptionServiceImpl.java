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
import com.wode.factory.model.AttributeOption;
import com.wode.factory.supplier.dao.AttributeOptionDao;
import com.wode.factory.supplier.query.AttributeOptionQuery;
import com.wode.factory.supplier.service.AttributeOptionService;

@Service("attributeOptionService")
public class AttributeOptionServiceImpl extends BaseService<AttributeOption,java.lang.Long> implements  AttributeOptionService{
	@Autowired
	@Qualifier("attributeOptionDao")
	private AttributeOptionDao attributeOptionDao;
	
	public EntityDao getEntityDao() {
		return this.attributeOptionDao;
	}
	
	public Page findPage(AttributeOptionQuery query) {
		return attributeOptionDao.findPage(query);
	}
	
}
