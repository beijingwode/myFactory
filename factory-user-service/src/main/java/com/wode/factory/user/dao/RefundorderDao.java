/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.dao;

import cn.org.rapid_framework.page.Page;

import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Refundorder;
import com.wode.factory.user.query.RefundorderQuery;

public interface RefundorderDao extends  EntityDao<Refundorder,Long>{
	public Page findPage(RefundorderQuery query);
	public void saveOrUpdate(Refundorder entity);
	public Refundorder getRefundordersById(Long refundOrderId);

}
