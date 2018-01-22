/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.BaseService;
import com.wode.common.frame.base.EntityDao;
import com.wode.factory.model.Refundorder;
import com.wode.factory.model.open.RefundOrders;
import com.wode.factory.supplier.dao.RefundorderDao;
import com.wode.factory.supplier.service.RefundorderService;

@Service("refundorderService")
public class RefundorderServiceImpl extends BaseService<Refundorder,java.lang.Long> implements  RefundorderService{
	@Autowired
	@Qualifier("refundorderDao")
	private RefundorderDao refundorderDao;
	
	public EntityDao getEntityDao() {
		return this.refundorderDao;
	}
	
	public Refundorder getRefundorById(Long id){
		return this.refundorderDao.getRefundorById(id);
	}

	@Override
	public List<RefundOrders> getRefundorWithSuborder(Map map) {
		return refundorderDao.getRefundorWithSuborder(map);
	}

	@Override
	public  List<Map> selectAttachmentsByRefundOrderId(Map map) {
		return refundorderDao.selectAttachmentsByRefundOrderId(map);
	}
	
}
