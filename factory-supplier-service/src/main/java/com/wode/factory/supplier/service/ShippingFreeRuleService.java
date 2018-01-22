package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ShippingFreeRule;


public interface ShippingFreeRuleService extends BasePageService<ShippingFreeRule> {
	
	public void insert(ShippingFreeRule record);
	
	public boolean batchDelete(List<Long> list);
	
	public boolean batchAdd(List<ShippingFreeRule> list);
	
}
