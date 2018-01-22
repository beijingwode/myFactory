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
import com.wode.factory.model.Refundorder;
import com.wode.factory.user.dao.RefundorderDao;
import com.wode.factory.user.query.RefundorderQuery;
import com.wode.factory.user.service.RefundorderService;

@Service("refundorderService")
public class RefundorderServiceImpl extends BaseService<Refundorder,java.lang.Long> implements  RefundorderService{
	@Autowired
	@Qualifier("refundorderDao")
	private RefundorderDao refundorderDao;
	
	public EntityDao getEntityDao() {
		return this.refundorderDao;
	}
	
	public Page findPage(RefundorderQuery query) {
		return refundorderDao.findPage(query);
	}

	@Override
	public Refundorder getRefundordersById(Long refundOrderId) {
		return refundorderDao.getRefundordersById(refundOrderId);
	}
	
}
