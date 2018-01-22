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
import com.wode.factory.model.Refundorderlog;
import com.wode.factory.user.dao.RefundorderLogDao;
import com.wode.factory.user.query.RefundorderLogQuery;
import com.wode.factory.user.service.RefundorderLogService;

@Service("refundorderLogService")
public class RefundorderLogServiceImpl extends BaseService<Refundorderlog,java.lang.Long> implements  RefundorderLogService{
	@Autowired
	@Qualifier("refundorderLogDao")
	private RefundorderLogDao refundorderLogDao;
	
	public EntityDao getEntityDao() {
		return this.refundorderLogDao;
	}
	
	public Page findPage(RefundorderLogQuery query) {
		return refundorderLogDao.findPage(query);
	}
	
}
