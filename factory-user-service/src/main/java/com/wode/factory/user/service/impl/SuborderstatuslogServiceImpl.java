/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Suborderstatuslog;
import com.wode.factory.user.dao.SuborderstatuslogDao;
import com.wode.factory.user.query.SuborderstatuslogQuery;
import com.wode.factory.user.service.SuborderstatuslogService;

@Service("suborderstatuslogService")
public class SuborderstatuslogServiceImpl extends BaseService<Suborderstatuslog,java.lang.Long> implements  SuborderstatuslogService{
	@Autowired
	@Qualifier("suborderstatuslogDao")
	private SuborderstatuslogDao suborderstatuslogDao;
	
	public EntityDao getEntityDao() {
		return this.suborderstatuslogDao;
	}
	
	public Page findPage(SuborderstatuslogQuery query) {
		return suborderstatuslogDao.findPage(query);
	}
	
}
