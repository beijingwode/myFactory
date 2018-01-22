package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.mapper.ShippingTemplateRuletDao;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.service.ShippingTemplateRuleService;

@Service("shippingTemplateRuleService")
public class ShippingTemplateRuleServiceImpl implements ShippingTemplateRuleService {
	@Autowired
	private ShippingTemplateRuletDao shippingTemplateRuletDao;

	@Override
	public List<ShippingTemplateRule> getByProductId(Long productId) {
		return shippingTemplateRuletDao.getByProductId(productId);
	}

	@Override
	public List<ShippingTemplateRule> selectByModel(ShippingTemplateRule record) {
		
		return shippingTemplateRuletDao.selectByModel(record);
	}

	@Transactional
	@Override
	public void deleteByTemplateId(Long templateId) {
		
	}
}
