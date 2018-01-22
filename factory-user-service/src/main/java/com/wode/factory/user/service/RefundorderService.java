/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Refundorder;
import com.wode.factory.user.query.RefundorderQuery;

@Service("refundorderService")
public interface RefundorderService extends EntityService<Refundorder,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Page findPage(RefundorderQuery query);
	/**
	 * 仅退款带有凭证
	 * @param refundOrderId
	 * @return
	 */
	public Refundorder getRefundordersById(Long refundOrderId);
	
}
