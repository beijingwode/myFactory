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
import com.wode.factory.model.Returnorderlog;
import com.wode.factory.user.dao.ReturnorderlogDao;
import com.wode.factory.user.query.ReturnorderlogQuery;
import com.wode.factory.user.service.ReturnorderlogService;

@Service("returnorderlogService")
public class ReturnorderlogServiceImpl extends BaseService<Returnorderlog,java.lang.Long> implements  ReturnorderlogService{
	@Autowired
	@Qualifier("returnorderlogDao")
	private ReturnorderlogDao returnorderlogDao;
	
	public EntityDao getEntityDao() {
		return this.returnorderlogDao;
	}
	
	public Page findPage(ReturnorderlogQuery query) {
		return returnorderlogDao.findPage(query);
	}
	
}
