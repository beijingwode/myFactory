package com.wode.factory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wode.factory.mapper.ShippingFreeRuleDao;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.service.ShippingFreeRuleService;

@Service("shippingFreeRuleService")
public class ShippingFreeRuleServiceImpl implements ShippingFreeRuleService {
	@Autowired
	private ShippingFreeRuleDao shippingFreeRuleDao;

	@Override
	public List<ShippingFreeRule> getByProductId(Long productId) {
		return shippingFreeRuleDao.getByProductId(productId);
	}

	@Override
	public List<ShippingFreeRule> selectByModel(ShippingFreeRule record) {
		
		return shippingFreeRuleDao.selectByModel(record);
	}

	@Transactional
	@Override
	public void deleteByTemplateId(Long templateId) {
		shippingFreeRuleDao.deleteByTemplateId(templateId);
		
	}
}
