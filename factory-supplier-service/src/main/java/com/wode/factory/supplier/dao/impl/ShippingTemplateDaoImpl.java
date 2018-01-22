package com.wode.factory.supplier.dao.impl;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.supplier.dao.ShippingTemplateDao;
@Repository("shippingTemplateDao")
public class ShippingTemplateDaoImpl extends BasePageDaoImpl<ShippingTemplate> implements ShippingTemplateDao {
	@Override
	public String getIbatisMapperNamesapce() {
		return "ShippingTemplateMapper";
	}

	@Override
	public Long getId(ShippingTemplate model) {
		return model.getId();
	}
}
