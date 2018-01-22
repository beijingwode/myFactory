package com.wode.factory.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.supplier.dao.ShippingTemplateDao;
import com.wode.factory.supplier.service.ShippingTemplateService;

@Service("shippingTemplateService")
public class ShippingTemplateServiceImpl extends BasePageServiceImpl<ShippingTemplate>
		implements ShippingTemplateService {

	@Autowired
	private ShippingTemplateDao dao;
	
	@Override
	protected BasePageDao<ShippingTemplate> getBaseDao() {
		return dao;
	}

}
