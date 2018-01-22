package com.wode.factory.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.service.ShippingTemplateRuleService;

@Service("shippingTemplateRuleService")
public class ShippingTemplateRuleServiceImpl implements ShippingTemplateRuleService {

	@Autowired
	private Dao dao;
	
	@Override
	@QueryCached(keyPreFix="shippingTemplateRule_product")
	public List<ShippingTemplateRule> findByProduct(Long productId) {
		
		List<ProductShipping> pAttr=dao.query(ProductShipping.class, Cnd.where("product_id", "=", productId));
		if(pAttr!= null && !pAttr.isEmpty()) {
			return findByTemplate(pAttr.get(0).getTemplateId());
		}
		return null;
	}

	@Override
	@QueryCached(keyPreFix="shippingTemplateRule_template")
	public List<ShippingTemplateRule> findByTemplate(Long templateId) {
		return dao.query(ShippingTemplateRule.class, Cnd.where("template_id", "=", templateId));
	}
}
