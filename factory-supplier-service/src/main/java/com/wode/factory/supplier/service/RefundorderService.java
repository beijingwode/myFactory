/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service;

import java.util.List;
import java.util.Map;

import com.wode.common.frame.base.EntityDao;
import com.wode.common.frame.base.EntityService;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.open.RefundOrders;

public interface RefundorderService extends EntityService<Refundorder,Long>{
	
	public EntityDao getEntityDao() ;
	
	public Refundorder getRefundorById(Long id);
	
	public List<RefundOrders> getRefundorWithSuborder(Map map);
	
	public  List<Map> selectAttachmentsByRefundOrderId(Map map);
}
