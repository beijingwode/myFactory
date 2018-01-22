/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.mapper;

import com.wode.factory.model.Refundorder;
import com.wode.factory.model.Returnorder;

public interface RefundorderDao{
	public void saveOrUpdate(Refundorder entity);
	/**
	 * 根据退货单Id查询退款
	 * @param returnOrderId
	 * @return
	 */
	public Refundorder findByReturnorderId(Long returnOrderId);
	public Refundorder getById(Long refundOrderId);
	public void update(Refundorder returnorder);

}
