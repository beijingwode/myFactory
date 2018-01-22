package com.wode.factory.service.impl;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.common.stereotype.QueryCached;
import com.wode.factory.model.ProductShipping;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.service.ShippingFreeRuleService;
import com.wode.factory.service.ShippingTemplateService;

@Service("shippingFreeRuleService")
public class ShippingFreeRuleServiceImpl implements ShippingFreeRuleService {

	@Autowired
	private Dao dao;
	
	@Autowired
	private ShippingTemplateService shippingTemplateService;
	
	@Override
	@QueryCached(keyPreFix="shippingFreeRule_product")
	public List<ShippingFreeRule> findByProduct(Long productId) {
		
		List<ProductShipping> pAttr=dao.query(ProductShipping.class, Cnd.where("product_id", "=", productId));
		if(pAttr!= null && !pAttr.isEmpty()) {
			return findByTemplate(pAttr.get(0).getTemplateId());
		}
		return null;
	}

	@Override
	@QueryCached(keyPreFix="shippingFreeRule_template")
	public List<ShippingFreeRule> findByTemplate(Long templateId) {
		return dao.query(ShippingFreeRule.class, Cnd.where("template_id", "=", templateId));
	}

	@Override
	public List<ShippingFreeRule> findBySupplier(Long supplierId) {
		ShippingTemplate shippingTemplate = shippingTemplateService.selectTemplateBySupplierId(supplierId);
		if(shippingTemplate!= null ) {
			return findByTemplate(shippingTemplate.getId());
		}
		return null;
	}

	@Override
	public List<ShippingFreeRule> findByTemplateOrCountTypeDes(Long id) {
		 List<ShippingFreeRule> query = dao.query(ShippingFreeRule.class, Cnd.where("template_id", "=", id));
		 return query;
	}
}
