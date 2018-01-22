/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wode.factory.mapper.SupplierLimitTicketDao;
import com.wode.factory.model.SupplierLimitTicket;
import com.wode.factory.service.SupplierLimitTicketService;
import com.wode.factory.vo.SupplierLimitTicketVo;

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
	public PageInfo<SupplierLimitTicketVo> findInfoPageList(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SupplierLimitTicketVo> list = dao.findInfoPageList(params);
		return new PageInfo(list);
	}

	@Override
	public List<SupplierLimitTicket> getPastTicket() {
		return dao.getPastTicket();
	}

	@Override
	public SupplierLimitTicket findInfoByMap(Map<String, Long> map) {
		return dao.findInfoByMap(map);
	}
}