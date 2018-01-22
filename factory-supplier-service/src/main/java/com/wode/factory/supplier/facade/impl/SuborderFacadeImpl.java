/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package com.wode.factory.supplier.facade.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wode.common.util.ActResult;
import com.wode.factory.model.Orders;
import com.wode.factory.model.Suborder;
import com.wode.factory.model.Suborderitem;
import com.wode.factory.model.Supplier;
import com.wode.factory.supplier.facade.SuborderFacade;
import com.wode.factory.supplier.service.OrdersService;
import com.wode.factory.supplier.service.SuborderService;
import com.wode.factory.supplier.service.SuborderitemService;
import com.wode.factory.supplier.service.SupplierService;

@Service("suborderFacade")
public class SuborderFacadeImpl implements SuborderFacade {

	@Autowired
	@Qualifier("suborderService")
	private SuborderService suborderService;
	
	@Autowired
	@Qualifier("ordersService")
	private OrdersService ordersService;
	
	@Autowired
	@Qualifier("suborderitemService")
	private SuborderitemService suborderitemService;

	@Autowired
	@Qualifier("supplierService")
	private SupplierService supplierService;
	
	@Override
	public ActResult<BigDecimal> changeFreight(Long supplierId,String suborderId, Double freight,String updUser) {
		Supplier supplier = supplierService.getById(supplierId);
		Suborder suborder_= suborderService.getById(suborderId);
		Orders o =ordersService.getById(suborder_.getOrderId());
		if (suborder_.getStatus()!=0) {//非待支付订单
			return ActResult.fail("操作失败，买家已付款，请刷新页面后重试");
		}
		//安全性检查
		if(suborder_.getSupplierId().equals(supplier.getId())){
			Date updDate = new Date();
			
			//修改订单运费及实付金额
			BigDecimal originalFreight = suborder_.getTotalShipping();
			BigDecimal newFreight = new BigDecimal(freight);
			suborder_.setTotalProduct(suborder_.getTotalProduct().add(newFreight.subtract(originalFreight)));
			suborder_.setRealPrice(suborder_.getRealPrice().add(newFreight.subtract(originalFreight)));
			suborder_.setTotalShipping(newFreight);
			suborder_.setUpdateBy(updUser);
			suborder_.setUpdateTime(updDate);
			
			//修改订单
			o.setTotalShipping(o.getTotalShipping().add(newFreight.subtract(originalFreight)));
			o.setRealPrice(o.getRealPrice().add(newFreight.subtract(originalFreight)));
			o.setUpdateBy(updUser);
			o.setUpdateTime(updDate);
			ordersService.saveOrUpdate(o);
			
			suborderService.saveOrUpdate(suborder_);
			
			//修改子单项中运费，将新运费设置到第一个子单项上，其他子单项运费清空（如有）
			Suborderitem record = new Suborderitem();
			record.setSubOrderId(suborderId);
			List<Suborderitem> items =suborderitemService.selectByModel(record);
			if(items !=null && !items.isEmpty()) {
				//将新运费设置到第一个子单项上
				items.get(0).setShipping(newFreight);
				items.get(0).setUpdateBy(updUser);
				items.get(0).setUpdateTime(updDate);
				suborderitemService.saveOrUpdate(items.get(0));
				
				//其他子单项运费清空（如有）
				for(int i=1;i<items.size();i++) {
					items.get(i).setShipping(BigDecimal.ZERO);
					items.get(i).setUpdateBy(updUser);
					items.get(i).setUpdateTime(updDate);
					suborderitemService.saveOrUpdate(items.get(i));
				}
			}
			return ActResult.success(originalFreight);
			
		} else {
			return ActResult.fail("操作失败，非本商家订单，请刷新页面后重试");
		}
	}
}
