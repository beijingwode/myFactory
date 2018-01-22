package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ShippingTemplate;
import com.wode.factory.model.ShippingTemplateRule;
import com.wode.factory.supplier.dao.ShippingTemplateRuleDao;
@Repository("shippingTemplateRuleDao")
public class ShippingTemplateRuleDaoImpl extends BasePageDaoImpl<ShippingTemplateRule> implements ShippingTemplateRuleDao {
	@Override
	public String getIbatisMapperNamesapce() {
		return "ShippingTemplateRuleMapper";
	}

	@Override
	public Long getId(ShippingTemplateRule model) {
		return model.getId();
	}

	@Override
	public int batchDelete(List<Long> list) {
		return getSqlSession().delete(getIbatisMapperNamesapce()+".batchDelete", list);
	}

	@Override
	public int bathcAdd(List<ShippingTemplateRule> list) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(getIbatisMapperNamesapce()+".batchAdd", list);
	}
	
	
}
