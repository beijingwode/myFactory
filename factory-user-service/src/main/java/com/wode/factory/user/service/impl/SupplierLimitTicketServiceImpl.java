/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.user.dao.SupplierLimitTicketDao;
import com.wode.factory.user.service.SupplierLimitTicketService;

@Service("supplierLimitTicketService")
public class SupplierLimitTicketServiceImpl extends FactoryEntityServiceImpl<SupplierLimitTicket> implements  SupplierLimitTicketService{
	@Autowired
	private SupplierLimitTicketDao dao;
	
	@Override
	public SupplierLimitTicketDao getDao() {
		return dao;
	}

	@Override
	public Long getId(SupplierLimitTicket entity) {
		return entity.getId();
	}

	@Override
	public void setId(SupplierLimitTicket entity, Long id) {
		if(entity!=null) {
			entity.setId(id);
		}
	}

	@Override
	public SupplierLimitTicket getByIdAndDateStatus(Long limitTicketId) {
		
		return dao.getByIdAndDateStatus(limitTicketId);
	}

	@Override
	public List<SupplierLimitTicket> selectLimit4BySupId(Long supplierId) {
		return dao.selectLimit4BySupId(supplierId);
	}

	@Override
	public List<SupplierLimitTicket> getWithOutTicketMap(Long supplierId, Long userId, Integer limitType,
			String skuIds) {
		return dao.getWithOutTicketMap(supplierId, userId, limitType, skuIds);
	}
}