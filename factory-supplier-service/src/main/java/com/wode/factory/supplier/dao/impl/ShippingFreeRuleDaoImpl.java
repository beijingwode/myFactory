package com.wode.factory.supplier.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wode.factory.company.dao.impl.BasePageDaoImpl;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.supplier.dao.ShippingFreeRuleDao;
@Repository("shippingFreeRuleDao")
public class ShippingFreeRuleDaoImpl extends BasePageDaoImpl<ShippingFreeRule> implements ShippingFreeRuleDao {
	
	@Override
	public String getIbatisMapperNamesapce() {
		return "ShippingFreeRuleMapper";
	}
	
	@Override
	public Long getId(ShippingFreeRule model) {
		return model.getId();
	}

	@Override
	public void insert(ShippingFreeRule record) {
		this.getSqlSession().selectOne(getIbatisMapperNamesapce()+".insert",record);
	}

	@Override
	public int batchDelete(List<Long> list) {
		// TODO Auto-generated method stub
		return getSqlSession().delete(getIbatisMapperNamesapce()+".batchDelete", list);
	}

	@Override
	public int batchAdd(List<ShippingFreeRule> list) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(getIbatisMapperNamesapce()+".batchAdd", list);
	}
}
