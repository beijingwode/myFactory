package com.wode.factory.supplier.service;

import java.util.List;

import com.wode.factory.company.service.BasePageService;
import com.wode.factory.model.ShippingTemplateRule;


public interface ShippingTemplateRuleService extends BasePageService<ShippingTemplateRule> {
	
	public boolean batchDelete(List<Long> list);

	public boolean batchAdd(List<ShippingTemplateRule> list);
}
