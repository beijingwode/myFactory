package com.wode.factory.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wode.factory.company.dao.BasePageDao;
import com.wode.factory.company.service.impl.BasePageServiceImpl;
import com.wode.factory.model.ShippingFreeRule;
import com.wode.factory.supplier.dao.ShippingFreeRuleDao;
import com.wode.factory.supplier.service.ShippingFreeRuleService;

@Service("supplierShippingFreeRuleService")
public class ShippingFreeRuleServiceImpl extends BasePageServiceImpl<ShippingFreeRule>
		implements ShippingFreeRuleService {

	@Autowired
	private ShippingFreeRuleDao dao;
	
	@Override
	protected BasePageDao<ShippingFreeRule> getBaseDao() {
		return dao;
	}

	@Override
	public void insert(ShippingFreeRule record) {
		dao.insert(record);
	}

	@Override
	public boolean batchDelete(List<Long> list) {
		if( null != list && !list.isEmpty()){
			int i = dao.batchDelete(list);
			if( i > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean batchAdd(List<ShippingFreeRule> list) {
		if( null != list && !list.isEmpty()){
			int i = dao.batchAdd(list);
			if( i > 0){
				return true;
			}
		}
		return false;
	}
	

}
