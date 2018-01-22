/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.frame.base.FactoryEntityServiceImpl;
import com.wode.common.util.StringUtils;
import com.wode.factory.model.Invoice;
import com.wode.factory.model.InvoiceApply;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Suborder;
import com.wode.factory.user.dao.InvoiceApplyDao;
import com.wode.factory.user.dao.InvoiceDao;
import com.wode.factory.user.service.InvoiceApplyService;
import com.wode.factory.user.service.OrdersService;
import com.wode.factory.user.service.SuborderService;

@Service("invoiceApplyService")
public class InvoiceApplyServiceImpl extends FactoryEntityServiceImpl<InvoiceApply> implements  InvoiceApplyService{
	@Autowired
	private InvoiceApplyDao dao;
	
	@Autowired
	@Qualifier("invoiceDao")
	private InvoiceDao invoiceDao;
	
	@Qualifier("suborderService")
	@Autowired
	private SuborderService suborderService;
	
	@Qualifier("ordersService") 
	@Autowired
	private OrdersService ordersService;

	@Override
	public InvoiceApplyDao getDao() {
		return dao;
	}

	@Override
	public Long getId(InvoiceApply entity) {
		return entity.getId();
	}

	@Override
	public void setId(InvoiceApply entity, Long id) {
		entity.setId(id);
	}

	@Override
	public boolean addInvoiceByOrderId(Long orderId) {
		//先查询出订单
		Orders order = ordersService.getById(orderId);
		/*if(StringUtils.isNullOrEmpty(order.getInvoiceTitle())){
			return false;
		}*/
		if(StringUtils.isEmpty(order.getInvoiceId())){
			return false;
		}
		Invoice invoice = invoiceDao.getById(order.getInvoiceId());
		if(invoice == null){
			return false;
		}
		//在查询出子单
		List<Suborder> lists = suborderService.findByOrderId(orderId);
		for (Suborder suborder : lists) {
			InvoiceApply ia = new InvoiceApply();
			ia.setUserId(order.getUserId());
			ia.setBillType(invoice.getBillType());
			ia.setCreatetime(new Date());
			ia.setTitle(invoice.getTitle());
			ia.setAddress(order.getAddress());
			ia.setType(1);
			ia.setOpeningBan(invoice.getOpeningBan());
			ia.setOpeningBanNumber(invoice.getOpeningBanNumber());
			ia.setRegisterAddress(invoice.getRegisterAddress());
			ia.setRegisterPhone(invoice.getRegisterPhone());
			ia.setTaxpayerNumber(invoice.getTaxpayerNumber());
			ia.setSuborderid(suborder.getSubOrderId());
			saveOrUpdate(ia);
			suborder.setInvoiceStatus(1);
			suborderService.update(suborder);
		}
		return true;
	}

	@Override
	public boolean addInvoiceBySubOrderId(String subOrderId) {
		Suborder suborder = suborderService.getById(subOrderId);
		Orders order = ordersService.getById(suborder.getOrderId());
		if(StringUtils.isNullOrEmpty(order.getInvoiceTitle())){
			return false;
		}
		InvoiceApply ia = new InvoiceApply();
		ia.setUserId(order.getUserId());
		ia.setCreatetime(new Date());
		ia.setTitle(order.getInvoiceTitle());
		ia.setAddress(order.getAddress());
		ia.setType(1);
		ia.setSuborderid(suborder.getSubOrderId());
		saveOrUpdate(ia);
		suborder.setInvoiceStatus(1);
		suborderService.update(suborder);
		return true;
	}
	
}
