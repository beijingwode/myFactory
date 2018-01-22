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
import com.wode.factory.model.Returnorderitem;
import com.wode.factory.user.dao.ReturnorderItemDao;
import com.wode.factory.user.query.ReturnorderItemQuery;
import com.wode.factory.user.service.ReturnorderItemService;

@Service("returnorderItemService")
public class ReturnorderItemServiceImpl extends BaseService<Returnorderitem,java.lang.Long> implements  ReturnorderItemService{
	@Autowired
	@Qualifier("returnorderItemDao")
	private ReturnorderItemDao returnorderItemDao;
	
	public EntityDao getEntityDao() {
		return this.returnorderItemDao;
	}
	
	public Page findPage(ReturnorderItemQuery query) {
		return returnorderItemDao.findPage(query);
	}
	
}
